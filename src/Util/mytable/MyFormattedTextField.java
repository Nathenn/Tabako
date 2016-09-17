package Util.mytable;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

import Util.RegexPatternFormatter;


/**
 * Felul lett irva az invalidEdit() metodus hogy ne pittyegjen ha atlepunk masik
 * FormattedTextField-be vagy rossz az input vagy esetleg uresen probalunk torolni
 * */
public class MyFormattedTextField extends JFormattedTextField{


	private static final long serialVersionUID = -7973705038477683604L;

	public MyFormattedTextField(NumberFormatter formatter) {
		super(formatter);
		setFocusLostBehavior(JFormattedTextField.COMMIT);
	}
	
	public MyFormattedTextField(Pattern pt) {
		super(pt);
	}

	public MyFormattedTextField(NumberFormat instance) {
		super(instance);
	}

	public MyFormattedTextField(Float float1) {
		super(float1);
	}

	public MyFormattedTextField(RegexPatternFormatter regexPatternFormatter) {
		super(regexPatternFormatter);
	}

	@Override
	public void invalidEdit(){
		setBorder(BorderFactory.createLineBorder(Color.RED));

	}

	
	
	
	
}
