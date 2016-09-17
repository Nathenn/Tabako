package Util;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class LineWrapCellRenderer  extends JTextArea implements TableCellRenderer {

	private static final long serialVersionUID = -3831683742444991818L;

	int textLength;
	
	@Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
		
		
        this.setText((String)value);
        this.setWrapStyleWord(true);            
        this.setLineWrap(true);       
                
        textLength = ((String)value).length();
        
        return this;
    }
	
	public int getNewHeight(){
		System.out.println(this.getText());
      int fontHeight = this.getFontMetrics(this.getFont()).getHeight();
      int textLength = this.textLength;
      int lines = textLength / (this.getColumns() +1);//+1, cause we need at least 1 row.           
      int height = fontHeight * lines; 
      return height;
	}

}