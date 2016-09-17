package control;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import Util.Pdfcreator;
import model.bean.CircItem;
import model.bean.Product;
import model.bean.PurchItem;
import model.bean.User;
import View.LoginScreen;
import View.MainScreen;
import View.SummaryScreen;
import model.Tabakomodel;

public class TabakoController {

	private boolean isSomeoneWorking;
	private LoginScreen loginScreen;
	private SummaryScreen sumScreen;
	private MainScreen mainScreen;
	private Tabakomodel dao;
	private User user;
	
	
	//CONSTRUCTOR
	public TabakoController(){
		loginScreen = new LoginScreen(this);
		dao   = new Tabakomodel(this); // Data Access Object
//		mainScreen = new MainScreen(this,  "developper");
//		sumScreen = new SummaryScreen(mainScreen, this);
	}
	

	
	//LOGIN-OUT CONTROL
	
	/**
	 * Login verification
	 * */
	public void loginCheck(String userName, String password) {
		//TODO
		//megfelelo e a beviteli formatum hianyzik...
		if(dao.userExists(userName) && dao.isCorrectPassword(userName, password)){
			
			user = new User(dao.getUser(userName)); //ki kell prob h egyszer megadok egyet aki nem dolgozik es utana a helyeset
													//ha nem jo akkor ilyenkor a usert nullazni kell (torolni)
			isSomeoneWorking = dao.checkIfSomeoneWorking();

			if(user.isAdmin() || user.isWorking() || !isSomeoneWorking){// ha van jogosultsaga
				
				loginScreen.dispose();
				mainScreen  = new MainScreen(this, userName);
				sumScreen = new SummaryScreen(mainScreen, this);

				if(!isSomeoneWorking && !user.isAdmin()){ //ha most kezd dolgozni es nem admin
					user.setWorking(true);
					user.setLoginDate(new SimpleDateFormat("yyyy-MM-dd (HH:mm)").format(Calendar.getInstance().getTime()));
					dao.updateUser(user); 
				}
				
				if(user.isAdmin()){
					//ide jonnek a plusz cuccok megejelenitesenek engedelyei
				}	
				
			}else{
				JOptionPane.showMessageDialog(loginScreen, "Nem dolgozol, de még admin se vagy!");
				return;
			}
		}else{
			JOptionPane.showMessageDialog(loginScreen, "Hibás jelszó vagy felhasználónév!");
		}
	}
	
	/**
	 * Log out from the application
	 * */
	public void logOut() {
		
		String[] options = new String[2];
    	options[0] = new String("Igen");
    	options[1] = new String("Nem");
		
		int dialogResult = JOptionPane.showOptionDialog(null,"Biztos, hogy kijelenzkezel?","Kijelentkezés", 0,JOptionPane.QUESTION_MESSAGE,null,options,null);

    	if(dialogResult == JOptionPane.YES_OPTION){
    		saveData();
    		
    		this.user = null;
    		this.mainScreen.dispose();
    		this.loginScreen = new LoginScreen(this);
    	}
		
		
		
	}
	
	/**
	 * Exit from the application
	 * */
	public void exitApp() {

		String[] options = new String[2];
    	options[0] = new String("Igen");
    	options[1] = new String("Nem");
		
		int dialogResult = JOptionPane.showOptionDialog(null,"Biztos, hogy kilépsz?","Kilépés", 0,JOptionPane.QUESTION_MESSAGE,null,options,null);

		if(dialogResult == JOptionPane.YES_OPTION){
			saveData();
    		
    		this.user = null;
    		this.mainScreen.dispose();
    		
		}	
		
	}
	
	/**
	 * At logout, save the table's data to the database
	 * */
	private void saveData(){
		
		if(mainScreen.getVetelezesPanel().isEdited()){
			dao.changeVetelTable(mainScreen.getVetelTable());
		}
		
		if(mainScreen.getForgalomPanel().isEdited()){
			dao.changeForgalomTable(mainScreen.getForgalomPanel());
		}
	}
	
