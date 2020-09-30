package Ruda;


import process.Actor;
import process.DispatcherFinishException;
import rnd.Randomable;

public class Ruda extends Actor {

   //час роботи
	private double finishTime;

	//час на заповнення порції ескаватором
	private Randomable rnd;


	// Коструктор, у якому ініціалізуються поля об'єкту через посилання на модель  та візуальну частину
	public Ruda(String name, GUI gui, Model model) {
		setNameForProtocol(name);
		finishTime = gui.getChooseDataFinishTime().getDouble();
	}

	
	protected void rule() throws DispatcherFinishException {
		// Ініціалізація умов
		while (getDispatcher().getCurrentTime() <= finishTime) {
			// Затримка на час формування порції руди для самоскиду
			holdForTime(rnd.next());
		}
	}

	//Час моделювання, що сформує TransProcessManager
		public void setFinishTime(double finishTime) {
			this.finishTime = finishTime;

		}
}
