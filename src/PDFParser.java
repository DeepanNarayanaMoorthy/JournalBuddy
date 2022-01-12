import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;


public class PDFParser
{

	static void RecursivePrint(File[] arr, int index, int level)
	{
		if (index == arr.length)
			return;

		for (int i = 0; i < level; i++)
			System.out.print("\t");

		if (arr[index].isFile())
			System.out.println(arr[index].getName());

		else if (arr[index].isDirectory()) {
			System.out.println("[" + arr[index].getName()
							+ "]");

			RecursivePrint(arr[index].listFiles(), 0,
						level + 1);
		}

		RecursivePrint(arr, ++index, level);
	}

    public static void main(String[] args) throws IOException
    {
    	
    	//SUBFOLDERFILES
    	/*
		String maindirpath
		= "E:\\Research Papers";
		
		File maindir = new File(maindirpath);
		
		if (maindir.exists() && maindir.isDirectory()) {
			
		File arr[] = maindir.listFiles();
		
		System.out.println(
			"**********************************************");
		System.out.println(
			"Files from main directory : " + maindir);
		System.out.println(
			"**********************************************");
		
		RecursivePrint(arr, 0, 0);
		
		}
		*/
		//SUBFOLDERFILES
		
    	File file = new File("E:\\Research Papers\\IoT\\07995046.pdf");
    	
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
                for (int i = 0; i < pageStr.length(); ++i)
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