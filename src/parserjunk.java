import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FilenameUtils;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;


public class parserjunk
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
    	
    	String directoryName="E:\\Research Papers\\Integrated Circuit";
    	List<File> filess = new ArrayList<File>();
    	List<Integer> iterr = new ArrayList<Integer>();
    	listf(directoryName, filess);

    	for(int i = 0;i <= filess.size(); i++){
    		iterr.add(i);
    	}
    	iterr.parallelStream().forEach((iterrnum) -> {

    		System.out.print("######################################################################");
//    		File file = new File(filess.get(i).getPath());
    		File file = filess.get(iterrnum);
            try (PDDocument document = Loader.loadPDF(file);)
            {
                AccessPermission ap = document.getCurrentAccessPermission();
                if (!ap.canExtractContent())
                {
                    throw new IOException("OOPS! It seems that you don't have permission to extract text");
                }

                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setSortByPosition(true);
                final String filenamenow=new String("E:\\Research Papers\\New folder\\"+String.valueOf(iterrnum)+".txt");
				try{
					File myObj = new File(filenamenow);
					if (myObj.createNewFile()) {
						System.out.println("File created: " + myObj.getName());
					} else {
						System.out.println("File already exists.");
					}
					} catch (IOException e) {
						System.out.println("An error occurred.");
						e.printStackTrace();
				}
				
				FileWriter myWriter = new FileWriter(filenamenow);
                
                for (int p = 1; p <= document.getNumberOfPages(); ++p)
                {
                    stripper.setStartPage(p);
                    stripper.setEndPage(p);
                    String text = stripper.getText(document);
                    String pageStr = String.format("page %d:", p);
                    myWriter.write(pageStr);
//                    System.out.println(pageStr);
                    for (int j = 0; j < pageStr.length(); ++j)
                    {
                        System.out.print("-");
                    }
                    myWriter.write("");
//                    System.out.println();
                    myWriter.write(text.trim());
//                    System.out.println(text.trim());
                    myWriter.write("");
//                    System.out.println();
                }
                myWriter.close();
                
            } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            });
    }
        
}

