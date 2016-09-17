package Util;



import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import control.TabakoController;

public class Pdfcreator {

	private static TabakoController control;
	
	@SuppressWarnings("unused")
	private static PdfFont font, bold;
	private static Table raktarTable, eladasTable, vetelezesTable, bejovo, leado, lotto;
	
 	@SuppressWarnings("static-access")
	public Pdfcreator(TabakoController control){
 		
 		this.control = control;

		try {
			font = PdfFontFactory.createFont(FontConstants.HELVETICA, "Cp1250", false);
			bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
			createPDF7();
			JOptionPane.showMessageDialog(null, "PDF legenerálva!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Nem sikerült legenerálni a PDF fájlt!");
		}
	}
	
	private static void createPDF7() throws IOException{
		
		PdfWriter writer = new PdfWriter(new FileOutputStream("elso7.pdf"));
		PdfDocument pdf = new PdfDocument(writer);    
		Document document = new Document(pdf, PageSize.A4);
		document.setMargins(20, 20, 20, 20);
		
		String userName = control.getUser().getName();
		System.out.println(userName);
		String currentDate = new SimpleDateFormat("yyyy-MM-dd (HH:mm)").format(Calendar.getInstance().getTime());
		System.out.println(currentDate);
		//ki dolgozott és meddig?
		Paragraph userInfo = new Paragraph( "Alkalmazott: " + userName + "\n" + 
											"Munka idotartama: " + control.getUser().getLoginDate() + " -> " + 
											currentDate
									);
		document.add(userInfo);
		
		
		//Raktar table
		addRaktar(document);
		
		//empty line
		Paragraph p = new Paragraph("");
		document.add(p);
		
		//Eladas table
		addEladas(document);
		
		document.add(p);
		addVetelezes(document);
		
		document.add(p);
		addForgalom(document);
		
			
		document.close();
		
	}
	
	
	private static void addRaktar(Document document){
		
		raktarTable = new Table(new float[]{4, 4, 4, 4});
		raktarTable.setWidthPercent(100);
		raktarTable.setFont(font);
		
		raktarTable.addHeaderCell("Megnevezés");
		raktarTable.addHeaderCell("Raktár");
		raktarTable.addHeaderCell("Hûtõ");
		raktarTable.addHeaderCell("Összesen");
		
		for(int row = 0; row < control.getRaktarTable().getRowCount(); row++){
			for(int col = 0; col<control.getRaktarTable().getColumnCount(); col++){
				raktarTable.addCell(control.getRaktarTable().getValueAt(row, col).toString());
			}
		}
		
		document.add(raktarTable);
	}

	private static void addEladas(Document document){
		
		eladasTable = new Table(new float[]{4, 4, 4, 4, 4, 4, 4, 4});
		eladasTable.setWidthPercent(100);
		eladasTable.setFont(font);
		
		//headers
		eladasTable.addHeaderCell("Megnevezés");
		eladasTable.addHeaderCell("Nyitó készlet");
		eladasTable.addHeaderCell("Vételezés");
		eladasTable.addHeaderCell("Összesen");
		eladasTable.addHeaderCell("Maradvány");
		eladasTable.addHeaderCell("Fogyás");
		eladasTable.addHeaderCell("Eladási ár");
		eladasTable.addHeaderCell("Eladási érték");
		
		for(int row = 0; row<control.getEladasTable().getRowCount(); row++){
			for(int col = 0; col<control.getEladasTable().getColumnCount(); col++){
				eladasTable.addCell(control.getEladasTable().getValueAt(row, col).toString());
			}
		}
		
		document.add(eladasTable);
	}
	
	private static void addForgalom(Document document){
		
		Table container = new Table(new float[]{4, 4});
		container.setWidthPercent(100);
		container.setFont(font);
		container.setBorder(Border.NO_BORDER);

		
		bejovo = new Table(new float[]{4, 4});
		bejovo.setWidthPercent(100);
		bejovo.setFont(font);
		bejovo.addHeaderCell("Megjegyzés");
		bejovo.addHeaderCell("Összeg");
		for(int row = 0; row < control.getBejovoTable().getRowCount(); row++){
			for(int col = 0; col<control.getBejovoTable().getColumnCount()-1; col++){
				
				Object value = control.getBejovoTable().getValueAt(row, col);

				if(value != null)
					bejovo.addCell(control.getBejovoTable().getValueAt(row, col).toString());
			}
		}
		container.addCell(bejovo);

		leado = new Table(new float[]{4, 4});
		leado.setWidthPercent(100);
		leado.setFont(font);
		leado.addHeaderCell("Megjegyzés");
		leado.addHeaderCell("Összeg");
		for(int row = 0; row < control.getLeadoTable().getRowCount(); row++){
			for(int col = 0; col<control.getLeadoTable().getColumnCount()-1; col++){
				
				Object value = control.getLeadoTable().getValueAt(row, col);
				
				if(value != null)
					leado.addCell(control.getLeadoTable().getValueAt(row, col).toString());
			}
		}
		container.addCell(leado);
		
		
		lotto = new Table(new float[]{4, 4});
		lotto.setWidthPercent(100);
		lotto.setFont(font);
		lotto.setKeepTogether(true);
		lotto.addHeaderCell("Megjegyzés");
		lotto.addHeaderCell("Összeg");
		for(int row = 0; row < control.getLottoTable().getRowCount(); row++){
			for(int col = 0; col<control.getLottoTable().getColumnCount()-1; col++){
				
				Object value = control.getLottoTable().getValueAt(row, col);
				
				if(value != null)
					lotto.addCell(control.getLottoTable().getValueAt(row, col).toString());
			}
		}
		
		container.addCell(lotto);
		
		

		document.add(container);
		
	}

	private static void addVetelezes(Document document){
		
		vetelezesTable = new Table(new float[]{4, 4, 4, 4});
		vetelezesTable.setWidthPercent(100);
		vetelezesTable.setFont(font);
		
		vetelezesTable.addHeaderCell("Megnevezés");
		vetelezesTable.addHeaderCell("Mennyiség");
		vetelezesTable.addHeaderCell("Dátum");
		vetelezesTable.addHeaderCell("Megjegyzés");
		
		for(int row = 0; row < control.getVetelTable().getRowCount(); row++){
			for(int col = 0; col<control.getVetelTable().getColumnCount()-1; col++){
				control.getVetelTable();
				vetelezesTable.addCell(control.getVetelTable().getValueAt(row, col).toString());
			}
		}
		
		document.add(vetelezesTable);
	}
}
