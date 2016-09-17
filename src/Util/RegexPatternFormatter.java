package Util;

/*
Java Swing, 2nd Edition
By Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
ISBN: 0-596-00408-7
Publisher: O'Reilly 
*/
// RegexPatternFormatter.java
//A formatter for regular expressions to be used with JFormattedTextField.
//

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatter;

public class RegexPatternFormatter extends DefaultFormatter {

	private static final long serialVersionUID = -9055044353741229376L;
	protected java.util.regex.Matcher matcher;

  public RegexPatternFormatter(java.util.regex.Pattern regex) {
    setOverwriteMode(true);
    matcher = regex.matcher(""); // create a Matcher for the regular
                   // expression
  }

  public Object stringToValue(String string) throws java.text.ParseException {
    if (string == null)
      return null;
    matcher.reset(string); // set 'string' as the matcher's input

    if (!matcher.matches()) // Does 'string' match the regular expression?
      throw new java.text.ParseException("does not match regex", 0);

    // If we get this far, then it did match.
    return super.stringToValue(string); // will honor the 'valueClass'
                      // property
  }

  public static void main(String argv[]) {
    // a demo main() to show how RegexPatternFormatter could be used

    JLabel lab1 = new JLabel("szamok:");
    java.util.regex.Pattern evenLength = java.util.regex.Pattern
        .compile("[0-9]+(\\.[0-9][0-9]?)?");
    JFormattedTextField ftf1 = new JFormattedTextField(
        new RegexPatternFormatter(evenLength));

    JLabel lab2 = new JLabel("no vowels:");
    java.util.regex.Pattern noVowels = java.util.regex.Pattern.compile(
        "[^aeiou]*", java.util.regex.Pattern.CASE_INSENSITIVE);
    RegexPatternFormatter noVowelFormatter = new RegexPatternFormatter(
        noVowels);
    noVowelFormatter.setAllowsInvalid(false); // don't allow user to type
                          // vowels
    JFormattedTextField ftf2 = new JFormattedTextField(noVowelFormatter);

    JFrame f = new JFrame("RegexPatternFormatter Demo");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel pan1 = new JPanel(new java.awt.BorderLayout());
    pan1.add(lab1, java.awt.BorderLayout.WEST);
    pan1.add(ftf1, java.awt.BorderLayout.CENTER);
    lab1.setLabelFor(ftf1);
    f.getContentPane().add(pan1, java.awt.BorderLayout.NORTH);
    JPanel pan2 = new JPanel(new java.awt.BorderLayout());
    pan2.add(lab2, java.awt.BorderLayout.WEST);
    pan2.add(ftf2, java.awt.BorderLayout.CENTER);
    lab2.setLabelFor(ftf2);
    f.getContentPane().add(pan2, java.awt.BorderLayout.SOUTH);
    f.setSize(300, 80);
    f.setVisible(true);
  }
}


