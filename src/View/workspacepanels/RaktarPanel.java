package View.workspacepanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.bean.Product;
import Util.ImprovedFormattedTextField;
import Util.mytable.MyCellEditor;
import Util.mytable.MyTableCellRenderer;
import Util.mytable.MyTableModel;
import control.TabakoController;

public class RaktarPanel extends JPanel {

	private static final long serialVersionUID = 8909992675191296839L;
	
	private JTable raktarTable;
	private TabakoController control;
	private ImprovedFormattedTextField field = new ImprovedFormattedTextField(NumberFormat.getInstance());
	private JButton btnSzamol;
	private JScrollPane scrollPane_Raktar;
	
//	private static final Pattern onlyNumber = Pattern.compile("^?[0-9]{1,12}(?:\\.[0-9]{1,4})?$"); //minusz kiveve az elejerol
	
	
	public RaktarPanel(TabakoController control){
		this.control = control;
		create();
	}
	
	private void create(){

		//Layout
		GridBagLayout gbl_raktarPanel = new GridBagLayout();
		gbl_raktarPanel.columnWidths = new int[]{0, 0};
		gbl_raktarPanel.rowHeights = new int[]{0, 0};
		gbl_raktarPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_raktarPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		this.setLayout(gbl_raktarPanel);
		
		//Table
		MyTableModel  tableModel = new MyTableModel(new Object[]{"Megnevezés", "Raktár", "Hûtõ", "Összesen"},0,1);
		for(Product item : control.getProductList()){
	        tableModel.addRow(new Object[]{item.getName(), item.getQuantity().setScale(2, RoundingMode.CEILING), "", ""});
		}
		raktarTable = new JTable();
		raktarTable.setModel(tableModel);
		raktarTable.setRowHeight(30);
	
		raktarTable.getColumnModel().getColumn(2).setCellEditor(new MyCellEditor(field));
		for(int i=1; i<raktarTable.getColumnCount(); i++){
			raktarTable.getColumnModel().getColumn(i).setCellRenderer(new MyTableCellRenderer());
	    }
	
		//ScrollPane
		scrollPane_Raktar = new JScrollPane(raktarTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane_Raktar = new GridBagConstraints();
		gbc_scrollPane_Raktar.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_Raktar.gridx = 0;
		gbc_scrollPane_Raktar.gridy = 0;
		this.add(scrollPane_Raktar, gbc_scrollPane_Raktar);
		
		//Button
		btnSzamol = new JButton("Számol");
		
		GridBagConstraints gbc_btnSzamol = new GridBagConstraints();
		gbc_btnSzamol.fill = GridBagConstraints.BOTH;
		gbc_btnSzamol.insets = new Insets(0, 0, 5, 0);
		gbc_btnSzamol.gridx = 0;
		gbc_btnSzamol.gridy = 1;
		this.add(btnSzamol, gbc_btnSzamol);
		
		setListeners();
		
		this.setVisible(false);
		
	}
	
	private void setListeners(){
		
		btnSzamol.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) { //tesztelni h eleg e csak mt vagy raktarmodel setvalue
				
				if(raktarTable.isEditing()){
					
					MyCellEditor mc = (MyCellEditor) raktarTable.getCellEditor();
					ImprovedFormattedTextField mt = (ImprovedFormattedTextField) mc.getComponent();
					int row = raktarTable.getSelectedRow();
					int col = raktarTable.getSelectedColumn();
					
					if(mt.isEditValid() && !mt.getText().isEmpty()){
						
						try {
	//						System.out.println("commit");
							mt.commitEdit();
							
							raktarTable.getModel().setValueAt(mt.getValue(), row, col);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
	//						System.out.println("commithiba");
							raktarTable.getCellEditor().stopCellEditing();
						}
					}else if(!mt.isEditValid()){
						mt.invalidEdit();
						mt.setValue(null);
						raktarTable.getModel().setValueAt("", row, col);
	//					System.out.println("invalidEdit");
					}else{
	//					System.out.println("else");
						mt.setValue("");
						raktarTable.getModel().setValueAt("", row, col);
					}
					
					
				}
	
				control.szamolRaktar(raktarTable);
				
			}
		});

	}

	
	public JTable getRaktarTable() {
		return raktarTable;
	}
	
}
