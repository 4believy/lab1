package Ruda;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import process.IModelFactory;
import rnd.Erlang;
import rnd.Norm;
import process.Dispatcher;
import widgets.ChooseData;
import widgets.ChooseRandom;
import widgets.Diagram;
import widgets.experiments.ExperimentManager;
import widgets.stat.StatisticsManager;
import widgets.trans.TransProcessManager;

public class GUI extends JFrame{

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private Diagram diagramHeepSize = null;
	
	private Diagram diagramAreaSize = null;

	private Diagram diagramDrobOnRoad = null;

	private JButton jButtonStart = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanelTest = null;

	private JScrollPane jScrollPaneTz = null;

	private JPanel jPanelTransient = null;

	private TransProcessManager transProcessManager = null;

	private JPanel jPanelModelParameters = null;

	private Dispatcher dispatcher = null;

	private JCheckBox jCheckBox = null;

	private JPanel jPanelStat = null;

	private ChooseRandom rndExcav = null;

	private ChooseData chooseDataNDrob = null;

	private ChooseData chooseDataBodySize = null;

	private ChooseRandom rndDrob = null;

	private ChooseData chooseDataBunkMaxSize = null;

	private ChooseData chooseDataFinishTime = null;

	private JTextPane jTextPane = null;
	private StatisticsManager statisticsManager;
	private JPanel jPanelRegres;

	public GUI() throws HeadlessException {
		super();
		initialize();
	}


