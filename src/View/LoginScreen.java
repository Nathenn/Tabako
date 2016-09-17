package View;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.xswingx.PromptSupport;

import control.TabakoController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginScreen extends JFrame {

	private static final long serialVersionUID = 1715937993335554123L;
	
	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField txtPassword;

	private TabakoController control;
	
	
	
	/**
	 * Create the frame.
	 */
	public LoginScreen(TabakoController tabakoController) {
		super("Bejelentkezés");
		
		control = tabakoController;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		

		txtUserName = new JTextField(15); 
		txtUserName.addKeyListener(listenEnter());
		txtUserName.setFont(new Font("Courier New", Font.PLAIN, 19));
		txtUserName.setBounds(51, 22, 333, 54);
		txtUserName.setColumns(10);
		PromptSupport.setPrompt("Felhasználónév", txtUserName);
		contentPane.add(txtUserName);
		
		txtPassword = new JPasswordField (15);
		txtPassword.addKeyListener(listenEnter());
		txtPassword.setFont(new Font("Courier New", Font.PLAIN, 19));
		txtPassword.setColumns(10);
		txtPassword.setBounds(51, 87, 333, 54);
		PromptSupport.setPrompt("Jelszó", txtPassword);
		contentPane.add(txtPassword);
		
		JButton btnBelps = new JButton("Belépés");
		btnBelps.setMnemonic(KeyEvent.VK_ENTER);
		btnBelps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control.loginCheck(getUserName(), getPassword());
			}
		});
		btnBelps.setFont(new Font("Courier New", Font.PLAIN, 19));
		btnBelps.setBounds(51, 178, 333, 47);
		contentPane.add(btnBelps);

		
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	

	public String getUserName() {
		return txtUserName.getText();
	}

	public String getPassword() {
		return String.valueOf(txtPassword.getPassword());
	}
	
	private KeyListener listenEnter(){
		
		return new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Kivul van");
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					System.out.println("Enter volt!");
					control.loginCheck(getUserName(), getPassword());
				}
			}
		};
		
	}

}
