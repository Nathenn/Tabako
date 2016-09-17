package Util.vetelezes;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ProductCellEditor extends AbstractCellEditor implements TableCellEditor{

	private static final long serialVersionUID = -8125451015223895266L;
	
	ProductCellComponent productComp;
//	int row, column;
	
	  public ProductCellEditor() {
		  productComp = new ProductCellComponent();
	  }
	 
	  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	    
		  
		  
		    productComp.updateData(value.toString(), isSelected, table);
		    if (isSelected) {
		    	productComp.setBackground(table.getSelectionBackground());
	        } else {
	        	productComp.setBackground(table.getBackground());
	        }
        
	    return productComp;
	  }
	 
	  public Object getCellEditorValue() {
	    return null;
	  }
	  
	  @Override
	  public boolean stopCellEditing() {

//	    if(this.productComp.isFocusOwner()){
//	    	return false;
//	    }else
//	    	return true;
	    
	    if(this.productComp.hasFocus()){
	    	return false;
	    }else
	    	return true;
	    
	  }

	

	
}
