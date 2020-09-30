package Ruda;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import process.Store;
import rnd.Randomable;

public class Excav extends Actor {


	private Store bunk; //бункер для руди
	
	private Store area; //площадка

	private QueueForTransactions<Drob> queueToExcav; //черга самоскидів

	private double finishTime; //тривалість роботи навантажувача

	private Randomable rnd; //Генератор часу, що витрачається на одну порцію руди

	private BooleanSupplier isDrob;//самоскид в черзі

	private double bunkMaxSize;  //максимальний розмір бункеру
	
	private BooleanSupplier bunkSize; //перевірка на загруженність бункеру

	// Коструктор, у якому ініціалізуються поля об'єкту через посилання на модель  та візуальну частину
	public Excav(String name, GUI gui, Model model) {
		setNameForProtocol(name);
		bunk = model.getBunk();
		area = model.getArea();
		queueToExcav = model.getQueueToExcav();
		finishTime = gui.getChooseDataFinishTime().getDouble();
		bunkMaxSize = gui.getChooseDataBunkMaxSize().getDouble();
		rnd = gui.getRndExcav();
		setHistoForActorWaitingTime(model.getHistoExcav());
	}

    //робота навантажувача:
	protected void rule() throws DispatcherFinishException {
		isDrob = () -> queueToExcav.size() > 0;
		bunkSize = () -> bunk.getSize() > 0;
		// Цикл виконанння правил дії навантажувача
		while (getDispatcher().getCurrentTime() <= finishTime) {
			// Перевірка, чи є в черзі самоскид,
			// і якщо його нема - чекання
			waitForCondition(isDrob, "має бути самоскид");
			// Забираємо самоскид на обслуговування
			Drob drob = queueToExcav.removeFirst();
			// Цикл завантаження самоскиду
			while (!drob.isFull()) {
				if (bunk.getSize() >= bunkMaxSize) {
		
				getDispatcher().printToProtocol(getNameForProtocol() + " додає порцію руди");
				area.add(1);
				holdForTime(rnd.next());
				drob.addPortion();
				getDispatcher().printToProtocol(
						getNameForProtocol() + " додає порцію руди на площажку " + drob.getNameForProtocol());
			}
				else {
					
					getDispatcher().printToProtocol(getNameForProtocol() + " додає порцію руди");
					bunk.add(1);
					holdForTime(rnd.next());
					drob.addPortion();
					getDispatcher().printToProtocol(
							getNameForProtocol() + " додає порцію руди в бункер " + drob.getNameForProtocol());
				}
		}}
	}

	//Час моделювання, що сформує TransProcessManager
	public void setFinishTime(double finishTime) {
		this.finishTime = finishTime;

	}
}
