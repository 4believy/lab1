package Ruda;

import java.util.HashMap;
import java.util.Map;

import  process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions;
import process.Store;
import stat.DiscretHisto;
import stat.Histo;
import stat.IHisto;
import widgets.experiments.IExperimentable;
import widgets.stat.IStatisticsable;
import widgets.trans.ITransMonitoring;
import widgets.trans.ITransProcesable;

public class Model implements IExperimentable, ITransProcesable,
		IStatisticsable { 
	//посилання на диспетчера
	private Dispatcher dispatcher;

	//гуи
	private GUI gui;

	//ескаватор
	private Excav excav;

//зразок самоскиду
	private Drob drob;

	//бригада самоскидів
	private MultiActor multiDrob;


	private Store bunk; //бункер
	
	private Store area; //площадка

	
	//Черга самоскидів до ескаватору
	
	private QueueForTransactions<Drob> queueToExcav;

	//Уявна черга для самоскидів у дорозі
	private QueueForTransactions<Drob> queueDrobOnRoad;

	// Гістограма для довжини черги до ескаватору 
	private DiscretHisto histoForQueueToExcav;

	// Гістограма для часу простою ескаватору
	private Histo histoExcav;

	//Гістограма для часу простою самоскида
	private Histo histoDrob;

	//Гістограма для розмірів бункеру
	private Histo histoBunk;
	
	//Гістограма для розмірів площадки
	private Histo histoArea;

	// ////////////////////////////////////////
	// Єдиний спосіб створити модель, це викликати цей конструктор
	// Він гарантовано передає посилання на диспетчера та GUI
	// ////////////////////////////////////////

	public Model(Dispatcher d, GUI g) {
		if (d == null || g == null) {
			System.out.println("Не визначено диспетчера або GUI для RgrModel");
			System.out.println("Подальша робота неможлива");
			System.exit(0);
		}
		dispatcher = d;
		gui = g;
		// Передаємо акторів до стартового списку диспетчера
		componentsToStartList();
	}

	//Передача акторів диспетчеру
	private void componentsToStartList() {
		// Передаємо акторів диспетчеру
		dispatcher.addStartingActor(getExcav());
		dispatcher.addStartingActor(getMultiDrob());
	}

	// ////////////////////////////////////////
	// Методи відкладеного створення акторів моделі,
	// ////////////////////////////////////////

   //Meтод створення навантажувача
	public Excav getExcav() {
		if (excav == null)
			excav = new Excav("Ескаватор", gui, this);
		return excav;
	}

	//Meтод створення зразка самоскида
	 	public Drob getDrob() {
		if (drob == null)
			drob = new Drob("Самоскид", gui, this);
		return drob;
	}

    //Meтод створення бригади самоскидів
	public MultiActor getMultiDrob() {
		if (multiDrob == null) {
			multiDrob = new MultiActor("MultiActor для бригади самоскидів",
					getDrob(), gui.getChooseDataNDrob().getInt());
		}
		return multiDrob;
	}

	// ////////////////////////////////////////
	// Методи відкладеного створення черг
	// ////////////////////////////////////////

	//Meтод створення Бункеру
	public Store getBunk() {
		if (bunk == null) {
			bunk = new Store("Бункер", dispatcher, getHistoBunk());
		}
		return bunk;
	}
	
	//Meтод створення площадки
	public Store getArea() {
		if (area == null) {
			area = new Store("Площадка для руди", dispatcher, getHistoArea());
		}
		return area;
	}
	
	//Meтод створення черги до навантажувачау
	public QueueForTransactions<Drob> getQueueToExcav() {
		if (queueToExcav == null) {
			queueToExcav = new QueueForTransactions<Drob>(
					"Черга до Ескаватору", dispatcher, getHistoForQueueToExcav());
		}
		return queueToExcav;
	}

	//Meтод створення уявної черги "самоскиди у дорозі"
	public QueueForTransactions<Drob> getQueueDrobOnRoad() {
		if (queueDrobOnRoad == null) {
			queueDrobOnRoad = new QueueForTransactions<Drob>(
					"Cамоскиди, що у дорозі", dispatcher);
		}
		return queueDrobOnRoad;
	}

	// //////////////////////////////////////////////////////
	// Методи відкладеного створення накопичувачів статистики
	// //////////////////////////////////////////////////////

	// Метод доступу до гістограми для розміру бункеру
	
	private Histo getHistoBunk() {
		if (histoBunk == null)
			histoBunk = new Histo();
		return histoBunk;
	}
	// Метод доступу до гістограми для розміру площадки
	private Histo getHistoArea() {
		if (histoArea == null)
			histoArea = new Histo();
		return histoArea;
	}
	
	// Метод доступу до гістограми для довжини черги до навантажувача
	public DiscretHisto getHistoForQueueToExcav() {
		if (histoForQueueToExcav == null) {
			histoForQueueToExcav = new DiscretHisto();
		}
		return histoForQueueToExcav;
	}

	// Метод доступу до гістограми для часу простою навантажувача
	public Histo getHistoExcav() {
		if (histoExcav == null) {
			histoExcav = new Histo();
		}
		return histoExcav;
	}

	
	 // Метод доступу до гістограми для часу простою самоскида
	public Histo getHistoDrob() {
		if (histoDrob == null) {
			histoDrob = new Histo();
		}
		return histoDrob;
	}

	// ///////////////////////////////////////////////////////////
	// Методи ініціалізації моделі та реалізація інтерфейсів
	// //////////////////////////////////////////////////////////////

	 // Ініціалізація для режиму "Тест"
	public void initForTest() {
		// Передаємо чергам painter-ів для динамічної індикації
		getBunk().setPainter(gui.getDiagramHeepSize().getPainter());
		getArea().setPainter(gui.getDiagramAreaSize().getPainter());
		getQueueDrobOnRoad()
				.setPainter(gui.getDiagramDrobOnRoad().getPainter());
		if (gui.getJCheckBox().isSelected())
			dispatcher.setProtocolFileName("Console");
	}

	// Ініціалізація для режиму "Статистика" 
	@Override
	public void initForStatistics() {

	}

	@Override
	public Map<String, IHisto> getStatistics() {
		Map<String, IHisto> map = new HashMap<>();
		map.put("Гістограма для довжини черги до Ескаватору",
				getHistoForQueueToExcav());
		map.put("Гістограма для розмірів Бункеру ", getHistoBunk());
		map.put("Гістограма для розмірів площадки ", getHistoArea());
		map.put("Гістограма для часу простою Самоскида", getHistoDrob());
		map.put("Гістограма для часу простою Ескаватру", getHistoExcav());
		return map;
	}

	// Реалізація інтерфейсу IExperimentable
	public void initForExperiment(double factor) {
		multiDrob.setNumberOfClones((int) factor);
	}

	public Map<String, Double> getResultOfExperiment() {
		Map<String, Double> resultMap = new HashMap<>();
		resultMap.put("Час простою Самоскидів від їх кількості", getHistoDrob()
				.getAverage());
		resultMap.put("Розмір бункеру від кількості Самоскидів", getHistoBunk().average());
		resultMap.put("Розмір площадки від кількості Самоскидів", getHistoArea().average());
		resultMap.put("Час простою екскаватору від кількості Самоскидів",
				getHistoExcav().getAverage());
		return resultMap;
	}


	public void initForTrans(double finishTime) {
		getExcav().setFinishTime(finishTime);
		getDrob().setFinishTime(finishTime);
		gui.getChooseDataFinishTime().setDouble(finishTime);

	}



	@Override
	public Map<String, ITransMonitoring> getMonitoringObjects() {
		Map<String, ITransMonitoring> transMap = new HashMap<>();
		transMap.put("Руда в бункері", getBunk());
		transMap.put("Черга до екскаватору", getQueueToExcav());
		transMap.put("Самоскиди на шляхах", getQueueDrobOnRoad());
		return transMap;
	}
}
