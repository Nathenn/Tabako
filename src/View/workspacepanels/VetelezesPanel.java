package View.workspacepanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import model.bean.Product;
import model.bean.PurchItem;
import Util.ImprovedFormattedTextField;
import Util.mytable.ButtonColumn;
import Util.mytable.MyCellEditor;
import Util.mytable.MyTableCellRenderer;
import Util.mytable.MyTableModel;
import control.TabakoController;

public class VetelezesPanel extends JPanel {

	private static final long serialVersionUID = -8176033037262907377L;

	private TabakoController control;
	private JTable vetelTable;
	
	private boolean valueChanged = false;
	private boolean isEdited = false;

	
	
	//CONSTRUCTOR
	public VetelezesPanel(TabakoController control){
		this.control = control;
		create();
	}
	
	
	//CREATE VIEW
	private void create(){
		
		GridBagLayout gbl_vetelezesPanel = new GridBagLayout();
		gbl_vetelezesPanel.columnWidths = new int[]{0, 0};
		gbl_vetelezesPanel.rowHeights = new int[]{0, 0};
		gbl_vetelezesPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_vetelezesPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		this.setLayout(gbl_vetelezesPanel);
		
		this.setBorder(	new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
				"Vételezés", TitledBorder.CENTER, TitledBorder.BELOW_TOP, 
				new Font("Serif", Font.BOLD, 24), 
				new Color(0, 0, 0)));
		
		
		createTable();

		JScrollPane scrollPane_vetelTable = new JScrollPane(vetelTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane_vetelTable = new GridBagConstraints();
		gbc_scrollPane_vetelTable.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_vetelTable.gridx = 0;
		gbc_scrollPane_vetelTable.gridy = 0;
		this.add(scrollPane_vetelTable, gbc_scrollPane_vetelTable);
		
		
		this.setVisible(true);	
	}
	
	private void createTable(){
		
		//Model
		MyTableModel  model = new MyTableModel(new Object[]{"Megnevezés", "Mennyiség", "Megjegyzés", "Dátum", ""}, 0, 3);
		
		//Data
		if(control.isSomeoneWorking() && control.getPurchSet().size()!=0){ //load data from database

			for(int i = control.getPurchSet().size()-1; i>=0; i--){
				
				PurchItem item = control.getPurchSet().get(i);
				
				String name = "";
				String lineEnd = "";
				if(!item.getName().isEmpty())
					name = item.getName();
				else
					lineEnd = "-";


				if(!item.getAmount().isEmpty() || !name.isEmpty()) //az uresen hagyott sorokat nem toltjuk vissza
					model.addRow(new Object[]{name, item.getAmount(),  item.getComment(),item.getDate(), lineEnd});
				
			}
			
		}else{
			for(Product item : control.getProductList())
				model.addRow(new Object[]{item.getName(), "", "", "", ""});

		}

		//preferences
		vetelTable = new JTable(model);
		vetelTable.setRowHeight(30);
		vetelTable.setFillsViewportHeight(true);
		vetelTable.getColumnModel().getColumn(1).setCellEditor(new MyCellEditor(new ImprovedFormattedTextField(NumberFormat.getInstance())));
		vetelTable.getColumnModel().getColumn(1).setCellRenderer(new MyTableCellRenderer());
//		vetelTable.getColumnModel().getColumn(1).getCellRenderer().
		vetelTable.getModel().addTableModelListener(editRow());
		vetelTable.putClientProperty("terminateEditOnFocusLost", true);
		vetelTable.setCellSelectionEnabled(true);
		vetelTable.setRequestFocusEnabled(true);
		
		
		new ButtonColumn(vetelTable, addAction(), 0);
		new ButtonColumn(vetelTable, delClearAction(), 4);

	}
	
	
	
	//SUM OF QUANTITY
	
	/**
	 * The given integers represents a cell in a JTable which always will be from column 1 of vetelTable. 
	 * This method returns with a product name, which belongs to the given cell.
	 * */
	@SuppressWarnings("unused")
	private String findItsProductName(int r, int col){
		
		int row = r;

		while((vetelTable.getValueAt(row, 0) != null || vetelTable.getValueAt(row, 0)!="")){
			
			if(vetelTable.getValueAt(row, 0) != null && vetelTable.getValueAt(row, 0)!="")
				return vetelTable.getValueAt(row, 0).toString();

			row--;
		}

		return vetelTable.getValueAt(row, 0).toString();

	}
	
	/**
	 * Get each Product with all of their purchase.
	 * */
	public Map<String, BigDecimal> getProductWithSums(){
		
		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();
		
		for(int row = 0; row<vetelTable.getRowCount(); row++){

			if( vetelTable.getValueAt(row, 0) != null && vetelTable.getValueAt(row, 0)!=""){
				String name = vetelTable.getValueAt(row, 0).toString();
				BigDecimal sum  = calcSumOfProduct(row);
				
//				System.out.println(name+": "); /////comment///////////////////////////////////
				map.put(name, sum);
			}

		}
		
		return map;
	}
	
