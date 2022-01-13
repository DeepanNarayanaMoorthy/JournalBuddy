import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import com.journalbuddy.invertedindexing.InvertedIndexingMain;

public class PDFParser
{
    
	public static String getNextWord(String str, String word) {
	    String[] strArr = str.split(word);
	    if(strArr.length > 1) {
	        strArr = strArr[1].trim().split(" ");
	        return strArr[0];
	    }
	    return null;
	}
	
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
    	Hashtable<String, String> doi_dict = new Hashtable<String, String>();
    	List<String> directoryName= new ArrayList<String>();
    	directoryName.add("E:\\Research Papers\\Integrated Circuit");
    	directoryName.add("E:\\Research Papers\\Image Processing");
    	List<File> filess = new ArrayList<File>();
    	
    	directoryName.parallelStream().forEach((directoryNameIter) -> {
    		listf(directoryNameIter, filess);
    	});

    	filess.parallelStream().forEach((filessiter) -> {
    		File file = filessiter;
            try (PDDocument document = Loader.loadPDF(file);)
            {
                AccessPermission ap = document.getCurrentAccessPermission();
                if (!ap.canExtractContent())
                {
                    throw new IOException("OOPS! It seems that you don't have permission to extract text");
                }

                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setSortByPosition(true);
                final String filenamenow=new String("E:\\Research Papers\\New folder\\"+file.getName()+".txt");//+".bin");
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
                    myWriter.write("\n-----------------------\n");
                    myWriter.write("");
                    myWriter.write(text.trim());
                    myWriter.write("");
                    
                    String[] arrOfStr = text.trim().toLowerCase().split("digital object identifier");
                    if(arrOfStr.length==2) {
                    	arrOfStr = arrOfStr[1].toLowerCase().split(" ");
//                    	System.out.print("\n88888888"+file.getName()+"88888888\n");
//                    	System.out.print(arrOfStr[1]);
                    	doi_dict.put(file.getName(), arrOfStr[1]);
//                    	System.out.print("\n");

                    } else {
                    	arrOfStr = text.trim().toLowerCase().split("DOI:");
                    	if(arrOfStr.length==2) {
                    		arrOfStr = arrOfStr[1].toLowerCase().split(" ");
//                    		System.out.print("\n88888888"+file.getName()+"88888888\n");
//                    		System.out.print(arrOfStr[1]);
                    		doi_dict.put(file.getName(), arrOfStr[1]);
//                    		System.out.print("\n");
                    	}

                    }
                }
                myWriter.close();
                
            } catch (IOException e1) {
				e1.printStackTrace();
			}
       });
    	System.out.print("###########DONE###############");
    	System.out.println("The set is: " + doi_dict.toString());
    	
        InvertedIndexingMain.GenerateInvertedIndex("E:\\Research Papers\\New folder","E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\op.txt");
    }

        
}

