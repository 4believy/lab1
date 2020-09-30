package Ruda;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import rnd.Randomable;


public class Drob extends Actor {

	//тривалість роботи самоскиду
	private double finishTime;

    //самоскиди в дорозі
	private QueueForTransactions<Drob> queueDrobOnRoad;

	//черга до самоскидів
	private QueueForTransactions<Drob> queueToExcav;

    //генератор часу на дорогу
	private Randomable rnd;

	//місткість кузову самоскиду
	private int bodySize;

	//завантаження самоскиду
	private int bodyLoad;
	
    //умова повного самоскиду
	private BooleanSupplier isBodyFull;

	//// Коструктор, у якому ініціалізуються поля об'єкту через посилання на модель  та візуальну частину
	public Drob(String name, GUI gui, Model model) {
		setNameForProtocol(name);
		this.queueToExcav = model.getQueueToExcav();
		this.queueDrobOnRoad = model.getQueueDrobOnRoad();
		this.finishTime = gui.getChooseDataFinishTime().getDouble();
		this.bodySize = gui.getChooseDataBodySize().getInt();
		this.rnd = gui.getRndDrob();
		this.setHistoForActorWaitingTime(model.getHistoDrob());
	}
	

	//робота самоскиду
	public void rule() throws DispatcherFinishException {
		isBodyFull = () -> isFull();
		// Цикл правил дії скиду
		while (getDispatcher().getCurrentTime() <= finishTime) {
			// Саммоскид їде до навантажувача
			// i реєструється у списку самоскидів, що їдуть
			queueDrobOnRoad.addLast(this);
			holdForTime(rnd.next());
			// вилучає себе із відповідного списку
			queueDrobOnRoad.remove(this);
			// Самоскид стає у чергу до ескаватора,
			queueToExcav.addLast(this);
			// Чекає поки завантажать
				waitForCondition(isBodyFull, "кузов має бути повним");
			// Самоскид їде на розвантаження
			getDispatcher().printToProtocol(
					getNameForProtocol() + " поїхав розвантажуватися");
		
			// реєструється у списку самоскидів, що їдуть
			queueDrobOnRoad.add(this);
			holdForTime(rnd.next());
			// вилучає себе із списку самоскидів, що їдуть
			queueDrobOnRoad.remove(this);
			getDispatcher().printToProtocol(
					getNameForProtocol() + " розвантажується");
			// Самоскид розвантажується
			bodyLoad = 0;

		}
	}		

	//доданя руди в самоскид
	public void addPortion() {
		bodyLoad++;
		getDispatcher().printToProtocol(
				getNameForProtocol() + "- у кузові стало " + bodyLoad);
	}

	//перевірка завантаженності самоскиду
	public boolean isFull() {
		return bodyLoad >= bodySize;
	}


	//Час моделювання, що сформує TransProcessManager
		public void setFinishTime(double finishTime) {
			this.finishTime = finishTime;

		}
}
