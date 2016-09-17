package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import control.TabakoController;

public class SummaryScreen extends JFrame {

	private static final long serialVersionUID = -129129867786162965L;
	
	private TabakoController control;
	private MainScreen main;
//	private SummaryScreen thisSum;
	
	private JTable raktarTable;
	private JTable eladasTable;
	private JTable vetelTable;
	private JTable bejovoTable;
	private JTable leadoTable;
	private JTable lottoTable;
	private JTable egyebTable;
	
	
	
	private JPanel contentPane;
	private JScrollPane scrollPane;
	

	public SummaryScreen(MainScreen main, TabakoController control){

//		thisSum = this;
		this.control = control;
		this.main = main;
		create();

	}
	
	
	private void create(){
		
		this.setTitle("Összegzés");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

		    @Override
		    public void windowClosing(WindowEvent e) {
		        
		    }
		});

		contentPane = new JPanel();
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{1.0};
//		gbl_contentPane.columnWidths = new int[]{1};
//		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
//		gbl_contentPane.columnWeights = new double[]{0.0};
//		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0,  Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		scrollPane = new JScrollPane(contentPane);
//		scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);	
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); //for faster scrolling

		
		setContentPane(scrollPane);
		setMinimumSize(new Dimension(950, 480));

		
		
		
		
		addRaktarPanel(); 	//0,0
		addEladasPanel(); 	//0,1
		addForgalomPanel(); //0,2
		addVetelezesPanel();//0,3
		
		
		//Button: Vegeztem
		JButton btnVegeztem = new JButton("Végeztem");
		btnVegeztem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.actionBtnVegeztem();
				
			}
		});
		btnVegeztem.setBackground(Color.RED);
		btnVegeztem.setPreferredSize(new Dimension(20, 60));
		
		GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
		gbc_btnNewButton_5.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_5.insets = new Insets(0, 0, 0, 0);
		gbc_btnNewButton_5.gridx = 0;
		gbc_btnNewButton_5.gridy = 4;
		contentPane.add(btnVegeztem, gbc_btnNewButton_5);
		
		this.setLocationRelativeTo(null);
		this.setVisible(false);
		
	}
	
	private void addRaktarPanel(){
		
		//Panel
		JPanel raktarPanel = new JPanel();
//		raktarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Raktár"));
		raktarPanel.setBorder(	new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
								"Raktár", TitledBorder.CENTER, TitledBorder.BELOW_TOP, 
								new Font("Serif", Font.BOLD, 24), 
								new Color(0, 0, 0)));
		
//		raktarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagLayout gbl_raktarPanel = new GridBagLayout();
		gbl_raktarPanel.columnWidths = new int[]{0};
		gbl_raktarPanel.rowHeights = new int[]{0};
		gbl_raktarPanel.columnWeights = new double[]{1.0};
		gbl_raktarPanel.rowWeights = new double[]{0.0};
		raktarPanel.setLayout(gbl_raktarPanel);
		
		//Label