	/**
	 * Update data and create PDF when a work session ends
	 * */
	public void actionBtnVegeztem(){
		
		new Pdfcreator(this);
		user.setWorking(false);
		dao.updateUser(user);
		updateRaktar();
		clearTempTables();

		
		
		this.user = null;
		this.mainScreen.dispose();
	}
	
	
	
	//TABLE CALCULATIONS
	
	/**
	 * Calculate and fill the empty cells of RaktarTable
	 * */
	public void szamolRaktar(JTable table) {

		if(table.isEditing())
			table.getCellEditor().stopCellEditing();
		
		for(int row = 0; row<table.getRowCount(); row++){
			
			Object objRaktaron = table.getModel().getValueAt(row, 1);
			Object objHutoben  = table.getModel().getValueAt(row, 2);

			BigDecimal raktaron = stringToBig(objRaktaron.toString());
			
			if(	!(objHutoben == null) && !objHutoben.toString().isEmpty()){

				BigDecimal hutoben = stringToBig(objHutoben.toString());
				BigDecimal sum = raktaron.add(hutoben);
				
				table.getModel().setValueAt(numToView(sum.toString()), row, 3);

			}else{
				
				table.getModel().setValueAt(numToView(raktaron.toString()), row, 3);
			}
		}

	}
	
	/**
	 * Calculate and fill all cells of EladasTable
	 * */
	public void szamolEladas(JTable eladasTable){
		
		if(eladasTable.isEditing())
			eladasTable.getCellEditor().stopCellEditing();
		
		BigDecimal sum = new BigDecimal(0);
		
		for(int row = 0; row<eladasTable.getRowCount()-1; row++){

			Object objVetelezes = eladasTable.getModel().getValueAt(row, 2);
			Object objMaradvany = eladasTable.getModel().getValueAt(row, 4);
			Object objNyito 	 = eladasTable.getModel().getValueAt(row, 1);

			BigDecimal osszesen = new BigDecimal(0);

			if(objVetelezes != null && !objVetelezes.toString().isEmpty()){ //Vetelezestol fuggoen kiszamitjuk az osszesent
				
				BigDecimal nyito    = stringToBig(objNyito.toString());
				BigDecimal vetelezes = stringToBig(objVetelezes.toString());
				osszesen = nyito.add(vetelezes);
				eladasTable.setValueAt(numToView(osszesen.toString()), row, 3);
		
			}else{
				osszesen = stringToBig(eladasTable.getValueAt(row, 1).toString());
				eladasTable.setValueAt(numToView(osszesen.toString()), row, 3);
			}
			
			
			if(objMaradvany != null && !objMaradvany.toString().isEmpty()){ //Fogyas szamitasa
				
				BigDecimal maradvany = stringToBig(objMaradvany.toString());
				BigDecimal fogyas = new BigDecimal(0);

				if(maradvany.compareTo(new BigDecimal(0))==-1){
					
					eladasTable.setValueAt("", row, 5);
					maradvany = new BigDecimal(0);
					eladasTable.setValueAt("",row, 4);
					
				}else
					fogyas = osszesen.subtract(maradvany);
				
				eladasTable.setValueAt(numToView(fogyas.toString()), row, 5);

				BigDecimal ar = stringToBig((eladasTable.getValueAt(row, 6).toString()));
				BigDecimal value = fogyas.multiply(ar);

				sum = sum.add(value);

				eladasTable.setValueAt(value.setScale(0, RoundingMode.CEILING), row, 7);
				
			}else{// ha nem volt fogyas
				eladasTable.getModel().setValueAt(0, row, 7);
			}

		}
		
		//vegul az osszes eladast is frissítjük az utolso cellaban
		eladasTable.setValueAt(sum.setScale(0, RoundingMode.CEILING), eladasTable.getRowCount()-1, 7);

	}
	
	
	
	//DATABASE GETTER
	
