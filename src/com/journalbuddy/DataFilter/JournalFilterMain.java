package com.journalbuddy.DataFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.journalbuddy.DataFilter.FilterData;
import com.journalbuddy.DataFilter.JournalData;
import com.journalbuddy.DataFilter.JournalDataLoader;

public class JournalFilterMain {
	
	public static HashMap<String, Integer> GenerateFilterCriteria() {
//		asd=		{"File_Name":{"criterias":["equals"], "need_inp":"yes"},
//				"DOI":{"criterias":["equals"], "need_inp":"yes"},
//				"Title":{"criterias":["equals"], "need_inp":"yes"},
//				"Container_Name":{"criterias":["equals"], "need_inp":"yes"},
//				"Author_FirstName":{"criterias":["equals"], "need_inp":"yes"},
//				"Author_LastName":{"criterias":["equals"], "need_inp":"yes"},
//				"Single_Subject":{"criterias":["equals"], "need_inp":"yes"},
//				"Single_Funder":{"criterias":["equals"], "need_inp":"yes"},
//				"Single_Award":{"criterias":["equals"], "need_inp":"yes"},
//				"Author_Sequence":{"criterias":["equals"], "need_inp":"yes"},
//
//				"Volume":{"criterias":["equals", "greater than", "lesser than"], "need_inp":"yes"},
//				"Issue":{"criterias":["equals", "greater than", "lesser than"], "need_inp":"yes"},
//				"Is_Referenced_By_Count":{"criterias":["equals", "greater than", "lesser than"], "need_inp":"yes"},
//				"Reference_count":{"criterias":["equals", "greater than", "lesser than"], "need_inp":"yes"},
//
//				"Published_Date":{"criterias":["equals", "greater than", "lesser than"], "need_inp":"yes"},
//				"Issued_Date":{"criterias":["equals", "greater than", "lesser than"], "need_inp":"yes"},
//				}
		HashMap<String, Integer> filtercriterias= new HashMap<String, Integer>();
		filtercriterias.put("File_Name##equals", 0);
		filtercriterias.put("DOI##equals", 1);
		filtercriterias.put("Title##equals", 2);
		filtercriterias.put("Container_Name##equals", 3);
		filtercriterias.put("Author_FirstName##equals", 4);
		filtercriterias.put("Author_LastName##equals", 5);
		filtercriterias.put("Single_Subject##equals", 6);
		filtercriterias.put("Single_Funder##equals", 7);
		filtercriterias.put("Single_Award##equals", 8);
		filtercriterias.put("Author_Sequence##equals", 9);
		filtercriterias.put("Volume##equals", 10);
		filtercriterias.put("Volume##greater_than", 10);
		filtercriterias.put("Volume##lesser_than", 10);
		filtercriterias.put("Issue##equals", 11);
		filtercriterias.put("Issue##greater_than", 11);
		filtercriterias.put("Issue##lesser_than", 11);
		filtercriterias.put("Is_Referenced_By_Count##equals", 12);
		filtercriterias.put("Is_Referenced_By_Count##greater_than", 12);
		filtercriterias.put("Is_Referenced_By_Count##lesser_than", 12);
		filtercriterias.put("Reference_count##equals", 13);
		filtercriterias.put("Reference_count##greater_than", 13);
		filtercriterias.put("Reference_count##lesser_than", 13);
		filtercriterias.put("Published_Date##equals", 14);
		filtercriterias.put("Published_Date##greater_than", 14);
		filtercriterias.put("Published_Date##lesser_than", 14);
		filtercriterias.put("Issued_Date##equals", 15);
		filtercriterias.put("Issued_Date##greater_than", 15);
		filtercriterias.put("Issued_Date##lesser_than", 15);
		return filtercriterias;
	}
	
}
