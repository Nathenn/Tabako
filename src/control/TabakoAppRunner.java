package control;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class TabakoAppRunner {

	public static void main(String[] args) {

		//Windows look and feel
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		//Nimbus look and feel
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		            defaults.put("Table.gridColor", new Color (214,217,223));
		            defaults.put("Table.disabled", false);
		            defaults.put("Table.showGrid", true);
		            defaults.put("Table.intercellSpacing", new Dimension(1, 1));
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, fall back to cross-platform
		    try {
		        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		    } catch (Exception ex) {
		        // not worth my time
		    }
		}
	
		new TabakoController();

	}

}