	/**
	 * Get the sum of purchases of one Product
	 * */
	private BigDecimal calcSumOfProduct(int r){

		Object product = vetelTable.getValueAt(r, 1);
		BigDecimal sum = new BigDecimal(0);

		if(product != null) //ha pl tobb sora van de az elsot kitoroltem uresre
			sum = new BigDecimal(control.numberToString(product.toString()));

		if(getLastRowOfProduct(r) == r) //ha csak ez az egy sora van
			return sum;

		int row = r+1;
		
		if(row >= vetelTable.getRowCount())
			return sum;
		
		
		if(vetelTable.getValueAt(row, 0)!="") //ha a kovi mar masik aru akkor kesz
			return sum;
		
		
		while((vetelTable.getValueAt(row, 0)=="")){

			if(vetelTable.getValueAt(row, 1)!=null && !vetelTable.getValueAt(row, 1).toString().equals("")){
				
				String nextOneInString = control.numberToString(vetelTable.getValueAt(row, 1).toString());
				BigDecimal nextOne = new BigDecimal(nextOneInString);
				BigDecimal temp = sum.add(nextOne);
				sum = temp;
			}
			if(row<vetelTable.getRowCount()-1)
				row++;
			else
				break;
	
		}

		return sum;
		
	}
	
	/**
	 * Get the last inserted row of the given productRow
	 * */
	private int getLastRowOfProduct(int productRow){

//		//ha ez az egy sora van es ez sincs kitoltve tehat ures
		if(vetelTable.getValueAt(productRow, 1)=="")
			return productRow;
		
		int row = productRow;
		while((row+1 != vetelTable.getRowCount() && vetelTable.getValueAt(row+1, 0)=="")){
			row++;
		}
		
		return row;
	}
	
	
	
	//ACTIONS
	
	/**
	 * Add row to the JTable onto the specified index
	 * */
	private Action addAction(){
		
		AbstractAction action;
		action = new AbstractAction() {

			private static final long serialVersionUID = -8381320971821346576L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        int lastRow = getLastRowOfProduct(modelRow);

		        if( vetelTable.getValueAt(lastRow, 1)!=null &&
		        	vetelTable.getValueAt(lastRow, 1).toString() !="" &&
		            !vetelTable.getValueAt(lastRow, 1).toString().isEmpty()){
		        	
		        	((DefaultTableModel)table.getModel()).insertRow(lastRow+1, new Object[]{"", "", "", "", "-"});

			        //jump to the next cell and edit it
			        //mukodnie kene: 
			        //http://stackoverflow.com/questions/11137931/jtable-cell-does-not-fire-key-events-when-editing-is-forced-on-the-cell-through
			        if (table.editCellAt(lastRow, 1)) {
			        	
			        	table.requestFocus();
			        	table.changeSelection(lastRow+1, 1, false, false);
			        	table.editCellAt(lastRow+1, 1);
			        	table.getEditorComponent().requestFocusInWindow();

			          }
		        }
			}
		};
		
		return action;
	}
	
	/**
	 * Delete or clear the specified row. The action depends on the buttons string value.
	 * */
	private Action delClearAction(){
	
		
		AbstractAction action;
		action = new AbstractAction() {

			private static final long serialVersionUID = 4032921304811404442L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        
		        Object button = vetelTable.getValueAt(modelRow, 4);
		        String buttonType = button.toString();
		        
		        if(buttonType=="-"){
		        	((DefaultTableModel)table.getModel()).removeRow(modelRow); //delete
		        	 valueChanged = true;
		        }
		        
		       
		        	
			}
		};
		
		if(!isEdited)
			isEdited = true;
		
		return action;
	}
		
	
	
	//LISTENERS
	
	/**
	 * If cellEditor losing its focus and its value is valid, this method will
	 * fill the specified row with values like date or sum.
	 * */
	private TableModelListener editRow(){
		
		TableModelListener listener;
		listener = new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {

				int row = e.getFirstRow();
				int col = e.getColumn();

				//ha az adott cella nem ures akkor kitoltjuk a tobbit is
				if(col == 1 && vetelTable.getValueAt(row, col)!=null && vetelTable.getValueAt(row, col)!=""){
					
					String date = new SimpleDateFormat("yyyy-MM-dd (HH:mm)").format(Calendar.getInstance().getTime());
					vetelTable.setValueAt(date, row, 3);
					valueChanged = true;
					if(!isEdited) isEdited = true;
				
					
				//ha az adott cella uresre lett torolve es az eppen az aru elso sora
				//itt kell h ha nem az uccso cella akkor ha kitorlom az egeszet akkor az egesz csusszon feljebb
				}
//				else if(col == 1 && row==findItsProductRow(row)){
//
//					vetelTable.setValueAt("",row, 2);
//					vetelTable.setValueAt("",row, 3);
//					vetelTable.setValueAt("",row, 4);
//					
//					System.out.println("prod row lett torolve");
////					((DefaultTableModel)vetelTable.getModel()).removeRow(row); //delete
//					valueChanged=true;
//				}
				else if(col == 1){
					
					vetelTable.setValueAt("",row, 2);
					vetelTable.setValueAt("",row, 3);
					valueChanged=true;
				}
				
			}
		};
		
		return listener;
	}
	
	
	
	
	//GETTER-SETTER
	public JTable getVetelTable(){
		return vetelTable;
	}

	public boolean isValueChanged() {
		return valueChanged;
	}

	public void setValueChanged(boolean valueChanged) {
		this.valueChanged = valueChanged;
	}

	public boolean isEdited() {
		return isEdited;
	}


}


