package br.com.luiz.marcadagua;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Function;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;


@RestController
@RequestMapping(path = "/pdf")
public class PdfController {

	@RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource>  cotacoes() 
    {
    	HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=doc.pdf");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        StringBuilder buff = new StringBuilder("<!DOCTYPE html>\r\n" + 
        		"<html>\r\n" + 
        		"<body>\r\n" + 
        		"<h1>Cabeçalho</h1>\r\n" + 
        		"<p>html convertido para pdf.</p>\r\n" + 
        		"</body>\r\n" + 
        		"</html>");
        
        byte[] content = marcaDaguaFunc.apply((toPdfFunc.apply(buff))).toByteArray();
        ByteArrayResource resource = new ByteArrayResource(content);

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(content.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    	
    }
   
    Function<StringBuilder, byte[]> toPdfFunc = html-> {
		try {
			OutputStream baosPDF = new ByteArrayOutputStream();
	        Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, baosPDF);
	        document.open();
	        InputStream is = new ByteArrayInputStream(html.toString().getBytes());
	        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
	        document.close();
	        byte[] bytearray = ((ByteArrayOutputStream) baosPDF).toByteArray();
	        baosPDF.close();
			return bytearray;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	};
	
	Function<byte[], ByteArrayOutputStream> marcaDaguaFunc = content-> {
		try {
			PdfReader reader = new PdfReader(content);
	        ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
	        
	        PdfStamper stamper = new PdfStamper(reader, baosPDF);
	        Font f = new Font(FontFamily.HELVETICA, 40);
	       
	        PdfContentByte over = stamper.getOverContent(1);
	        Phrase p = new Phrase("Marca dágua - In2Code", f);
	        over.saveState();
	        PdfGState gs1 = new PdfGState();
	        gs1.setFillOpacity(0.5f);
	        over.setGState(gs1);
	        ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 450, 0);
	        over.restoreState();
	        
	        stamper.close();
	        reader.close();
	        return baosPDF;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	};
}