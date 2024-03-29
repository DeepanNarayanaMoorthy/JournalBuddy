import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.journalbuddy.DataFilter.ConcurrentSearch;
import com.journalbuddy.DataFilter.FilterData;
import com.journalbuddy.JournalDatabase.JournalData;
import com.journalbuddy.DataFilter.JournalDataLoader;
import com.journalbuddy.DataFilter.Filter;
public class DataFilterDemo {

	public static void main(String[] args) throws IOException {
		String CSVpathSTR=System.getProperty("user.dir")+"\\data\\JournalData.csv";
		Path path = Paths.get(CSVpathSTR);
		HashMap<String, Integer> filtercriterias=Filter.GenerateFilterCriteria();
		JournalData data[] = JournalDataLoader.load(path);
		System.out.println("Number of items: " + data.length);

		Date start, end;
		final int SIZE=data.length;
		
		List<FilterData> filters = new ArrayList<FilterData>();
		FilterData filter = new FilterData();
		
		filters = new ArrayList<FilterData>();
		filter = new FilterData();
		filter.setIdField(filtercriterias.get("Author_FirstName##equals"));
		filter.setLeven_dis(2);
		filter.setValue("Xinmiao");
		filters.add(filter);
		filter = new FilterData();
		filter.setIdField(filtercriterias.get("Single_Subject##equals"));
		filter.setLeven_dis(3);
		filter.setValue("General");
		filters.add(filter);

		System.out.println("filters.size: "+ filters.size());
		start = new Date();
		List<JournalData> results = ConcurrentSearch.findAll(data, filters, SIZE);
		System.out.println("ConcurrentSearch done");
		System.out.println("Test 5 - Results: " + results.size());
		end = new Date();
		System.out.println("Test 5 - Execution Time: "
				+ (end.getTime() - start.getTime()));
		
		for(JournalData asd: results) {
			System.out.println(asd.getTitle());
			System.out.println(asd.getAuthor_firstname());
		}

	}
}
