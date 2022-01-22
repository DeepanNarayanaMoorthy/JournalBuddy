import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.journalbuddy.KeyWordExtract.GetKeyWords;
import com.journalbuddy.KeyWordExtract.Word;
public class KeyWordsDemo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String vocabsfile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\VocabTFs.txt";
		String Keywordsfile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\KeyWords.txt";
		String TXTfolders="E:\\Research Papers\\New folder";
		
		List<String> results = new ArrayList<String>();


		File[] files = new File(TXTfolders).listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getAbsolutePath());
		    }
		}
		
		System.out.println(results.toString());
		GetKeyWords.WriteToFile(results, vocabsfile, Keywordsfile);
		ConcurrentHashMap<String, Integer> ReadKeywordsmap=GetKeyWords.ReadKeywords(Keywordsfile);
		ConcurrentHashMap<String, Word> ReadVocabsmap=GetKeyWords.ReadVocabs(vocabsfile);
	}
}
