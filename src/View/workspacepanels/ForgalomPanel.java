package View.workspacepanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import model.bean.CircItem;
import Util.ImprovedFormattedTextField;
import Util.mytable.ButtonColumn;
import Util.mytable.MyCellEditor;
import Util.mytable.MyLostCellFocusListener;
import Util.mytable.MyTableCellRenderer;
import Util.mytable.MyTableModel;
import control.TabakoController;

import java.awt.Insets;

public class ForgalomPanel extends JPanel{

	private static final long serialVersionUID = -2562795351551606926L;
	
	private ImprovedFormattedTextField field;
	private TabakoController control;

	private JTable kimenoTable;
	private JScrollPane scrollPane_kimeno;
	
	private JTable bejovoTable;
	private JTable inComeTable;
	private JScrollPane scrollPane_bejovo;
	private JTextField txtResult;
	private JTextField txtJattResult;
	private JTextField txtKassza;
	
	private JTable lottoTable;
	private JScrollPane scrollPane_lotto;
	
	private EladasPanel eladasPanel;
	private JPanel resPanel;
	
	private boolean isEdited;

	private JTable egyebTable;
	
	private BigDecimal sumKimeno;
	private BigDecimal sumBejovo;
	@SuppressWarnings("unused")
	private BigDecimal sumLotto;
	
	
	
	
	
	//CONSTRUCTOR
	public ForgalomPanel(TabakoController control, EladasPanel eladasPanel){
		this.eladasPanel = eladasPanel;
		this.control = control;
		
		sumKimeno = new BigDecimal(0);
		sumBejovo = new BigDecimal(0);
		sumLotto = new BigDecimal(0);
		
		this.field = new ImprovedFormattedTextField(NumberFormat.getInstance());
		
		create();
	}

	
	//CREATE VIEW
	private void create() {

		GridBagLayout gbl_vetelezesPanel = new GridBagLayout();
		gbl_vetelezesPanel.columnWidths = new int[]{0, 0};
		gbl_vetelezesPanel.rowHeights = new int[]{0, 0};
		gbl_vetelezesPanel.columnWeights = new double[]{1.0, 1.0};
		gbl_vetelezesPanel.rowWeights = new double[]{1.0, 1.0};
		this.setLayout(gbl_vetelezesPanel);


		createKimeno();
		createBejovo();
		
		createLotto();
		createEgyeb();
		
		
		
		this.setVisible(false);
		
	}
	
	private JTable createTable(int id){

		MyTableModel  model;
		
		if(control.isSomeoneWorking()){
			model = new MyTableModel(new Object[]{"Megjegyzés", "Összeg", ""},0,4);

			for(int i = control.getCircSet().size()-1; i>=0; i--){
				CircItem c = control.getCircSet().get(i);
				if(c.getType() == id) // az adatbazisban a bejovo tabla azonositoja az 1
					model.addRow(new Object[]{c.getComment(), c.getAmount(), "-"});
			}
			
			while(model.getRowCount()<4){
				model.addRow(new Object[]{"", "", ""});
			}
		}else{
			
			model = new MyTableModel(new Object[]{"Megjegyzés", "Összeg", ""},0,4);
			model.insertRow(0, new Object[]{"", "", ""});
			model.insertRow(1, new Object[]{"", "", ""});
			model.insertRow(2, new Object[]{"", "", ""});
			model.insertRow(3, new Object[]{"", "", ""});
			
			
		}
		
		JTable table = new JTable();
		table.setModel(model);
		table.setRowHeight(30);
		
		
		table.getColumnModel().getColumn(2).setMaxWidth(33);
		table.getColumnModel().getColumn(2).setMinWidth(33);
		table.getColumnModel().getColumn(2).setResizable(false);
		
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setResizable(false);
		
//		table.getColumnModel().getColumn(0).setMaxWidth(150);
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setResizable(false);
		
		table.setCellSelectionEnabled(true);
		table.setRequestFocusEnabled(true);
		
		new ButtonColumn(table, deleteAction(), 2);
		field.addFocusListener(new MyLostCellFocusListener(table, field, "cellListener"));
		
		table.getColumnModel().getColumn(1).setCellEditor(new MyCellEditor(field));
		new ButtonColumn(table, deleteAction(), 2);
		
		table.addFocusListener(new MyLostCellFocusListener(table, field, "tableListener"));
		table.getModel().addTableModelListener(editListener());

		
		return table;
	}
	
	
	
