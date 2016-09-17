package View.workspacepanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import model.bean.Product;
import Util.ImprovedFormattedTextField;
import Util.mytable.MyCellEditor;
import Util.mytable.MyTableCellRenderer;
import Util.mytable.MyTableModel;
import control.TabakoController;

public class EladasPanel extends JPanel {

	private static final long serialVersionUID = 6859816001404065932L;

	private ImprovedFormattedTextField 	field;
	private TabakoController 		  control;
	private VetelezesPanel 				vetel;
	private JScrollPane 	scrollPane_Eladas;
	private JTable 				  eladasTable;
		
	
	public EladasPanel(TabakoController control, VetelezesPanel vetel){
		this.field = new ImprovedFormattedTextField(NumberFormat.getInstance());
		this.control = control;
		this.vetel = vetel;
		create();
	}
	
	private void create(){
	
		GridBagLayout gbl_eladasPanel = new GridBagLayout();
		gbl_eladasPanel.columnWidths = new int[]{0, 0};
		gbl_eladasPanel.rowHeights = new int[]{0, 0};
		gbl_eladasPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_eladasPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		this.setLayout(gbl_eladasPanel);
		
		//Table
		MyTableModel  tableModel = new MyTableModel(new Object[]{	"Megnevezés", "Nyitó készlet", "Vételezés", "Összesen", 
																	"Maradvány", "Fogyás", "Eladási ár", "Eladási érték"},0,2);
		for(Product item : control.getProductList()){
			
			String quantity = control.numToView(item.getQuantity().setScale(2, RoundingMode.CEILING).toString());
            tableModel.addRow(new Object[]{	item.getName(), quantity, "", quantity, "", "", item.getPrice().setScale(0, RoundingMode.CEILING), ""});
		}
		
		
		tableModel.addRow(new Object[]{"Apró", "", "", "", "", "", "", ""});
		tableModel.addRow(new Object[]{"Váltó", "", "", "", "", "", "", ""});
		tableModel.addRow(new Object[]{"", "", "", "", "", "", "", ""});
		
		eladasTable = new JTable();
		eladasTable.setModel(tableModel);
		eladasTable.setRowHeight(30);
	    eladasTable.getColumnModel().getColumn(4).setCellEditor(new MyCellEditor(field));
	    
	    for(int i=1; i<eladasTable.getColumnCount(); i++)
	    	eladasTable.getColumnModel().getColumn(i).setCellRenderer(new MyTableCellRenderer());

	    eladasTable.getModel().addTableModelListener(editRow());
		
		scrollPane_Eladas = new JScrollPane(eladasTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane_Raktar = new GridBagConstraints();
		gbc_scrollPane_Raktar.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_Raktar.gridx = 0;
		gbc_scrollPane_Raktar.gridy = 0;
		this.add(scrollPane_Eladas, gbc_scrollPane_Raktar);

		
		//Button
//		btnSzamol = new JButton("Számol");
//		GridBagConstraints gbc_btnSzamol = new GridBagConstraints();
//		gbc_btnSzamol.fill = GridBagConstraints.BOTH;
//		gbc_btnSzamol.insets = new Insets(0, 0, 5, 0);
//		gbc_btnSzamol.gridx = 0;
//		gbc_btnSzamol.gridy = 2;
//		this.add(btnSzamol, gbc_btnSzamol);
		
//		setListeners();
		updateVetelezes();
		
		this.setVisible(false);
	}
	

	public JTable getEladasTable() {
		return eladasTable;
	}
	
	private TableModelListener editRow(){
		
		TableModelListener listener;
		listener = new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {

				int row = e.getFirstRow();
				int col = e.getColumn();

				
				if(col == 4 && eladasTable.getValueAt(row, col)!=null && eladasTable.getValueAt(row, col)!="")
					calcRow(row);
				else if(col == 4 && (eladasTable.getValueAt(row, col)==null || eladasTable.getValueAt(row, col)=="")){
					 eladasTable.setValueAt("", row, 5);
					 eladasTable.setValueAt("0", row, 7);
					 updateEladasSum();
				}
			}
		};
		
		return listener;
	}

