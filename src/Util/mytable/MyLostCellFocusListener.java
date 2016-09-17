package Util.mytable;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFormattedTextField;
import javax.swing.JTable;
/**
 * FocusLost listener for tablecells(cancelEditing) and tables(clearSelection). 
 * */
public class MyLostCellFocusListener implements FocusListener{

	private JTable table;
//	private JFormattedTextField field;
	private String mode;
	
	public MyLostCellFocusListener(JTable table, JFormattedTextField field, String mode){
		this.table = table;
//		this.field = field;
		this.mode = mode;
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		System.out.println("szamol");
		if(mode.equals("cellListener")){
			if(table.isEditing()){
				System.out.println("szamol");
				table.getCellEditor().cancelCellEditing();
			}
		}else if(mode.equals("tableListener")){
			table.clearSelection();
		}else{
			System.err.println("Rossz parameter lett megadva a MyLostCellListenernel!");
		}
		
		
	}

}