	//CREATE PANELS
	
	private void createKimeno(){
		
		JPanel container = new JPanel();
		GridBagLayout gbl_container = new GridBagLayout();
		gbl_container.columnWidths = new int[]{0, 0};
		gbl_container.rowHeights = new int[]{0, 0};
		gbl_container.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_container.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		container.setLayout(gbl_container);

		container.setBorder(	new TitledBorder(	UIManager.getBorder("TitledBorder.border"), 
													"Kimenõ", 
													TitledBorder.CENTER, 
													TitledBorder.BELOW_TOP, 
													new Font("Serif", Font.BOLD, 18), 
													new Color(0, 0, 0)));
		
		kimenoTable = createTable(1); // az adatbazisban a bejovo tabla azonositoja az 1
		kimenoTable.getColumnModel().getColumn(1).setCellRenderer(new MyTableCellRenderer());
		kimenoTable.getColumnModel().getColumn(1).setCellEditor(new MyCellEditor(field));
		kimenoTable.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("elvesztette a focust");
				int row = kimenoTable.getSelectedRow();
				int col = kimenoTable.getSelectedColumn();
//				kimenoTable.getCellEditor().stopCellEditing();
////				kimenoTable.requestFocus();
				
				if(col == 0 || col ==1){
					Object value = kimenoTable.getCellEditor().getCellEditorValue();
					System.out.println("value");
					kimenoTable.setValueAt(value, row, col);
					kimenoTable.getCellEditor().cancelCellEditing();
					
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//scroll pane
		scrollPane_kimeno = new JScrollPane(kimenoTable, 
											JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
											JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		BigDecimal sum = calcSum((MyTableModel) kimenoTable.getModel());
		String sumString = control.numToView(sum.toString());
		
		scrollPane_kimeno.setBorder(new TitledBorder(	BorderFactory.createEmptyBorder(), 
														"Összesen: " + sumString, 
														TitledBorder.RIGHT, 
														TitledBorder.ABOVE_BOTTOM, 
														new Font("Serif", Font.BOLD, 18), 
														new Color(0, 0, 0)));
		
		GridBagConstraints gbc_scrollPane_leado = new GridBagConstraints();
		gbc_scrollPane_leado.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_leado.gridx = 0;
		gbc_scrollPane_leado.gridy = 0;
		container.add(scrollPane_kimeno, gbc_scrollPane_leado);

		GridBagConstraints gbc_leado = new GridBagConstraints();
		gbc_leado.insets = new Insets(0, 0, 5, 0);
		gbc_leado.fill = GridBagConstraints.BOTH;
		gbc_leado.gridx = 0;
		gbc_leado.gridy = 0;
		this.add(container, gbc_leado);
	}
	
	private void createBejovo(){
		
		JPanel container = new JPanel();
		GridBagLayout gbl_container = new GridBagLayout();
		gbl_container.columnWidths = new int[]{0, 0};
		gbl_container.rowHeights = new int[]{0, 0};
		gbl_container.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_container.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		container.setLayout(gbl_container);
		
		container.setBorder(	new TitledBorder(	UIManager.getBorder("TitledBorder.border"), 
													"Bejövõ", 
													TitledBorder.CENTER, TitledBorder.BELOW_TOP, 
													new Font("Serif", Font.BOLD, 18), 
													new Color(0, 0, 0)));
		
		MyTableModel  model = new MyTableModel(new Object[]{"Tábla", "Összeg"},0);
		model.insertRow(0, new Object[]{"Eladás összesen", eladasPanel.getSum().toString()});
		model.insertRow(1, new Object[]{"Lottó összesen", "0"});
		inComeTable = new JTable();
		inComeTable.setModel(model);
		inComeTable.getColumnModel().getColumn(1).setCellRenderer(new MyTableCellRenderer());
		inComeTable.setRowHeight(30);
		inComeTable.setFont(new Font("Serif", Font.BOLD, 14));
		inComeTable.setEnabled(false);
		
		GridBagConstraints gbc_beSum = new GridBagConstraints();
		gbc_beSum.fill = GridBagConstraints.HORIZONTAL;
		gbc_beSum.insets = new Insets(0, 5, 5, 5);
		gbc_beSum.gridx = 0;
		gbc_beSum.gridy = 0;
		container.add(inComeTable, gbc_beSum);

		bejovoTable = createTable(0); // az adatbazisban a bejovo tabla azonositoja az 0
		bejovoTable.getColumnModel().getColumn(1).setCellRenderer(new MyTableCellRenderer());
	    //scroll pane
		scrollPane_bejovo = new JScrollPane(bejovoTable, 
														JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
														JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		scrollPane_bejovo.setBorder(new TitledBorder(	BorderFactory.createEmptyBorder(), 
														"Összesen: " + control.numToView(calcSum((MyTableModel) bejovoTable.getModel()).toString()), 
														TitledBorder.RIGHT, 
														TitledBorder.ABOVE_BOTTOM, 
														new Font("Serif", Font.BOLD, 18), 
														new Color(0, 0, 0)));
		
		GridBagConstraints gbc_scrollPane_bejovo = new GridBagConstraints();
		gbc_scrollPane_bejovo.ipady = 150;
		gbc_scrollPane_bejovo.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane_bejovo.anchor = GridBagConstraints.NORTH;
		gbc_scrollPane_bejovo.gridx = 0;
		gbc_scrollPane_bejovo.gridy = 1;
		container.add(scrollPane_bejovo, gbc_scrollPane_bejovo);
		

		resPanel = createResultPanel();
		
		
		GridBagConstraints gbc_resPanel = new GridBagConstraints();
		gbc_resPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_resPanel.ipadx = -200;
		gbc_resPanel.insets = new Insets(70, 0, 0, 0);
		gbc_resPanel.gridx = 0;
		gbc_resPanel.gridy = 2;
		container.add(resPanel, gbc_resPanel);
		
		
		
		
		
		GridBagConstraints gbc_bejovo = new GridBagConstraints();
		gbc_bejovo.gridheight = 2;
		gbc_bejovo.insets = new Insets(0, 0, 0, 5);
		gbc_bejovo.fill = GridBagConstraints.BOTH;
		gbc_bejovo.gridx = 1;
		gbc_bejovo.gridy = 0;
		this.add(container, gbc_bejovo);
		
	}
	
	private void createLotto(){
		
		JPanel container = new JPanel();
		GridBagLayout gbl_container = new GridBagLayout();
		gbl_container.columnWidths = new int[]{0, 0};
		gbl_container.rowHeights = new int[]{0, 0};
		gbl_container.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_container.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		container.setLayout(gbl_container);
		
		container.setBorder(	new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
				"Lottó", TitledBorder.CENTER, TitledBorder.BELOW_TOP, 
				new Font("Serif", Font.BOLD, 18), 
				new Color(0, 0, 0)));
		
		// create table
		lottoTable = createTable(2);
		lottoTable.getColumnModel().getColumn(1).setCellRenderer(new MyTableCellRenderer());
		
	    //scroll pane
		scrollPane_lotto = new JScrollPane(	lottoTable, 
											JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
											JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		scrollPane_lotto.setBorder(new TitledBorder(	BorderFactory.createEmptyBorder(), 
				"Összesen: " + control.numToView(calcSum((MyTableModel) lottoTable.getModel()).toString()), 
				TitledBorder.RIGHT, 
				TitledBorder.ABOVE_BOTTOM, 
				new Font("Serif", Font.BOLD, 18), 
				new Color(0, 0, 0)));
		
		GridBagConstraints gbc_scrollPane_lotto = new GridBagConstraints();
		gbc_scrollPane_lotto.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_lotto.gridx = 0;
		gbc_scrollPane_lotto.gridy = 0;
		container.add(scrollPane_lotto, gbc_scrollPane_lotto);
		
		GridBagConstraints gbc_bejovo = new GridBagConstraints();
		gbc_bejovo.insets = new Insets(0, 0, 0, 0);
		gbc_bejovo.fill = GridBagConstraints.BOTH;
		gbc_bejovo.gridx = 0;
		gbc_bejovo.gridy = 1;
		this.add(container, gbc_bejovo);
		
		
		inComeTable.setValueAt(calcSum((MyTableModel) lottoTable.getModel()), 1, 1);
	}

	private void createEgyeb(){
		JPanel container = new JPanel();
		GridBagLayout gbl_container = new GridBagLayout();
		gbl_container.columnWidths = new int[]{0, 0};
		gbl_container.rowHeights = new int[]{0, 0};
		gbl_container.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_container.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		container.setLayout(gbl_container);
		
		// create table
		MyTableModel  egyebModel;
		
		if(control.isSomeoneWorking()){
			egyebModel = new MyTableModel(new Object[]{"Megjegyzés", "Összeg"},0);
			for(int i = control.getCircSet().size()-1; i>=0; i--){
				CircItem c = control.getCircSet().get(i);
				if(c.getType() == 3) // az adatbazisban a bejovo tabla azonositoja a 3
					egyebModel.addRow(new Object[]{c.getComment(), c.getAmount()});
			}
			while(egyebModel.getRowCount()<4){
				egyebModel.addRow(new Object[]{"", ""});
			}
		}else{
			egyebModel = new MyTableModel(new Object[]{"Megjegyzés", "Összeg"},4);
		}
		
		egyebTable = new JTable();
		egyebTable.setModel(egyebModel);
		egyebTable.setRowHeight(30);

		field.addFocusListener(new MyLostCellFocusListener(egyebTable, field, "cellListener"));
		
		egyebTable.getColumnModel().getColumn(1).setCellEditor(new MyCellEditor(field));
		egyebTable.addFocusListener(new MyLostCellFocusListener(egyebTable, field, "tableListener"));
		egyebTable.getModel().addTableModelListener(editListener());
	    
	    //scroll pane
		JScrollPane scrollPane_bejovo = new JScrollPane(egyebTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane_bejovo = new GridBagConstraints();
		gbc_scrollPane_bejovo.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_bejovo.gridx = 0;
		gbc_scrollPane_bejovo.gridy = 0;
		container.add(scrollPane_bejovo, gbc_scrollPane_bejovo);
		
		//button
		JButton bejovoPlus = new JButton("+");
		bejovoPlus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
 //				egyebModel.addRow(new Object[]{"", ""}); // 1.7 miatt
				
			}
		});
		
		GridBagConstraints gbc_bejovoPlus = new GridBagConstraints();
		gbc_bejovoPlus.fill = GridBagConstraints.BOTH;
		gbc_bejovoPlus.gridx = 0;
		gbc_bejovoPlus.gridy = 1;
		container.add(bejovoPlus, gbc_bejovoPlus);
		
		GridBagConstraints gbc_bejovo = new GridBagConstraints();
		gbc_bejovo.fill = GridBagConstraints.BOTH;
		gbc_bejovo.gridx = 1;
		gbc_bejovo.gridy = 1;
//		this.add(container, gbc_bejovo);
	}

	private JPanel createResultPanel(){
				
		JPanel resPanel = new JPanel();
		resPanel.setBorder(new TitledBorder(	UIManager.getBorder("TitledBorder.border"), 
												"", 
												TitledBorder.RIGHT, 
												TitledBorder.ABOVE_BOTTOM, 
												new Font("Serif", Font.BOLD, 18), 
												new Color(0, 0, 0)));
		
		GridBagLayout gbl_resPanel = new GridBagLayout();
		gbl_resPanel.columnWidths = new int[]{0, 0};
		gbl_resPanel.rowHeights = new int[]{0, 0};
		gbl_resPanel.columnWeights = new double[]{0.0, 1.0};
		gbl_resPanel.rowWeights = new double[]{1.0, 1.0};
		resPanel.setLayout(gbl_resPanel);
		
		//bejovo-kimeno
		JLabel beki = new JLabel("Bejövõ - Kimenõ: ");
		beki.setFont(new Font("Serif", Font.BOLD, 22));
		GridBagConstraints gbc_beki = new GridBagConstraints();
		gbc_beki.fill = GridBagConstraints.VERTICAL;
		gbc_beki.anchor = GridBagConstraints.WEST;
		gbc_beki.insets = new Insets(0, 5, 5, 5);
		gbc_beki.gridx = 0;
		gbc_beki.gridy = 0;
		resPanel.add(beki, gbc_beki);
		
		txtResult = new JTextField();
		txtResult.setDisabledTextColor(Color.BLACK);
		txtResult.setEnabled(false);
		txtResult.setBackground(Color.LIGHT_GRAY);
		txtResult.setFont(new Font("Serif", Font.BOLD, 22));
		txtResult.setEditable(false);
		GridBagConstraints gbc_lblBeki = new GridBagConstraints();
		gbc_lblBeki.fill = GridBagConstraints.BOTH;
		gbc_lblBeki.insets = new Insets(0, 5, 5, 0);
		gbc_lblBeki.gridx = 1;
		gbc_lblBeki.gridy = 0;
		resPanel.add(txtResult, gbc_lblBeki);
		

		
		//kasszaban
		JLabel kassza = new JLabel("Kassza: ");
		kassza.setFont(new Font("Serif", Font.BOLD, 22));
		GridBagConstraints gbc_kassza = new GridBagConstraints();
		gbc_kassza.fill = GridBagConstraints.VERTICAL;
		gbc_kassza.anchor = GridBagConstraints.WEST;
		gbc_kassza.insets = new Insets(0, 5, 5, 5);
		gbc_kassza.gridx = 0;
		gbc_kassza.gridy = 1;
		resPanel.add(kassza, gbc_kassza);

		txtKassza = new ImprovedFormattedTextField(NumberFormat.getInstance(Locale.FRANCE));
		txtKassza.setColumns(100);
		txtKassza.setText("");
		txtKassza.setFont(new Font("Serif", Font.BOLD, 22));
		txtKassza.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {

//				String strKassza = txtKassza.getText();
//				txtKassza.setText(control.stringToBig(strKassza).toString());
//				BigDecimal eredmeny    = control.stringToBig(txtResult.getText());
//				BigDecimal kassza      = control.stringToBig(strKassza);
//				txtJattResult.setText(control.numToView(eredmeny.subtract(kassza).toString()));
				
				BigDecimal eredmeny    = control.stringToBig(txtResult.getText());
				BigDecimal kassza      = control.stringToBig(txtKassza.getText());

				txtJattResult.setText(control.numToView(eredmeny.subtract(kassza).toString()));
			}
			
			@Override
			public void focusGained(FocusEvent e) {

				String text = txtKassza.getText();
				
				if(text.isEmpty()){
					txtKassza.setText("");
					return;
				}

				String strValue = "";
				for(int i = 0; i<text.length(); i++){
					if(Character.isDigit(text.charAt(i)))
						strValue+=text.charAt(i);
					
				}
				BigDecimal bigValue = control.stringToBig(strValue);
				txtKassza.setText(bigValue.setScale(0, RoundingMode.CEILING).toString());
				
			}
		});
		txtKassza.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
					