	public void updateVetelezes(){
		
		Iterator<Entry<String, BigDecimal>> it = vetel.getProductWithSums().entrySet().iterator();
		int rowIndex = 0;
	    while (it.hasNext()) {
	    	
	        Map.Entry<String, BigDecimal> pair = (Map.Entry<String, BigDecimal>)it.next();
	        BigDecimal value = pair.getValue().setScale(2, RoundingMode.CEILING);
	        
	        if(value.compareTo(new BigDecimal(0))==1) //if(value == 0)
	        	eladasTable.setValueAt(control.numToView(value.toString()), rowIndex, 2);	
	        else
	        	eladasTable.setValueAt("", rowIndex, 2);
	        
	        rowIndex++;
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    vetel.setValueChanged(false);
	    control.szamolEladas(eladasTable);
	}

	public BigDecimal getSum(){
		Object value = eladasTable.getValueAt(eladasTable.getRowCount()-1, 7).toString();
		if(value!=null && !value.toString().isEmpty())
			return new BigDecimal(value.toString());
		else
			return new BigDecimal(0);
	
	}

	public void calcRow(int row){
		
		if(row == eladasTable.getRowCount()-2 || row == eladasTable.getRowCount()-3 ){
			Object value = eladasTable.getModel().getValueAt(row, 4);
			BigDecimal bigValue = control.stringToBig(value.toString());
			eladasTable.setValueAt(bigValue.setScale(0, RoundingMode.CEILING), row, 7);
			updateEladasSum();
			return;
		}
		
		Object objVetelezes = eladasTable.getModel().getValueAt(row, 2);
		Object objMaradvany = eladasTable.getModel().getValueAt(row, 4);
		Object objNyito 	 = eladasTable.getModel().getValueAt(row, 1);

//		BigDecimal sum = control.stringToBig(eladasTable.getValueAt(eladasTable.getRowCount()-1, 7).toString());		
		BigDecimal osszesen = new BigDecimal(0);

		if(objVetelezes != null && !objVetelezes.toString().isEmpty()){ //Vetelezestol fuggoen kiszamitjuk az osszesent
			
			BigDecimal nyito    = control.stringToBig(objNyito.toString());
			BigDecimal vetelezes = control.stringToBig(objVetelezes.toString());
			osszesen = nyito.add(vetelezes);
			eladasTable.setValueAt(control.numToView(osszesen.toString()), row, 3);
	
		}else{
			osszesen = control.stringToBig(eladasTable.getValueAt(row, 1).toString());
//			osszesen = new BigDecimal(eladasTable.getValueAt(row, 1).toString());
			eladasTable.setValueAt(control.numToView(osszesen.toString()), row, 3);
		}
		
		
		if(objMaradvany != null && !objMaradvany.toString().isEmpty()){ //Fogyas szamitasa
			
			BigDecimal maradvany = control.stringToBig(objMaradvany.toString());
			BigDecimal fogyas = new BigDecimal(0);

			if(maradvany.compareTo(new BigDecimal(0))==-1){
				
				eladasTable.setValueAt("", row, 5);
				maradvany = new BigDecimal(0);
				eladasTable.setValueAt("",row, 4);
				
			}else
				fogyas = osszesen.subtract(maradvany);
			
			eladasTable.setValueAt(control.numToView(fogyas.toString()), row, 5);

			BigDecimal ar = control.stringToBig((eladasTable.getValueAt(row, 6).toString()));
			BigDecimal value = fogyas.multiply(ar);

//			sum = sum.add(value);

			eladasTable.setValueAt(value.setScale(0, RoundingMode.CEILING), row, 7);
			
		}else{// ha nem volt fogyas
			eladasTable.getModel().setValueAt(0, row, 7);
		}
		
		//vegul az osszes eladast is frissítjük az utolso cellaban
		updateEladasSum();
//		eladasTable.setValueAt(updateEladasSum().setScale(0, RoundingMode.CEILING), eladasTable.getRowCount()-1, 7);
	}
	
	
	private BigDecimal updateEladasSum(){
		
		BigDecimal sum = new BigDecimal(0);
		for(int i = 0; i<eladasTable.getRowCount()-1; i++){
			BigDecimal value = control.stringToBig(eladasTable.getValueAt(i, 7).toString());
			sum = sum.add(value);
		}
		eladasTable.setValueAt(sum.setScale(0, RoundingMode.CEILING), eladasTable.getRowCount()-1, 7);
		return sum;
	}
	
}
