package model.bean;

public class PurchItem {

//	"Megnevez�s", "Mennyis�g", "�sszeg", "D�tum", "Megjegyz�s", ""
	
	private String name;
	private String amount;
	private String date;
	private String comment;
	
	
	public PurchItem(){}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String string) {
		this.amount = string;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
