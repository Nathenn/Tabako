package Util.vetelezes;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ProductCellRenderer implements TableCellRenderer{

	ProductCellComponent comp;
	
	public ProductCellRenderer(){
		comp = new ProductCellComponent();
		comp.setOpaque(true);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		if (value instanceof ProductCellComponent){
			comp = (ProductCellComponent) value;
			table.getSelectionModel().setSelectionInterval(row, row);
			setSelectedRow(row);
		}
		
		
		
		comp.updateData(value.toString(), isSelected, table);
        
		
		return comp;
	
	}
	
	public void setSelectedRow(int row){
		comp.updateTableData(row);
	}
		
	
	
}