	/**
	 * Get purchase table from database
	 * */
	public List<PurchItem> getPurchSet(){
		return dao.getPurchSet();
	}
	
	/**
	 * Get the temporary-saved circulation tables's data from database
	 * */
	public List<CircItem> getCircSet(){
		return dao.getCircSet();
	}

	/**
	 * Get the list of products from database
	 * */
	public List<Product> getProductList() {
		return dao.getProductList();
	}

	/**
	 * Get the exact product which has the given name
	 * */
	public Product getProduct(String name) {
		
		for(Product p : dao.getProductList()){
			if(p.getName().equals(name))
				return p;
		}

		//TODO: lehetne esetleg JOptionPanet tenni
		System.out.println("Nincs a keresesnek megfelelo aru (control.getProduct())");
		return null;
	}
	
	/**
	 * Get all product with the whole amount of its purchases
	 * */
	public Map<String, BigDecimal> getVetelSum() {
		return mainScreen.getVetelSum();
	}
	
	
	
	//DATABASE SETTER
	
	/**
	 * Deletes all item from temporary tables in the database
	 * */
	private void clearTempTables(){
		dao.clearTempTables();
	} 
	
	/**
	 * Updates (rewrites) products quantity when the work session ends
	 * */
	private void updateRaktar(){
		dao.updateRaktar(sumScreen.getEladasTable());
	}
	
	
	
	//GETTER-SETTER
	
	public MainScreen getMainScreen() {
		return mainScreen;
	}

	public boolean isSomeoneWorking() {
		return isSomeoneWorking;
	}
	
	public User getUser() {
		return this.user;
	}

	
	
	//NUMBER FORMATIONS
	
	/**
	 * Parse number to string.  The number may contains comma which is the decimal separator
	 * */
	public String numberToString(String string) {
		
		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
	    Number number = 0;
		try {
			number = format.parse(string);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
		}
		return number.toString();
	}

	/**
	 * Change number format for the view: 10234.8500 -> 10 234,85
	 * */
	public String numToView(String s) {

		 if(s.isEmpty()) return s;
		 
		 BigDecimal big = new BigDecimal(s);
		 big = big.setScale(2, RoundingMode.CEILING);
		 String string = big.toString();
		 
		 
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

	/**
	 * Change the number format for calculations: 10 234,85 -> 10234.85
	 * */
	public BigDecimal stringToBig(String s){
		
		BigDecimal big = new BigDecimal(0);
//		String result = "";
		
		if(s.isEmpty()) 
			return big;
		
		String string = s.replaceAll(" ", "");
		string = string.replaceAll(",", ".");

//		for(int i = 0; i<string.length(); i++){
//
//			if(	 string.charAt(i)==' '  ){
//				continue;
//				
//			}else if(string.charAt(i)==','){
//				 result+='.';
//				 for(int j=i+1; j<string.length();j++)
//					 result+=string.charAt(j);
//				 break;
//				 
//			}else
//				result+=string.charAt(i);
//			
//		 }

		big = new BigDecimal(string);
		return big;

	}
	
	
	//PANEL DISPLAY

	/**
	 * Display only the given panel
	 * */
	public void displayPanel(JPanel pan){

		for(JPanel p : mainScreen.getWorkSpacePanels()){
			if(!p.equals(pan) && p.isVisible()){
				p.setVisible(false);
			}
		}
		pan.setVisible(true);
	}



	
	public JTable getRaktarTable() {
		return mainScreen.getRaktarTable();
	}

	public JTable getEladasTable() {
		return mainScreen.getEladasTable();
	}

	public JTable getBejovoTable() {
		return mainScreen.getBejovoTable();
	}

	public JTable getLeadoTable() {
		return mainScreen.getLeadoTable();
	}

	public JTable getLottoTable() {
		return mainScreen.getLottoTable();
	}

	public JTable getVetelTable() {
		return mainScreen.getVetelTable();
	}

	



}
