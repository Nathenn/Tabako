package View;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import View.workspacepanels.EladasPanel;
import View.workspacepanels.ForgalomPanel;
import View.workspacepanels.RaktarPanel;
import View.workspacepanels.VetelezesPanel;
import control.TabakoController;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MainScreen extends JFrame{

	private static final long serialVersionUID = 1564028484547836079L;

	private TabakoController control;
	private String name;
	
	private JPanel contentPane;
	private JPanel workSpacePanelContainer;

	private JPanel raktarPanel;
//	private JTable raktarTable;
	private EladasPanel eladasPanel;
//	private JTable eladasTable;
	private ForgalomPanel forgalomPanel;
	private VetelezesPanel vetelezesPanel;
	
	private JTable bejovoTable;
	private JTable leadoTable;
	private JTable lottoTable;
	private JTable egyebTable;
	
	private JScrollPane scrollPane_Raktar;
	private JScrollPane scrollPane_Eladas;
	private JScrollPane scrollPane_bejovo;
	private JScrollPane scrollPane_leado;
	private JScrollPane scrollPane_lotto;
	private JScrollPane scrollPane_egyeb;
	
	
	private JButton btnVetelezes;
	private JButton btnRaktar;
	private JButton btnEladas;
	private JButton btnForgalom;
	private JButton btnVegeztem;
	private JButton btnLogout;
	private JButton btnExit;
	

	//KONSTRUKTOR
	public MainScreen(TabakoController tabakoController, String name) {
		
		this.name = name;
		this.setTitle("Tabako - [" + name + "]");
		this.control = tabakoController;

		init();
	}
	
	private void init(){
		
		this.setName(name);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 580);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{183, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		createButtonPanel();
		createWorkSpacePanel();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	//MAIN PANELS
	private void createButtonPanel() {
		
		JPanel pnlBtnPanel = new JPanel();
		GridBagConstraints gbc_pnlBtnPanel = new GridBagConstraints();
		gbc_pnlBtnPanel.insets = new Insets(0, 0, 5, 5);
		gbc_pnlBtnPanel.fill = GridBagConstraints.BOTH;
		gbc_pnlBtnPanel.gridx = 0;
		gbc_pnlBtnPanel.gridy = 0;
		contentPane.add(pnlBtnPanel, gbc_pnlBtnPanel);
		
		GridBagLayout gbl_pnlBtnPanel = new GridBagLayout();
		gbl_pnlBtnPanel.columnWidths = new int[]{0, 0};
		gbl_pnlBtnPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnlBtnPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlBtnPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		pnlBtnPanel.setLayout(gbl_pnlBtnPanel);
		
		btnRaktar = new JButton("Raktár");
		btnRaktar.setFont(new Font("Serif", Font.BOLD, 18));
		btnRaktar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				control.displayPanel(raktarPanel);
				colorButton(btnRaktar);
			}
		});
		GridBagConstraints gbc_btnRaktar = new GridBagConstraints();
		gbc_btnRaktar.fill = GridBagConstraints.BOTH;
		gbc_btnRaktar.insets = new Insets(0, 0, 5, 0);
		gbc_btnRaktar.gridx = 0;
		gbc_btnRaktar.gridy = 1;
		pnlBtnPanel.add(btnRaktar, gbc_btnRaktar);
		
		btnVetelezes = new JButton("Vételezés");
		btnVetelezes.setBackground(new Color(100,150,200, 200));
		btnVetelezes.setFont(new Font("Serif", Font.BOLD, 18));
		btnVetelezes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.displayPanel(vetelezesPanel);
				colorButton(btnVetelezes);
				
			}
		});
		GridBagConstraints gbc_btnVetelezes = new GridBagConstraints();
		gbc_btnVetelezes.fill = GridBagConstraints.BOTH;
		gbc_btnVetelezes.insets = new Insets(0, 0, 5, 0);
		gbc_btnVetelezes.gridx = 0;
		gbc_btnVetelezes.gridy = 0;
		pnlBtnPanel.add(btnVetelezes, gbc_btnVetelezes);
		
		btnEladas = new JButton("Eladás");
		btnEladas.setFont(new Font("Serif", Font.BOLD, 18));
		btnEladas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(vetelezesPanel.isValueChanged())
					eladasPanel.updateVetelezes();
				
				control.displayPanel(eladasPanel);
				colorButton(btnEladas);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 2;
		pnlBtnPanel.add(btnEladas, gbc_btnNewButton_1);
		
		btnForgalom = new JButton("Forgalom");
		btnForgalom.setFont(new Font("Serif", Font.BOLD, 18));
		btnForgalom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				String eladasSum = control.numToView(eladasPanel.getSum().toString());
				forgalomPanel.getInComeTable().setValueAt(eladasPanel.getSum(), 0, 1);
				forgalomPanel.updateBejovoSum();
				control.displayPanel(forgalomPanel);
				colorButton(btnForgalom);
				
			}
		});
		GridBagConstraints gbc_btnForgalom = new GridBagConstraints();
		gbc_btnForgalom.fill = GridBagConstraints.BOTH;
		gbc_btnForgalom.insets = new Insets(0, 0, 5, 0);
		gbc_btnForgalom.gridx = 0;
		gbc_btnForgalom.gridy = 3;
		pnlBtnPanel.add(btnForgalom, gbc_btnForgalom);
		
		btnVegeztem = new JButton("Végeztem");
		btnVegeztem.setFont(new Font("Serif", Font.BOLD, 18));
		btnVegeztem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				colorButton(btnVegeztem);
				
				//TODO: ell h minden tabla helyes e es ki van e toltve
				
				String[] options = new String[2];
	        	options[0] = new String("Igen");
	        	options[1] = new String("Nem");
	        	
	        	int dialogResult = JOptionPane.showOptionDialog(null,"Biztos, hogy befejezted a munkát?",
	        			"Törlés", 0,JOptionPane.QUESTION_MESSAGE,null,options,null);

	        	if(dialogResult == JOptionPane.YES_OPTION)
	        		control.actionBtnVegeztem();
				
				
			}
		});
		GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
		gbc_btnNewButton_5.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_5.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_5.gridx = 0;
		gbc_btnNewButton_5.gridy = 4;
		pnlBtnPanel.add(btnVegeztem, gbc_btnNewButton_5);
		
		btnLogout = new JButton("Kijelentkezés");
		btnLogout.setFont(new Font("Serif", Font.BOLD, 18));
		btnLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				colorButton(btnLogout);
				control.logOut();
				
			}
		});
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_4.gridx = 0;
		gbc_btnNewButton_4.gridy = 5;
		pnlBtnPanel.add(btnLogout, gbc_btnNewButton_4);
		
		btnExit = new JButton("Kilépés");
		btnExit.setFont(new Font("Serif", Font.BOLD, 18));
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				colorButton(btnExit);
				control.exitApp();
				
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 6;
		pnlBtnPanel.add(btnExit, gbc_btnNewButton_2);
		
	}

	private void createWorkSpacePanel() {
		
		workSpacePanelContainer = new JPanel();
		GridBagConstraints gbc_workSpacePanelContainer = new GridBagConstraints();
		gbc_workSpacePanelContainer.fill = GridBagConstraints.BOTH;
		gbc_workSpacePanelContainer.gridx = 1;
		gbc_workSpacePanelContainer.gridy = 0;
		contentPane.add(workSpacePanelContainer, gbc_workSpacePanelContainer);
		
		GridBagLayout gbl_workSpacePanelContainer = new GridBagLayout();
		gbl_workSpacePanelContainer.columnWidths = new int[]{0, 0};
		gbl_workSpacePanelContainer.rowHeights = new int[]{0, 0};
		gbl_workSpacePanelContainer.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_workSpacePanelContainer.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		workSpacePanelContainer.setLayout(gbl_workSpacePanelContainer);
		
		
		raktarPanel = new RaktarPanel(control);
		GridBagConstraints gbc_raktarPanel = new GridBagConstraints();
		gbc_raktarPanel.fill = GridBagConstraints.BOTH;
		gbc_raktarPanel.gridx = 0;
		gbc_raktarPanel.gridy = 0;
		workSpacePanelContainer.add(raktarPanel, gbc_raktarPanel);
		
		vetelezesPanel = new VetelezesPanel(control);
		GridBagConstraints gbc_vetelezesPanel = new GridBagConstraints();
		gbc_vetelezesPanel.fill = GridBagConstraints.BOTH;
		gbc_vetelezesPanel.gridx = 0;
		gbc_vetelezesPanel.gridy = 0;
		workSpacePanelContainer.add(vetelezesPanel, gbc_vetelezesPanel);
		

		eladasPanel = new EladasPanel(control, getVetelezesPanel());
		GridBagConstraints gbc_eladasPanel = new GridBagConstraints();
		gbc_eladasPanel.fill = GridBagConstraints.BOTH;
		gbc_eladasPanel.gridx = 0;
		gbc_eladasPanel.gridy = 0;
		workSpacePanelContainer.add(eladasPanel, gbc_eladasPanel);
		
		
		forgalomPanel = new ForgalomPanel(control, eladasPanel);
		GridBagConstraints gbc_forgalomPanel = new GridBagConstraints();
		gbc_forgalomPanel.fill = GridBagConstraints.BOTH;
		gbc_forgalomPanel.gridx = 0;
		gbc_forgalomPanel.gridy = 0;
		workSpacePanelContainer.add(forgalomPanel, gbc_forgalomPanel);
		
		this.bejovoTable = forgalomPanel.getBejovoTable();
		this.leadoTable = forgalomPanel.getLeadoTable();
		this.lottoTable = forgalomPanel.getLottoTable();
		this.egyebTable = forgalomPanel.getEgyebTable();
		
		
		
	}

	
	//Getter-Setter
	public JPanel[] getWorkSpacePanels() {
		JPanel[] workSpacePanels = {raktarPanel, eladasPanel, forgalomPanel, vetelezesPanel};
		return workSpacePanels;
	}

	public JScrollPane getScrollPane_Raktar() {
		return scrollPane_Raktar;
	}

	public JScrollPane getScrollPane_Eladas() {
		return scrollPane_Eladas;
	}

	public JScrollPane getScrollPane_bejovo() {
		return scrollPane_bejovo;
	}

	public JScrollPane getScrollPane_leado() {
		return scrollPane_leado;
	}

	public JScrollPane getScrollPane_lotto() {
		return scrollPane_lotto;
	}

	public JScrollPane getScrollPane_egyeb() {
		return scrollPane_egyeb;
	}

	public JTable getRaktarTable() {
		return ((RaktarPanel)raktarPanel).getRaktarTable();
	}

	public JTable getEladasTable() {
		return ((EladasPanel)eladasPanel).getEladasTable();
	}
	
	public JTable getVetelTable(){
		return ((VetelezesPanel)vetelezesPanel).getVetelTable();
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

	public VetelezesPanel getVetelezesPanel() {
		return vetelezesPanel;
	}
	
	public ForgalomPanel getForgalomPanel() {
		return forgalomPanel;
	}

	public JPanel getRaktarPanel() {
		return raktarPanel;
	}
	
	private List<JButton> getButtonList(){
		List<JButton> list = new ArrayList<JButton>();
		list.add(btnEladas);
		list.add(btnVetelezes);
		list.add(btnForgalom);
		list.add(btnRaktar);
		list.add(btnLogout);
		list.add(btnExit);
		list.add(btnVegeztem);
		return list;
	}

	private void colorButton(JButton button){
		for(JButton b : getButtonList()){
			b.setBackground(UIManager.getColor("Button.background"));
			
		}
		button.setBackground(new Color(100,150,200, 200));
	}

	public Map<String, BigDecimal> getVetelSum() {
		return vetelezesPanel.getProductWithSums();		
	}
	
	
}
