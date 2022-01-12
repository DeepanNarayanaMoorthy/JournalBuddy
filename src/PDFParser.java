import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;


public class PDFParser
{

	public static void listf(String directoryName, List<File> files) {
	    File directory = new File(directoryName);

	    File[] fList = directory.listFiles();
	    if(fList != null)
	        for (File file : fList) {      
	            if (file.isFile() && FilenameUtils.getExtension(file.getName()).equals("pdf")) {
	                files.add(file);
	            } else if (file.isDirectory()) {
	                listf(file.getAbsolutePath(), files);
	            }
	        }
	}
	
    public static void main(String[] args) throws IOException
    {
    	
    	String directoryName="E:\\Research Papers";
    	List<File> filess = new ArrayList<File>();
    	listf(directoryName, filess);
//    	filess.forEach(System.out::println);


    	for(int i = 0;i<=6;i++){
    		
    		System.out.print("######################################################################");
    		File file = new File(filess.get(i).getPath());
        	
            try (PDDocument document = Loader.loadPDF(file);)
            {
                AccessPermission ap = document.getCurrentAccessPermission();
                if (!ap.canExtractContent())
                {
                    throw new IOException("OOPS! It seems that you don't have permission to extract text");
                }

                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setSortByPosition(true);

                for (int p = 1; p <= document.getNumberOfPages(); ++p)
                {
                    stripper.setStartPage(p);
                    stripper.setEndPage(p);
                    String text = stripper.getText(document);
                    String pageStr = String.format("page %d:", p);
                    System.out.println(pageStr);
                    for (int j = 0; j < pageStr.length(); ++j)
                    {
                        System.out.print("-");
                    }
                    System.out.println();
                    System.out.println(text.trim());
                    System.out.println();

                }
            }
    	}
        
    }

}