						BigDecimal eredmeny    = control.stringToBig(txtResult.getText());
						BigDecimal kassza      = control.stringToBig(txtKassza.getText());

						txtJattResult.setText(control.numToView(eredmeny.subtract(kassza).toString()));
						txtJattResult.requestFocus();
					}
				
			}
		});
		GridBagConstraints gbc_kasszaField = new GridBagConstraints();
		gbc_kasszaField.fill = GridBagConstraints.BOTH;
		gbc_kasszaField.insets = new Insets(10, 5, 5, 0);
		gbc_kasszaField.gridx = 1;
		gbc_kasszaField.gridy = 1;
		resPanel.add(txtKassza, gbc_kasszaField);
		
		
		//jatt
		JLabel jatt = new JLabel("Jatt: ");
		jatt.setFont(new Font("Serif", Font.BOLD, 22));
		GridBagConstraints gbc_jatt = new GridBagConstraints();
		gbc_jatt.fill = GridBagConstraints.VERTICAL;
		gbc_jatt.anchor = GridBagConstraints.WEST;
		gbc_jatt.insets = new Insets(0, 5, 0, 5);
		gbc_jatt.gridx = 0;
		gbc_jatt.gridy = 2;
		resPanel.add(jatt, gbc_jatt);
		
		txtJattResult = new JTextField();
		txtJattResult.setEnabled(false);
		txtJattResult.setDisabledTextColor(Color.BLACK);
		txtJattResult.setEditable(false);
		txtJattResult.setFont(new Font("Serif", Font.BOLD, 22));
		GridBagConstraints gbc_lblJattResult = new GridBagConstraints();
		gbc_lblJattResult.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblJattResult.insets = new Insets(0, 5, 0, 0);
		gbc_lblJattResult.gridx = 1;
		gbc_lblJattResult.gridy = 2;
		resPanel.add(txtJattResult, gbc_lblJattResult);
			
		
		return resPanel;
		
	}
	
	//LISTENERS
	
	/**
	 * If cellEditor losing its focus set isEdited to true and put delete button to the last cell.
	 * Also update the table's summary field (which is the scrollpane's border title)
	 * */
	private TableModelListener editListener(){
		
		TableModelListener listener;
		listener = new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {

				isEdited = true;

				MyTableModel model = (MyTableModel)e.getSource();
				if(isTableFull(model))
					model.insertRow(model.getRowCount(), new Object[]{"", "", ""});
				
				int row = e.getFirstRow();
				int col = e.getColumn();

				Object comm = model.getValueAt(row, 0);
				Object value = model.getValueAt(row, 1);
				
				if(		comm  != null && !comm.toString().isEmpty()  && 
						value != null && !value.toString().isEmpty() && 
						model.getValueAt(row, 2)==""){
					
					model.setValueAt("-", row, 2);
					
				}

				//update other tables is necessary
				if(model.equals(kimenoTable.getModel())){
					String sumString = control.numToView(calcSum(model).toString());
					((TitledBorder)scrollPane_kimeno.getBorder()).setTitle("Összesen: " + sumString);
					sumKimeno = calcSum(model);
					
					updateInOut();
					scrollPane_kimeno.repaint();
					
					updateInOut();
					selectNextCell(kimenoTable, row, col);
					
				}else if(model.equals(bejovoTable.getModel())){

					updateBejovoSum();
					selectNextCell(bejovoTable, row, col);
					
				}else if(model.equals(lottoTable.getModel())){

					inComeTable.setValueAt(calcSum(model).toString(), 1, 1);	
					
					String lottoString = control.numToView(calcSum(model).toString());
					((TitledBorder)scrollPane_lotto.getBorder()).setTitle("Összesen: " + lottoString);
					sumLotto = calcSum(model);
					scrollPane_lotto.repaint();

					updateBejovoSum();
					
					selectNextCell(lottoTable, row, col);
				}
				
				txtResult.setText(control.numToView(sumBejovo.subtract(sumKimeno).toString()));
				resPanel.repaint();
			}
		};
		
		return listener;
	}
	
	public void updateBejovoSum(){
		
		BigDecimal inCome = calcSum((MyTableModel) inComeTable.getModel());
		BigDecimal bejovo = calcSum((MyTableModel) bejovoTable.getModel());
		BigDecimal result = inCome.add(bejovo);

		sumBejovo = result;
		
		((TitledBorder)scrollPane_bejovo.getBorder()).setTitle("Összesen: " + control.numToView(result.toString()));
		scrollPane_bejovo.repaint();
		
		updateInOut();

	}
	
	private void updateInOut(){
		
		txtResult.setText(control.numToView(sumBejovo.subtract(sumKimeno).toString()));
		
		BigDecimal eredmeny    = control.stringToBig(txtResult.getText());
		
		String kasszaText = txtKassza.getText();
		String kasszaValue = "";
		for(int i = 0; i<kasszaText.length();i++){
			if(Character.isDigit(kasszaText.charAt(i)))
				kasszaValue+=kasszaText.charAt(i);
		}
		BigDecimal kassza = control.stringToBig(kasszaValue);
		BigDecimal result = eredmeny.subtract(kassza);
		
		txtJattResult.setText(control.numToView(result.toString()));
		txtJattResult.requestFocus();
		
	}
	
	
	//SELECTION CONTROL
	
	/**
	 * Control the selection (meg lehetne csiszolni rajta)
	 * */
	private void selectNextCell(JTable table, int row, int col){

		if(col == 0){
			table.requestFocus();
        	table.changeSelection(row, 1, false, false);
//        	table.editCellAt(row, 1);
//        	table.getEditorComponent().requestFocusInWindow();
		}else if(col == 1){
			table.requestFocus();
        	table.changeSelection(row+1, 0, false, false);
//        	table.editCellAt(row+1, 0);
//        	table.getEditorComponent().requestFocusInWindow();
		}
		
		
	}
	

	//CALCULATIONS
	
	/**
	 * Calculate the sum of the given tablemodel's values
	 * */
	private BigDecimal calcSum(MyTableModel model){
		
		BigDecimal result = new BigDecimal(0);
		
		for(int row = 0; row<model.getRowCount(); row++){
			if(model.getValueAt(row, 1) != null && !model.getValueAt(row, 1).toString().isEmpty()){
				Object value = model.getValueAt(row, 1);

				result = result.add(new BigDecimal(control.numberToString(value.toString())));
			}
		}
		
		
		return result;
	}
	
	@SuppressWarnings("unused")
	private void calcResult(){
		txtResult.setText(" " + control.numToView(sumKimeno.subtract(sumBejovo).toString()));
	}
	

	//TABLE INFORMATIONS
	
	/**
	 * Check if there is an empty cell in table(except last column). If not, returns true.
	 * */
	private boolean isTableFull(MyTableModel table){
		
		boolean result = true;
		boolean br = false;
		
		for(int i = 0; i<table.getRowCount(); i++){
			for(int j = 0; j<table.getColumnCount()-1; j++){
				if(table.getValueAt(i, j) == null || table.getValueAt(i, j).toString().isEmpty()){
					result = false;
					br = true;
					break;
				}
			}
			
			if(br)
				break;
		}
		
		
		return result;
		
	}

	
	//ACTIONS
	
	/**
	 * Delete the specified row if it isn't the only one in the table
	 * */
	private Action deleteAction(){

		AbstractAction action;
		action = new AbstractAction() {

			private static final long serialVersionUID = 4032921304811404442L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        
		        Object button = table.getValueAt(modelRow, 2);
		        String buttonType = button.toString();
		        
		        if(buttonType=="-" && table.getRowCount() != 1){
		        	
		        	MyTableModel dm = new MyTableModel(new Object[]{"Comment", "Összeg"},1);
		        	dm.setValueAt(table.getValueAt(modelRow, 0), 0, 0);
		        	dm.setValueAt(table.getValueAt(modelRow, 1), 0, 1);
		        	
		        	JTable tab = new JTable(dm);
		        	tab.setRowHeight(30);
		        	
		        	String[] options = new String[2];
		        	options[0] = new String("Igen");
		        	options[1] = new String("Nem");
		        	
		        	int dialogResult = JOptionPane.showOptionDialog(null,setConfirmPane(tab),"Törlés", 0,JOptionPane.QUESTION_MESSAGE,null,options,null);

		        	if(dialogResult == JOptionPane.YES_OPTION){
		        		((DefaultTableModel)table.getModel()).removeRow(modelRow);
		        	}
		        	 
		        }
		        
		       
		        	
			}
		};
		
		if(!isEdited)
			isEdited = true;
		
		return action;
	}
	
	private JPanel setConfirmPane(JTable table){
		
		JPanel panel = new JPanel();
		GridBagLayout gbl_container = new GridBagLayout();
		gbl_container.columnWidths = new int[]{0, 0};
		gbl_container.rowHeights = new int[]{0, 0};
		gbl_container.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_container.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_container);
		
		JLabel text = new JLabel("Biztosan törlöd a következõ sort?");
		text.setFont(new Font("Serif", Font.BOLD, 18));
		GridBagConstraints gbc_kasszaField = new GridBagConstraints();
		gbc_kasszaField.fill = GridBagConstraints.BOTH;
		gbc_kasszaField.insets = new Insets(0, 5, 5, 5);
		gbc_kasszaField.gridx = 0;
		gbc_kasszaField.gridy = 0;
		panel.add(text, gbc_kasszaField);
		
		
		JTable tab = table;
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.insets = new Insets(0, 5, 5, 5);
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		panel.add(tab, gbc_table);
		
		return panel;
	}
	
	
	
	//GETTER-SETTER
	
 	public JTable getBejovoTable() {
		return bejovoTable;
	}

	public JTable getLeadoTable() {
		return kimenoTable;
	}

	public JTable getLottoTable() {
		return lottoTable;
	}

	public JTable getEgyebTable() {
		return egyebTable;
	}
	
	public JTable getInComeTable() {
		return inComeTable;
	}

	public boolean isEdited() {
		return isEdited;
	}
	
}
