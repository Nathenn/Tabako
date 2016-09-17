package Util.vetelezes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ProductCellComponent extends JPanel{


	private static final long serialVersionUID = -5469314372537537454L;
	
	JButton addButton;
	JLabel label;
	
	int row, col;
	
	 
	public ProductCellComponent() {
		
		row =0; 
		col = 0;
		
		setOpaque(true);
		
	    GridBagLayout gridBagLayout = new GridBagLayout();
	    gridBagLayout.columnWidths = new int[]{204, 1, 0};
	    gridBagLayout.rowHeights = new int[]{23, 0};
	    gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
	    gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	    setLayout(gridBagLayout);
	    
	    addButton = new JButton("+");
	    addButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		
	    		JOptionPane.showMessageDialog(null, "Hurraaay!(" + row + "," + col + ")");
	    	}
	    });
	    addButton.setVisible(true);
	    addButton.setOpaque(false);
	    
	    label = new JLabel();
	    label.setOpaque(false);
       	GridBagConstraints gbc_label = new GridBagConstraints();
       	gbc_label.anchor = GridBagConstraints.NORTHWEST;
       	gbc_label.insets = new Insets(0, 0, 0, 5);
       	gbc_label.gridx = 0;
       	gbc_label.gridy = 0;
       	add(label, gbc_label);
       	
	    GridBagConstraints gbc_addButton = new GridBagConstraints();
	    gbc_addButton.anchor = GridBagConstraints.NORTHEAST;
	    gbc_addButton.gridx = 1;
	    gbc_addButton.gridy = 0;
	    add(addButton, gbc_addButton);
	    setFocusable(false);
	    setUI(getUI());
	 }
	 
	  public void updateData(String text, boolean isSelected, JTable table) {
	    
		  label.setText(text);
		  row = table.getSelectedRow();
		  col = table.getSelectedColumn();
		  
		  if (isSelected){
				setBackground(table.getSelectionBackground());
				setForeground(table.getSelectionForeground());
	            
	      }else{
	        	setBackground(table.getBackground());
	        	setForeground(table.getForeground());
	        	
	      }
		  
	  }

	public void updateTableData(int row) {
		System.out.println("update:" + row);
		this.row = row;

	}

	public JButton getAddButton() {
		return addButton;
	}
}
