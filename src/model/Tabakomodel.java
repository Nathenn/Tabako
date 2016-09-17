package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import model.bean.CircItem;
import model.bean.Product;
import model.bean.PurchItem;
import model.bean.User;
import View.workspacepanels.ForgalomPanel;
import control.TabakoController;

public class Tabakomodel {

	private List<Product> products = new ArrayList<Product>();
	private static Connection conn = null;
	private TabakoController controller;
	
	
	
	//CONSTRUCTOR: initialize the database
	public Tabakomodel(TabakoController controller){
		this.controller = controller;
		try {
			Class.forName("org.sqlite.JDBC");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a database connection
	 * */
	public static Connection dbConnection(){
		try {
			
			conn = DriverManager.getConnection("jdbc:sqlite:db\\tbkdb.db");
			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		} 
	}
	
	
	
	
	
	//USER, LOGIN-OUT
	
	/**
	 * Check if the given user name and password is valid or not
	 * */
	public boolean checkUserLogin(String userName, String password){
		//TODO: ez nincs felhasznalva sehol
		boolean result = false;
		Statement stmt = null;
		ResultSet rs = null;
		conn = null;
		
		try {
			conn = dbConnection();
			String query = "select * from user where name=" + userName + " and pwd=" + password;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			if (rs.next())  result = true; //ha mar van ilyen, akkor jok vagyunk; tobb nem lehet, az mashol van lekezelve
			
		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e);

		}finally {

			try{
				if(stmt != null) stmt.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); }
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); }
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); }
		}
		
		return result;
	}

	/**
	 * Does exist a user with that name?
	 * Only one can exist, because it's checked at registration
	 */
	public boolean userExists(String userName) {
		
		PreparedStatement pst = null;
		conn = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			conn = dbConnection();
			String query = "select * from user where name=?";
			pst = conn.prepareStatement(query);
			pst.setString(1, userName);
			rs = pst.executeQuery();
			
			if (rs.next())  result = true; //ha van ilyen, akkor jok vagyunk

		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try {
				if(rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	/**
	 * Check if the give password is correct or not
	 * */
	public boolean isCorrectPassword(String userName, String password) {

		PreparedStatement pst = null;
		conn = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			conn = dbConnection();
			String query = "select * from user where name=? and pwd=?";
			pst = conn.prepareStatement(query);
			pst.setString(1, userName);
			pst.setString(2, password);
			rs = pst.executeQuery();
			
			if (rs.next())  result = true; //ha van ilyen, akkor jok vagyunk

		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}

		return result;
	}
	
	/**
	 * Check if someone in a work session
	 * */
	public boolean checkIfSomeoneWorking() {
		PreparedStatement pst = null;
		conn = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			conn = dbConnection();
			String query = "select * from user where isWorking=1";
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			if (rs.next()) result = true;  //ha van ilyen, akkor valaki dolgozik mar
			
		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}

		return result;
	}
	
	/**
	 * Get the user with the given name
	 * */
	public User getUser(String name) {
		
		PreparedStatement pst = null;
		conn = null;
		ResultSet rs = null;
		User user = null;

		try {
			conn = dbConnection();
			String query = "select * from user where name=?";
			pst = conn.prepareStatement(query);
			pst.setString(1, name);
			rs = pst.executeQuery();
			
			if (rs.next()){   //ha van ilyen, akkor jok vagyunk
				user = new User();
				
				user.setName(rs.getString("name"));
				
				if(rs.getInt("admin")==0)
					user.setAdmin(false);
				else
					user.setAdmin(true);
				
				if(rs.getInt("isWorking")==0)
					user.setWorking(false);
				else
					user.setWorking(true);

			}

		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}

		return user;
	}
	


	//DATA CHANGES
	
	/**
	 * Updates the temporary purchase table
	 * */
	public void changeVetelTable(JTable table) {
		//torli az elozot es bemasolja az ujat
		PreparedStatement pst = null;
		Statement stmt = null;
		conn = null;
		ResultSet rs = null;

		try {
			conn = dbConnection();
			String query = "delete from purchaseTemp";
			stmt = conn.createStatement();
			stmt.execute(query);
			
			query = "insert into purchaseTemp(`name`,`amount`, `comment`, `date`)VALUES (?,?,?,?);";
			pst = conn.prepareStatement(query);
			
			for(int row = table.getRowCount()-1; row >= 0 ; row--){
				for(int col = 0; col < table.getColumnCount()-1; col++){ // getColumnCount()-1 mert az uccso oszlop gombnak van
					
					if(table.getValueAt(row, col)!=null){

						String value = table.getValueAt(row, col).toString();
						if(value != "" && !value.isEmpty()){
							
//							if(col == 1 || col == 2){
//								BigDecimal big = controller.stringToBig(value.toString());
//								value = big.toString();
//							}
							
							pst.setString(col+1, value);
						}else
							pst.setString(col+1,"");
					}else{
						pst.setString(col+1,"");
					}
				}
				
				pst.executeUpdate();
				
			}


		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try {
				if(stmt != null) stmt.close();
			} catch (SQLException e) { e.printStackTrace(); }		
			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}

		
		
	}
	
	/**
	 * Update user's isWorking status
	 * */
	public void updateUser(User user) {

		PreparedStatement pst = null;
		conn = null;
		ResultSet rs = null;

		try {
			conn = dbConnection();
			String query = "UPDATE user SET `isWorking`=? WHERE name = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1, user.isWorking() ? 1 : 0); 
			pst.setString(2, user.getName());
			pst.executeUpdate();
				
		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}

	}

	/**
	 * Deletes all records from temporary database tables (purchaseTemp, circulationTemp)
	 * */
	public void clearTempTables() {
		
		PreparedStatement pst = null;
		Statement stmt = null;
		conn = null;
		ResultSet rs = null;

		try {
			conn = dbConnection();
			String query = "delete from purchaseTemp";
			stmt = conn.createStatement();
			stmt.execute(query);
			
			query = "delete from circulationTemp";
			stmt.execute(query);

		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try {
				if(stmt != null) stmt.close();
			} catch (SQLException e) { e.printStackTrace(); }		
			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}
	}
	
	/**
	 * Updates the temporary circulation table in the database
	 * */
	public void changeForgalomTable(ForgalomPanel forgalomPanel) {

		PreparedStatement pst = null;
		Statement stmt = null;
		conn = null;
		ResultSet rs = null;
		
		List<JTable> tables = new ArrayList<JTable>();
		tables.add(forgalomPanel.getBejovoTable());
		tables.add(forgalomPanel.getLeadoTable());
		tables.add(forgalomPanel.getLottoTable());
//		tables.add(forgalomPanel.getEgyebTable());

		try {
			conn = dbConnection();
			String query = "delete from circulationTemp";
			stmt = conn.createStatement();
			stmt.execute(query);
			
			query = "insert into circulationTemp(`comment`,`amount`, `type`)VALUES (?,?,?);";
			pst = conn.prepareStatement(query);
			
			//identify tables:
			// 0 = bejovo
			// 1 = leado
			// 2 = lotto
			// 3 = egyeb
			int id = 0;
			boolean hasToWrite = false;
			for(JTable table : tables){
				
				pst.setInt(3,id);
				for(int row = table.getRowCount()-1; row >= 0 ; row--){
					for(int col = 0; col < table.getColumnCount()-1; col++){
						
						if(table.getValueAt(row, col) != null && table.getValueAt(row, col).toString() != ""){
							
							String value = table.getValueAt(row, col).toString();
							if(col == 1){
								value = controller.stringToBig(value).toString();
							}
							
							pst.setString(col+1, value);
							hasToWrite = true;
						}
					}
					
					if(hasToWrite){
						pst.executeUpdate();
						hasToWrite = false;
					}

				}

				id++;
			}
			

		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try {
				if(stmt != null) stmt.close();
			} catch (SQLException e) { e.printStackTrace(); }		
			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}
		
	}
	
	/**
	 * Updates the quantity of products, based on the given table
	 * */
	public void updateRaktar(JTable eladas) {
		
		PreparedStatement pst = null;
		conn = null;
		ResultSet rs = null;

		try {
			conn = dbConnection();
			String query = "UPDATE product SET `quantity`=? WHERE name = ?";
			pst = conn.prepareStatement(query);
			
			for(int i = 0; i<eladas.getRowCount(); i++){
				
				if(eladas.getValueAt(i, 4).toString()!="" && eladas.getValueAt(i, 4)!=null){
//					BigDecimal bigAmount = controller.stringToBig(eladas.getValueAt(i, 4).toString());
					pst.setString(1,eladas.getValueAt(i, 4).toString()); 
							
							
//					double amount = Double.parseDouble(eladas.getValueAt(i, 4).toString()) ;
//					pst.setDouble(1, amount); 
					
					String productName = (String) eladas.getValueAt(i, 0);
					pst.setString(2, productName);
					
					pst.executeUpdate();
					
				}else{
					BigDecimal bigAmount = controller.stringToBig(eladas.getValueAt(i, 3).toString());
					pst.setString(1, bigAmount.setScale(2, RoundingMode.CEILING).toString()); 

					String productName = (String) eladas.getValueAt(i, 0);
					pst.setString(2, productName);
					
					pst.executeUpdate();
				}
			}
			
			
			
			
		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}
		
	}
	
	
	
	//DATA LISTS

	/**
	 * List all of the users
	 * */
	public boolean listUsers(){
		
		//TODO: NINCS FELHASZNALVA SEHOL
		boolean result = true;
		Statement stmt = null;
		conn = null;
		ResultSet rs = null;
		try {
			conn = dbConnection();
			String query = "select * from user";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				User u = new User();
				u.setName(rs.getString("name"));
				
				if(rs.getInt("admin")==0)
					u.setAdmin(false);
				else
					u.setAdmin(true);
	        }
			result = true;
	        conn.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}finally {
			//NAGYON FONTOS, hogy minden adatbazis objektumot finallyban le kell zarni, mivel ha ezt nem tesszuk meg akkor elofordulhat
			//hogy nyitott kapcsolatok maradnak az adatbazis fele. Az adatbazis pedig korlatozott szamban tart fennt kapcsolatokat, ezert
			//egy ido utan akar ez be is telhet!
			//Minden egyes objektumot kulon try catch agban kell megprobalni bezarni!
			try {
				if(stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
		
	/**
	 * Get all of the products
	 * */
	public List<Product> getProductList() {

		products.clear();

		PreparedStatement pst = null;
		conn = null;
		ResultSet rs = null;
		
		try {
			conn = dbConnection();
			String query = "select * from product";
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()){
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setPrice(controller.stringToBig((rs.getString("price"))));
				p.setQuantity(controller.stringToBig(rs.getString("quantity")));
				
				products.add(p);
			}

		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}
		
		return products;
	}

	/**
	 * Get the temporary purchase table
	 * */
	public List<PurchItem> getPurchSet() {
		
		PreparedStatement pst = null;
		conn = null;
		ResultSet rs = null;
		List<PurchItem> list = new ArrayList<PurchItem>();
		
		try {
			conn = dbConnection();
			String query = "select * from purchaseTemp";
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {
				PurchItem p = new PurchItem();
				p.setName(rs.getString("name"));
				p.setAmount(rs.getString("amount"));
				p.setDate(rs.getString("date"));
				p.setComment(rs.getString("comment"));
				
				list.add(p);
			}

		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}
		
		return list;
	}

	/**
	 * Get the temporary saved circulationTemp table from database
	 * */
	public List<CircItem> getCircSet() {

		PreparedStatement pst = null;
		conn = null;
		ResultSet rs = null;
		List<CircItem> list = new ArrayList<CircItem>();
		
		try {
			conn = dbConnection();
			String query = "select * from circulationTemp";
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {
				CircItem p = new CircItem();
				p.setComment(rs.getString("comment"));
				
//				BigDecimal amount = controller.stringToBig(rs.getString("amount"));
//				p.setAmount(amount.toString());
				p.setAmount(rs.getString("amount"));
//				p.setAmount(rs.getDouble("amount"));
				
				
				p.setType(rs.getInt("type"));
				list.add(p);
			}

		} catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();

		}finally {

			try{
				if(pst != null) pst.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();} //printstacktrace majd mehet innen ki!!!
			try{
				if(rs != null) rs.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
			try {
				if(conn != null) conn.close();
			}catch (SQLException e) { JOptionPane.showMessageDialog(null, e); e.printStackTrace();}
		}
		
		return list;
	}
	

	


}