//		JLabel raktarLabel = new JLabel("Raktár");
//		raktarLabel.setFont(new Font("Serif", Font.BOLD, 24));
//		GridBagConstraints gbc_raktarLabel = new GridBagConstraints();
//		gbc_raktarLabel.fill = GridBagConstraints.VERTICAL;
//		gbc_raktarLabel.gridx = 0;
//		gbc_raktarLabel.gridy = 0;
//		raktarPanel.add(raktarLabel, gbc_raktarLabel);
		
		//Table
		raktarTable = new JTable(main.getRaktarTable().getModel());
		raktarTable.setRowHeight(30);
		raktarTable.setEnabled(false);
		JScrollPane scrollPane_raktar = new JScrollPane(raktarTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_raktar.setEnabled(false);
		
		//lehessen gorgetni
		scrollPane_raktar.addMouseWheelListener(new MouseWheelListener() {

		    @Override
		    public void mouseWheelMoved(MouseWheelEvent e) {
//		    	scrollPane_raktar.getParent().dispatchEvent(e);
		    }
		});
		
		GridBagConstraints gbc_raktarTable = new GridBagConstraints();
		gbc_raktarTable.fill = GridBagConstraints.BOTH;
		gbc_raktarTable.gridx = 0;
		gbc_raktarTable.gridy = 2;
		raktarPanel.add(scrollPane_raktar, gbc_raktarTable);
	
		//Add to ContentPane
		GridBagConstraints gbc_raktarPanel = new GridBagConstraints();
		gbc_raktarPanel.fill = GridBagConstraints.BOTH;
		gbc_raktarPanel.insets = new Insets(10, 0, 5, 0);
		gbc_raktarPanel.gridx = 0;
		gbc_raktarPanel.gridy = 0;
		contentPane.add(raktarPanel, gbc_raktarPanel);
		
	}
	
	private void addEladasPanel(){
		
		//Panel
		JPanel eladasPanel = new JPanel();
		eladasPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagLayout gbl_eladasPanel = new GridBagLayout();
		gbl_eladasPanel.columnWidths = new int[]{0, 0};
		gbl_eladasPanel.rowHeights = new int[]{0, 0, 0};
		gbl_eladasPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_eladasPanel.rowWeights = new double[]{1.0, 0.0, 1.0};
		eladasPanel.setLayout(gbl_eladasPanel);
		
		//Label
		JLabel eladasLabel = new JLabel("Eladás");
		eladasLabel.setFont(new Font("Serif", Font.BOLD, 24));
		GridBagConstraints gbc_eladasLabel = new GridBagConstraints();
		gbc_eladasLabel.fill = GridBagConstraints.VERTICAL;
		gbc_eladasLabel.gridx = 0;
		gbc_eladasLabel.gridy = 0;
		eladasPanel.add(eladasLabel, gbc_eladasLabel);
		
		//Table
		eladasTable = new JTable(main.getEladasTable().getModel());
//		eladasTable.setDefaultRenderer(Object.class, main.getEladasTable().getDefaultRenderer(Object.class));
		eladasTable.setRowHeight(30);
		eladasTable.setEnabled(false);
		JScrollPane scrollPane_eladas = new JScrollPane(eladasTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_eladas.setEnabled(false);
		
		//lehessen gorgetni
		scrollPane_eladas.addMouseWheelListener(wheeleUpgrade(scrollPane_eladas));



		GridBagConstraints gbc_eladasTable = new GridBagConstraints();
		gbc_eladasTable.fill = GridBagConstraints.BOTH;
		gbc_eladasTable.gridx = 0;
		gbc_eladasTable.gridy = 2;
		eladasPanel.add(scrollPane_eladas, gbc_eladasTable);
		
		//Add to ContentPane
		GridBagConstraints gbc_eladasPanel = new GridBagConstraints();
		gbc_eladasPanel.insets = new Insets(0, 0, 5, 0);
		gbc_eladasPanel.fill = GridBagConstraints.BOTH;
		gbc_eladasPanel.gridx = 0;
		gbc_eladasPanel.gridy = 1;
		contentPane.add(eladasPanel, gbc_eladasPanel);
		
		
	}
	
	private void addForgalomPanel(){
		
		//Panel
		JPanel forgalomPanel = new JPanel();
		forgalomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagLayout gbl_forgalomPanel = new GridBagLayout();
		gbl_forgalomPanel.columnWidths = new int[]{0, 0};
		gbl_forgalomPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_forgalomPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_forgalomPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0};
		forgalomPanel.setLayout(gbl_forgalomPanel);
		
		//Label: Forgalom
		JLabel forgalomLabel = new JLabel("Forgalom");
		forgalomLabel.setFont(new Font("Serif", Font.BOLD, 24));
		GridBagConstraints gbc_forgalomLabel = new GridBagConstraints();
		gbc_forgalomLabel.fill = GridBagConstraints.VERTICAL;
		gbc_forgalomLabel.gridwidth = 2;
		gbc_forgalomLabel.gridx = 0;
		gbc_forgalomLabel.gridy = 0;
		forgalomPanel.add(forgalomLabel, gbc_forgalomLabel);
		
		//---- BEJOVO
		//Label: Bejovo
		JLabel bejovoLabel = new JLabel("Bejövõ forgalom");
		bejovoLabel.setFont(new Font("Serif", Font.BOLD, 18));
		GridBagConstraints gbc_bejovoLabel = new GridBagConstraints();
		gbc_bejovoLabel.fill = GridBagConstraints.VERTICAL;
		gbc_bejovoLabel.gridx = 0;
		gbc_bejovoLabel.gridy = 1;
		forgalomPanel.add(bejovoLabel, gbc_bejovoLabel);
		
		//Table: bejovo
		bejovoTable = new JTable(main.getBejovoTable().getModel());
		bejovoTable.setRowHeight(30);
		bejovoTable.setEnabled(false);
		JScrollPane scrollPane_bejovo = new JScrollPane(bejovoTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_bejovo.setEnabled(false);
		scrollPane_bejovo.addMouseWheelListener(wheeleUpgrade(scrollPane_bejovo));
		
		GridBagConstraints gbc_bejovoTable = new GridBagConstraints();
		gbc_bejovoTable.fill = GridBagConstraints.BOTH;
		gbc_bejovoTable.gridx = 0;
		gbc_bejovoTable.gridy = 2;
		forgalomPanel.add(scrollPane_bejovo, gbc_bejovoTable);
		
		//---- LEADO
		//Label: Leado
		JLabel leadoLabel = new JLabel("Leadó forgalom");
		leadoLabel.setFont(new Font("Serif", Font.BOLD, 18));
		GridBagConstraints gbc_leadoLabel = new GridBagConstraints();
		gbc_leadoLabel.fill = GridBagConstraints.VERTICAL;
		gbc_leadoLabel.gridx = 1;
		gbc_leadoLabel.gridy = 1;
		forgalomPanel.add(leadoLabel, gbc_leadoLabel);
		
		//Table: leado
		leadoTable = new JTable(main.getLeadoTable().getModel());
		leadoTable.setRowHeight(30);
		leadoTable.setEnabled(false);
		JScrollPane scrollPane_leado = new JScrollPane(leadoTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_leado.setEnabled(false);
		scrollPane_leado.addMouseWheelListener(wheeleUpgrade(scrollPane_leado));
		
		GridBagConstraints gbc_leadoTable = new GridBagConstraints();
		gbc_leadoTable.fill = GridBagConstraints.BOTH;
		gbc_leadoTable.gridx = 1;
		gbc_leadoTable.gridy = 2;
		forgalomPanel.add(scrollPane_leado, gbc_leadoTable);
		
		//---- LOTTO
		//Label: Lotto
		JLabel lottoLabel = new JLabel("Lottó forgalom");
		lottoLabel.setFont(new Font("Serif", Font.BOLD, 18));
		GridBagConstraints gbc_lottoLabel = new GridBagConstraints();
		gbc_lottoLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lottoLabel.gridx = 0;
		gbc_lottoLabel.gridy = 3;
		forgalomPanel.add(lottoLabel, gbc_lottoLabel);
		
		//Table: leado
		lottoTable = new JTable(main.getLottoTable().getModel());
		lottoTable.setRowHeight(30);
		lottoTable.setEnabled(false);
		JScrollPane scrollPane_lotto = new JScrollPane(lottoTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_lotto.setEnabled(false);
		scrollPane_lotto.addMouseWheelListener(wheeleUpgrade(scrollPane_lotto));
		
		GridBagConstraints gbc_lottoTable = new GridBagConstraints();
		gbc_lottoTable.fill = GridBagConstraints.BOTH;
		gbc_lottoTable.gridx = 0;
		gbc_lottoTable.gridy = 4;
		forgalomPanel.add(scrollPane_lotto, gbc_lottoTable);
		
		//---- EGYEB
		//Label: Egyeb
		JLabel egyebLabel = new JLabel("Egyéb forgalom");
		egyebLabel.setFont(new Font("Serif", Font.BOLD, 18));
		GridBagConstraints gbc_egyebLabel = new GridBagConstraints();
		gbc_egyebLabel.fill = GridBagConstraints.VERTICAL;
		gbc_egyebLabel.gridx = 1;
		gbc_egyebLabel.gridy = 3;
		forgalomPanel.add(egyebLabel, gbc_egyebLabel);
		
		//Table: leado
		egyebTable = new JTable(main.getEgyebTable().getModel());
		egyebTable.setRowHeight(30);
		egyebTable.setEnabled(false);
		JScrollPane scrollPane_egyeb = new JScrollPane(egyebTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_egyeb.setEnabled(false);
		scrollPane_egyeb.addMouseWheelListener(wheeleUpgrade(scrollPane_egyeb));
		
		GridBagConstraints gbc_egyebTable = new GridBagConstraints();
		gbc_egyebTable.fill = GridBagConstraints.BOTH;
		gbc_egyebTable.gridx = 1;
		gbc_egyebTable.gridy = 4;
		forgalomPanel.add(scrollPane_egyeb, gbc_egyebTable);
		
		
		
		//Add to ContentPane
		GridBagConstraints gbc_forgalomPanel = new GridBagConstraints();
		gbc_forgalomPanel.insets = new Insets(0, 0, 5, 0);
		gbc_forgalomPanel.fill = GridBagConstraints.BOTH;
		gbc_forgalomPanel.gridx = 0;
		gbc_forgalomPanel.gridy = 2;
		contentPane.add(forgalomPanel, gbc_forgalomPanel);
		
	}
	
	private void addVetelezesPanel(){
		
		JPanel vetelezesPanel = new JPanel();
		vetelezesPanel.setBorder(	new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
								"Vételezés", TitledBorder.CENTER, TitledBorder.BELOW_TOP, 
								new Font("Serif", Font.BOLD, 24), 
								new Color(0, 0, 0)));
		

		GridBagLayout gbl_vetelezesPanel = new GridBagLayout();
		gbl_vetelezesPanel.columnWidths = new int[]{0, 0};
		gbl_vetelezesPanel.rowHeights = new int[]{0, 0, 0};
		gbl_vetelezesPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_vetelezesPanel.rowWeights = new double[]{0.0, 0.0, 0.0};
		vetelezesPanel.setLayout(gbl_vetelezesPanel);
		
		//Table
		vetelTable = new JTable(main.getVetelTable().getModel());
		vetelTable.setRowHeight(30);
		vetelTable.setEnabled(false);
		JScrollPane scrollPane_vetel = new JScrollPane(vetelTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_vetel.setEnabled(false);
		
		//lehessen gorgetni
		scrollPane_vetel.addMouseWheelListener(wheeleUpgrade(scrollPane_vetel));
		
		GridBagConstraints gbc_vetelTable = new GridBagConstraints();
		gbc_vetelTable.fill = GridBagConstraints.BOTH;
		gbc_vetelTable.gridx = 0;
		gbc_vetelTable.gridy = 2;
		vetelezesPanel.add(scrollPane_vetel, gbc_vetelTable);
	
		//Add to ContentPane
		GridBagConstraints gbc_vetelPanel = new GridBagConstraints();
		gbc_vetelPanel.insets = new Insets(10, 0, 5, 0);
		gbc_vetelPanel.fill = GridBagConstraints.BOTH;
		gbc_vetelPanel.gridx = 0;
		gbc_vetelPanel.gridy = 3;
		contentPane.add(vetelezesPanel, gbc_vetelPanel);
		
	}
	
	private MouseWheelListener wheeleUpgrade(JScrollPane pane){
		
		MouseWheelListener listener;
		
		listener = new MouseWheelListener() {

		    @Override
		    public void mouseWheelMoved(MouseWheelEvent e) {
//		    	pane.getParent().dispatchEvent(e); //ez meg a fent scroll pane raktar az 1.7es compiler miatt lett kikommentezve am jo
		    }
		};
		
		return listener;
	}


	public JTable getRaktarTable() {
		return raktarTable;
	}


	public JTable getEladasTable() {
		return eladasTable;
	}


	public JTable getBejovoTable() {
		return bejovoTable;
	}
	
	
	public JTable getLeadoTable() {
		return leadoTable;
	}


	public JTable getLottoTable() {
		return lottoTable;
	}


	public JTable getEgyebTable() {
		return egyebTable;
	}


	public JTable getVetelTable() {
		return vetelTable;
	}
	
}
