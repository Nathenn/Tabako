package notused;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

import Util.ImprovedFormattedTextField;

public class MyTestCellEditor extends DefaultCellEditor{

	private static final long serialVersionUID = 3020951404159341086L;

	public MyTestCellEditor(MyTestFormTextField textField) {
		super(textField);
		
	}
	
	public MyTestCellEditor(ImprovedFormattedTextField textField) {
		super(textField);
		
	}
	
	
	@Override
	  public Object getCellEditorValue() {

		String value = (String) super.getCellEditorValue();
		if(super.getCellEditorValue() == null || value.isEmpty() ){ //ha uresre torlom leheseen kilepni a textfieldbol
			((JTextField)getComponent()).setText("");
			return null;
			
		}else{
		}
		return value;
		
	}
}
