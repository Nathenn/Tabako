package Util.mytable;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class MyTableCellRenderer extends DefaultTableCellRenderer  {

	private static final long serialVersionUID = 7209931437768698404L;
	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
	

	 @Override
	 public final Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		 
	        final Component result = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	        if (value != null) {
	            setHorizontalAlignment(JLabel.RIGHT);
	            setText(changeAppereance(value.toString()));
	        } else
	            setText("");
	        
	        if(value !=null && column == 5){ //ez csak az eladasra lehet igaz mert annak van legalabb 5 oszlopa
				
				String strValue = value.toString();
				BigDecimal bigvalue = stringToBig(strValue);
				
				if(bigvalue.compareTo(new BigDecimal(0))==-1){//value < 0
					result.setBackground(new Color(250,67,79));
				}else if(isSelected)
					result.setBackground(table.getSelectionBackground());
				else
					result.setBackground(UIManager.getDefaults().getColor("Table.background"));
			}
	        
	        //SET BACKGROUND COLORS
	        if(table.getColumnCount() == 5 && column == 1){ 		//vetelezes tabla
	        	result.setBackground(new Color(150,240,170,70));
	        	result.setForeground(Color.BLACK);
	        }else if(table.getColumnCount() == 8 && column == 4){	//eladas tabla
	        	result.setBackground(new Color(150,240,170,70));
        		result.setForeground(Color.BLACK);
	        }else if(table.getColumnCount() == 4 && column == 2){	//raktar tabla
	        	result.setBackground(new Color(150,240,170,70));
        		result.setForeground(Color.BLACK);
	        }else if(table.getColumnCount() == 3 && column == 1){	//forgalom tabla
	        	result.setBackground(new Color(150,240,170,70));
        		result.setForeground(Color.BLACK);
	        }
	        
	        return result;
	    }

	 //ezreseknel csoportosit
	 private String changeAppereance(String string) {

		 if(string.isEmpty()) return string;
		 
		 String result = "";
		 
		 //calculate the size of integer part if it exists
		 int intPart=0;
		 for(int i=0;i<string.length();i++){
			 if(string.charAt(i)==',' || string.charAt(i)=='.')
				 break;
			 else 
				 intPart++;
		 }
	 

		 for(int i = 0; i<string.length(); i++){
			
			 if(string.charAt(i)==',' || string.charAt(i)=='.'){
				 result+=',';
				 for(int j=i+1; j<string.length();j++)
					 result+=string.charAt(j);
				 break;
			 }else if(intPart%3==0){
				 result+=" ";
				 result+=string.charAt(i);
				 intPart--;
			 }else{
				 result+=string.charAt(i);
				 intPart--;
			 }
			 
		 }
		 
		 return result;
	 }
	 
	public void setBGcolor(Color c){
		setBackground(c);
	}
	

	/**
	 * Change the number format for calculations: 10 234,85 -> 10234.85
	 * */
	public BigDecimal stringToBig(String string){
		
		BigDecimal big = new BigDecimal(0);
		String result = "";
		
		if(string.isEmpty()) 
			return big;
		
		
		for(int i = 0; i<string.length(); i++){
			
			if(string.charAt(i)==' ')
				continue;
				
			else if(string.charAt(i)==','){
				 result+='.';
				 for(int j=i+1; j<string.length();j++)
					 result+=string.charAt(j);
				 break;
				 
			}else
				result+=string.charAt(i);

		 }
		
		big = new BigDecimal(result);
		return big;

	}
	
}
