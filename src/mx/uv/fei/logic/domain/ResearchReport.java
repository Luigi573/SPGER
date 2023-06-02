package mx.uv.fei.logic.domain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.media.jfxmedia.logging.Logger;

public class ResearchReport {
    public void generateResearchReport(){
        Document document = new Document();
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, 
                new FileOutputStream("Reporte_de_Anteproyectos.pdf"));

            document.open();

            PdfContentByte contentByte = pdfWriter.getDirectContent();
            Graphics graphics = contentByte.createGraphicsShapes(
                PageSize.LETTER.getWidth(), PageSize.LETTER.getHeight());

            Font font1 = new Font("Helvetica", Font.BOLD + Font.ITALIC, 35);    
            graphics.setFont(font1);

            graphics.setColor(Color.BLACK);
            graphics.drawString("Quiúbo", 0, 0);
            
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        document.close();
    }
}