	private JButton getJButtonStart() {
		if (jButtonStart == null) {
			jButtonStart = new JButton();
			jButtonStart.setText("Старт");
			jButtonStart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					startTest();
				}
			});
		}
		return jButtonStart;
	}

	private void startTest() {
		getJButtonStart().setEnabled(false);
		getDiagramHeepSize().clear();
		getDiagramAreaSize().clear();
		getDiagramDrobOnRoad().clear();
		Dispatcher dispatcher = new Dispatcher();		
		dispatcher.addDispatcherFinishListener(
				()->getJButtonStart().setEnabled(true));
		IModelFactory factory = (d)-> new Model(d, this);
		Model model =(Model) factory.createModel(dispatcher);
		model.initForTest();
		dispatcher.start();
	}


	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setName("");
			jTabbedPane.setVisible(true);
			jTabbedPane.setFont(new java.awt.Font("Courier New",
					java.awt.Font.PLAIN, 14));
			jTabbedPane.addTab("Tz", null, getJScrollPaneTz(), null);
			jTabbedPane.addTab("Test", null, getJPanelTest(), null);
			jTabbedPane.addTab("Stat", null, getJPanelStat(),
					"Статистичні характеристики");
			jTabbedPane.addTab("Regres", null, getJPanelRegres(), null);
			jTabbedPane.addTab("Transient", null, getJPanelTransient(), null);

		}
		return jTabbedPane;
	}


	private JPanel getJPanelTest() {
		if (jPanelTest == null) {
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.insets = new java.awt.Insets(4, 8, 3, 2);
			gridBagConstraints13.gridy = 4;
			gridBagConstraints13.gridx = 0;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.insets = new Insets(2, 3, 0, 4);
			gridBagConstraints12.gridy = 4;
			gridBagConstraints12.ipadx = 10;
			gridBagConstraints12.ipady = -1;
			gridBagConstraints12.anchor = GridBagConstraints.EAST;
			gridBagConstraints12.gridwidth = 2;
			gridBagConstraints12.gridx = 2;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.insets = new Insets(1, 4, 1, 4);
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 2;
			gridBagConstraints11.ipadx = 183;
			gridBagConstraints11.ipady = -86;
			gridBagConstraints11.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.gridwidth = 4;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.insets = new Insets(0, 3, 0, 4);
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.ipadx = 183;
			gridBagConstraints10.ipady = -86;
			gridBagConstraints10.weightx = 1.0D;
			gridBagConstraints10.weighty = 1.0D;
			gridBagConstraints10.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints10.gridwidth = 4;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new Insets(4, 3, 0, 4);
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.ipadx = 183;
			gridBagConstraints8.ipady = -86;
			gridBagConstraints8.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints8.weightx = 1.0D;
			gridBagConstraints8.weighty = 2.0D;
			gridBagConstraints8.gridwidth = 4;
			jPanelTest = new JPanel();
			jPanelTest.setLayout(new GridBagLayout());
			jPanelTest.add(getDiagramHeepSize(), gridBagConstraints8);
			jPanelTest.add(getDiagramAreaSize(), gridBagConstraints10);
			jPanelTest.add(getDiagramDrobOnRoad(), gridBagConstraints11);
			jPanelTest.add(getJButtonStart(), gridBagConstraints12);
			jPanelTest.add(getJCheckBox(), gridBagConstraints13);
			jPanelTest
			.addComponentListener(new java.awt.event.ComponentAdapter() {
				public void componentShown(
						java.awt.event.ComponentEvent e) {
					getChooseDataNDrob().select(0,0);
					getChooseDataFinishTime().select(0,0);
					getChooseDataBunkMaxSize().select(0,0);
				}
			});
		}
		return jPanelTest;
	}


	private JScrollPane getJScrollPaneTz() {
		if (jScrollPaneTz == null) {
			jScrollPaneTz = new JScrollPane();
			jScrollPaneTz.setName("jScrollPaneTz");
			jScrollPaneTz.setViewportView(getJTextPane());
			jScrollPaneTz
					.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
		return jScrollPaneTz;
	}


	private JPanel getJPanelTransient() {
		if (jPanelTransient == null) {
			jPanelTransient = new JPanel();
			jPanelTransient.setLayout(new CardLayout(0, 0));
			jPanelTransient.add(getTransProcessManager(), "name_34492577955736");

		}
		return jPanelTransient;
	}


	private TransProcessManager getTransProcessManager() {
		if (transProcessManager == null) {
			transProcessManager = new TransProcessManager();
			transProcessManager.setNumberOfInterval("20");
			transProcessManager.setTextInterval("5");
			transProcessManager.setFactory((d)-> new Model(d, this));

		}
		return transProcessManager;
	}


	private JPanel getJPanelModelParameters() {
		if (jPanelModelParameters == null) {
			jPanelModelParameters = new JPanel();
			jPanelModelParameters.setLayout(null);
			jPanelModelParameters
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createBevelBorder(javax.swing.border.BevelBorder.RAISED),
									"Параметри системи що досліджується",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.BOLD, 12),
									new java.awt.Color(51, 51, 51)));
			jPanelModelParameters.setPreferredSize(new Dimension(262, 436));
			jPanelModelParameters.setMinimumSize(new Dimension(262, 436));
			jPanelModelParameters.add(getRndExcav(), null);
			jPanelModelParameters.add(getChooseDataNDrob(), null);
			jPanelModelParameters.add(getChooseDataBodySize(), null);
			jPanelModelParameters.add(getRndDrob(), null);
			jPanelModelParameters.add(getChooseDataBunkMaxSize(), null);
			jPanelModelParameters.add(getChooseDataFinishTime(), null);
		}
		return jPanelModelParameters;
	}


	public JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox
					.setActionCommand("\u0412\u044b\u0432\u043e\u0434 \u043f\u0440\u043e\u0442\u043e\u043a\u043e\u043b\u0430 \u043d\u0430 \u043a\u043e\u043d\u0441\u043e\u043b\u044c");
			jCheckBox.setText("Протокол на консоль");
			jCheckBox.setBorder(BorderFactory
					.createBevelBorder(BevelBorder.RAISED));
		}
		return jCheckBox;
	}


	private JPanel getJPanelStat() {
		if (jPanelStat == null) {
			jPanelStat = new JPanel();
			jPanelStat.setLayout(new CardLayout(0, 0));
			jPanelStat.add(getStatisticsManager(), "name_131147950583608");
		}
		return jPanelStat;
	}


	public ChooseRandom getRndExcav() {
		if (rndExcav == null) {
			rndExcav = new ChooseRandom();
			rndExcav.setRandom(new Norm(1,0.2));
			rndExcav.setTitle("Продуктивність ескаватору");
			rndExcav.setBounds(new Rectangle(3, 81, 231, 52));
		}
		return rndExcav;
	}


	public ChooseData getChooseDataNDrob() {
		if (chooseDataNDrob == null) {
			chooseDataNDrob = new ChooseData();
			chooseDataNDrob.setBounds(new Rectangle(3, 200, 231, 53));
			chooseDataNDrob.setTitle("Кількість самоскидів");
			chooseDataNDrob.setText("6");
			chooseDataNDrob
			.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					if (getJPanelTest().isShowing()){
						getDiagramDrobOnRoad().setVerticalMaxText(
								chooseDataNDrob.getText());
	
					}
				}
			});

		}
		return chooseDataNDrob;
	}


	public ChooseData getChooseDataBodySize() {
		if (chooseDataBodySize == null) {
			chooseDataBodySize = new ChooseData();
			chooseDataBodySize.setBounds(new Rectangle(4, 263, 231, 53));
			chooseDataBodySize.setTitle("Вмістимість кузову самоскиду");
			chooseDataBodySize.setText("3");
		}
		return chooseDataBodySize;
	}


	public ChooseRandom getRndDrob() {
		if (rndDrob == null) {
			rndDrob = new ChooseRandom();
			rndDrob.setBounds(new Rectangle(3, 142, 231, 52));
			rndDrob.setRandom(new Erlang(20, 4));
			rndDrob.setTitle("Перебування самоскида у дорозі");
		}
		return rndDrob;
	}


	public ChooseData getChooseDataBunkMaxSize() {
		if (chooseDataBunkMaxSize == null) {
			chooseDataBunkMaxSize = new ChooseData();
			chooseDataBunkMaxSize.setBounds(new Rectangle(1, 326, 231, 53));
			chooseDataBunkMaxSize.setTitle("Максимальний розмір бункеру");
			chooseDataBunkMaxSize.setText("100");
			chooseDataBunkMaxSize.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					if (getJPanelTest().isShowing())
						getDiagramHeepSize().setVerticalMaxText(
								chooseDataBunkMaxSize.getText());
				}
			});

		}
		return chooseDataBunkMaxSize;
	}


	public ChooseData getChooseDataFinishTime() {
		if (chooseDataFinishTime == null) {
			chooseDataFinishTime = new ChooseData();
			chooseDataFinishTime.setBounds(new Rectangle(3, 385, 231, 53));
			chooseDataFinishTime.setTitle("Час моделювання");
			chooseDataFinishTime.setText("500");
			chooseDataFinishTime
			.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					if (getJPanelTest().isShowing()) {
						getDiagramHeepSize().setHorizontalMaxText(
								chooseDataFinishTime.getText());
						getDiagramAreaSize().setHorizontalMaxText(
								chooseDataFinishTime.getText());
						getDiagramDrobOnRoad().setHorizontalMaxText(
								chooseDataFinishTime.getText());
					}
				}
			});

		}
		return chooseDataFinishTime;
	}


	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			String str="/ruda/TZ.html";
			URL url = getClass().getResource(str);
			try {
				jTextPane.setPage(url);
			} catch (IOException e33) {
				System.err
						.println("Problems with file "+str);
			}

		}
		return jTextPane;
	}


	public static void main(String[] args) {
		GUI application = new GUI();
		application.setVisible(true);
	}


	private void initialize() {
		this.setSize(949, 499);
		this.setContentPane(getJContentPane());
		this
				.setTitle("RGR Ruda");
	}


	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(9, 10, 7, 2);
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.ipadx = -21;
			gridBagConstraints7.ipady = 13;
			gridBagConstraints7.gridx = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.ipadx = -96;
			gridBagConstraints6.ipady = -294;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.weighty = 1.0;
			gridBagConstraints6.insets = new Insets(8, 3, 6, 7);
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getJTabbedPane(), gridBagConstraints6);
			jContentPane.add(getJPanelModelParameters(), gridBagConstraints7);
		}
		return jContentPane;
	}


	public Boolean getProtocolToConcole() {
		return getJCheckBox().isSelected();
	}



	public Diagram getDiagramHeepSize() {
		if (diagramHeepSize == null) {
			diagramHeepSize = new Diagram();
			diagramHeepSize.setHorizontalMaxText("500");
			diagramHeepSize.setTitleText("Руда в бункері");
			diagramHeepSize.setVerticalMaxText("100");
			diagramHeepSize.setPainterColor(java.awt.Color.red );
		}
		return diagramHeepSize;
	}


	public Diagram getDiagramAreaSize() {
		if (diagramAreaSize == null) {
			diagramAreaSize = new Diagram();
			diagramAreaSize.setHorizontalMaxText("500");
			diagramAreaSize.setTitleText("Руда на площадці");
			diagramAreaSize.setVerticalMaxText("400");
			diagramAreaSize.setPainterColor(java.awt.Color.red);
		}
		return diagramAreaSize;
	}


	public Diagram getDiagramDrobOnRoad() {
		if (diagramDrobOnRoad == null) {
			diagramDrobOnRoad = new Diagram();
			diagramDrobOnRoad.setHorizontalMaxText("500");
			diagramDrobOnRoad
					.setTitleText("Самоскиди у дорозі");
			diagramDrobOnRoad.setVerticalMaxText("10");
			diagramDrobOnRoad.setPainterColor(java.awt.Color.red);
		}
		return diagramDrobOnRoad;
	}

	public Dispatcher getDispatcher() {
		if (dispatcher == null)
			dispatcher = new Dispatcher();
		return dispatcher;
	}


	private StatisticsManager getStatisticsManager() {
		if (statisticsManager == null) {
			statisticsManager = new StatisticsManager();
			statisticsManager.setFactory((d)-> new Model(d, this));
		}
		return statisticsManager;
	}
	private JPanel getJPanelRegres() {
		if (jPanelRegres == null) {
			jPanelRegres = new JPanel();
			jPanelRegres.setLayout(new CardLayout(0, 0));		
			ExperimentManager em = new ExperimentManager();
			em.getChooseDataFactors().setTitle("Кількість самоскидів");
			em.getComboBox().setPreferredSize(new Dimension(328, 20));
			em.getComboBox().setMinimumSize(new Dimension(328, 20));
			em.getDiagram().setHorizontalMaxText("15");
			em.getDiagram().setVerticalMaxText("500");
			em.getChooseDataFactors().setText("1 3 5 7 9 12 14");
			em.setFactory((d)-> new Model(d, this));
			jPanelRegres.add(em, "name_111625814938837");
		}
		return jPanelRegres;
	}
}
