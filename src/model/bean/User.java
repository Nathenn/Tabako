package model.bean;

public class User {

	private String name;
	private String loginDate;
	private boolean isAdmin;
	private boolean isWorking;
	
	
	public User(User u){
		this.name = u.getName();
		this.loginDate = ""; 
		this.isAdmin = u.isAdmin();
		this.isWorking = u.isWorking();
	}
	public User(String userName) {
		this.name = userName;
	}
	public User() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String name) {
		this.loginDate = name;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public boolean isWorking() {
		return isWorking;
	}
	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}
	public String toString(){
		
		String admine;
		if(isAdmin)
			admine = "ADMIN";
		else
			admine = "NEM-admin";

		return " Nev: " + name +  "Admin: " + admine + " Munka kezdete: " + loginDate;
	}
	
}
