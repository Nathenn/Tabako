package model.bean;

/**
 * A forgalom panel tablainak egy-egy sorat tarolja.
 * A tipus szerint kulonboztetjuk meg melyik tablahoz tartozik az adott rekord
 * Tipusok:	0 - bejovo
 * 			1 - leado
 * 			2 - lotto
 * 			3 - egyeb
 * */
public class CircItem {

	private String comment;
	private String amount;
	private int type;
	
	public CircItem(){}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
