package Util.mytable;

import java.awt.Color;
import java.util.EventObject;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

import Util.ImprovedFormattedTextField;

public class MyCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 1805467768382027465L;


	public MyCellEditor(ImprovedFormattedTextField field) {
		super(field);
		
	}
	
	@Override
	  public Object getCellEditorValue() {

		String value = (String) super.getCellEditorValue();
		ImprovedFormattedTextField field = (ImprovedFormattedTextField)getComponent();
		System.out.println("field:" + field.getValue() + "value:" + value);
		if(super.getCellEditorValue() == null || value.isEmpty() ){ //ha uresre torlom leheseen kilepni a textfieldbol
			((JTextField)getComponent()).setText("");
			return null;
			
		}else if(field.getValue() == null || field.isEditValid()){

			return value;
		}
		return null;

	  }

	  @Override
	  public boolean stopCellEditing() {
		
	    boolean result = false;
	    ImprovedFormattedTextField f = (ImprovedFormattedTextField)getComponent();
	    
	    if(super.getCellEditorValue().equals(null) || super.getCellEditorValue().toString().equals("") || f.isEditValid()){
	    	
	    	((JTextField)getComponent()).setBorder(BorderFactory.createLineBorder(Color.BLUE));
	    	result = super.stopCellEditing();
	    }else{
	    	((JTextField)getComponent()).setBorder(BorderFactory.createLineBorder(Color.RED));
	    	result = false;
	    }

	    return result;
	  }

	  @Override
	  public boolean isCellEditable(EventObject anEvent) {
	    // reset color when begin editing
		((JTextField)getComponent()).setBorder(BorderFactory.createLineBorder(Color.BLUE));
	    return super.isCellEditable(anEvent);
	  }
	  
	
	 
	  
}
