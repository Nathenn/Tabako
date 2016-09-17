package Util.mytable;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {
	
	
	private static final long serialVersionUID = 6960338563207938888L;
	private int tableMode;
//	private int selectedRow;
	
	/**
	 * tableMode: 	1->raktarTable
	 * 				2->eladasTable
	 * 				3->vetelezes
	 * */
	public MyTableModel(Object[] objects, int i, int mode){
		super(objects, i);
		tableMode = mode;
	}
	
	
	public MyTableModel(Object[] objects, int i){
		super(objects, i);
		tableMode = 0;
	}
	
	public boolean isCellEditable(int row,int cols){
		
		
		if(tableMode == 1){ 	 //raktarTable
			if( cols==0 || cols==1 || cols==3){return false;}
		}
		
		else if(tableMode == 2){ //eladasTable
			if(row == this.getRowCount()-1 && cols == 4) return false;
			if( cols==4)return true;
			else 		return false;
		}
		
		else if(tableMode == 3){ //vetelezesTable
			if( 	 
					cols==3 || 
				   (cols==0 && getValueAt(row, cols)=="")  || 	//uj sor eseten ne legyen gomb megnevezes oszlopban
				   (cols==4 && getValueAt(row, cols)=="")		//table feltolteskor NE legyen torlo gomb az utolso oszlopban
				){return false;}
			
			
		}
		
		else if(tableMode == 4){  //forgalom
			if(cols==2 && getValueAt(row, cols)==""){
				return false;
			}
			
		}
		
		else{
			return true;
		}
		
		return true;                                                                                    
	}
	
	public void setSelectedRow(int r){
//		this.selectedRow = r;
//		ProductCellComponent comp = (ProductCellComponent) getValueAt(r, 0);
//		comp.updateTableData(r);
//		fireTableDataChanged();
		
	}



}     
