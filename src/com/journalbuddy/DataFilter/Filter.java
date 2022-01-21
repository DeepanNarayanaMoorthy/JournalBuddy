package com.journalbuddy.DataFilter;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import com.journalbuddy.JournalDatabase.JournalData;
import com.journalbuddy.MatchingVocab.LevenshteinDistance;
public class Filter {
	
	public static boolean calculateResult(String a, String b, float factor) {
		float valuee=LevenshteinDistance.calculate(a, b);
		if(a==null) a="";
		if(b==null) b="";
		float tocompare=factor;
		System.out.println(a+" "+b+" "+" "+Float.toString(valuee)+" "+Float.toString(tocompare) );
		if(valuee<=tocompare){
			return true;
		} else {
			return false;
		}
	}

	public static boolean filter(JournalData data, List<FilterData> filters) throws ParseException {
		boolean result = true;

		for (FilterData filter : filters) {
			result = result && evaluateFilter(data, filter);
			if (!result) {
				return false;
			}
		}

		return true;
	}

	private static boolean evaluateFilter(JournalData data, FilterData filter) throws ParseException {
		switch (filter.getIdField()) {
		case 0:
//			if (data.getFilename() == filter.getValue()) {
			if (calculateResult(data.getFilename(), filter.getValue(), filter.getLeven_dis())) {
				return true;
			}
			break;
		case 1:
//			if (data.getDoi() == (filter.getValue())) {
			if (calculateResult(data.getDoi(), filter.getValue(), filter.getLeven_dis())) {
				return true;
			}
			break;
		case 2:
//			if (data.getTitle() == filter.getValue()) {
			if (calculateResult(data.getTitle(), filter.getValue(), filter.getLeven_dis())) {
				return true;
			}
			break;
		case 3:
//			if (data.getContainer_name() == filter.getValue()) {
			if (calculateResult(data.getContainer_name(), filter.getValue(), filter.getLeven_dis())) {
				return true;
			}
			break;
		case 4:
//			if (data.getAuthor_firstname() == filter.getValue()) {
			if (calculateResult(data.getAuthor_firstname(), filter.getValue(), filter.getLeven_dis())) {
				return true;
			}
			break;
		case 5:
//			if (data.getAuthor_lastname() ==filter.getValue()) {
			if (calculateResult(data.getAuthor_lastname(), filter.getValue(), filter.getLeven_dis())) {				
				return true;
			}
			break;
		case 6:
//			if (data.getSingle_subject() == filter.getValue()) {
			if (calculateResult(data.getSingle_subject(), filter.getValue(), filter.getLeven_dis())) {
				return true;
			}
			break;
		case 7:
//			if (data.getSingle_funder() == filter.getValue()) {
			if (calculateResult(data.getSingle_funder(), filter.getValue(), filter.getLeven_dis())) {
				return true;
			}
			break;
		case 8:
//			if (data.getSingle_award() == (filter.getValue())) {
			if (calculateResult(data.getSingle_award(), filter.getValue(), filter.getLeven_dis())) {
				return true;
			}
			break;
		case 9:
//			if (data.getAuth_seq() == filter.getValue()) {
			if (calculateResult(data.getAuth_seq(), filter.getValue(), filter.getLeven_dis())) {

				return true;
			}
			break;
		case 10:
			if (data.getVolume()  == Integer.valueOf(filter.getValue())) {
				return true;
			}
			break;
		case 11:
			if (data.getVolume()  > Integer.valueOf(filter.getValue())) {
				return true;
			}
			break;
		case 12:
			if (data.getVolume()  < Integer.valueOf(filter.getValue())) {
				return true;
			}
			break;
		case 13:
			if (data.getIssue() == Integer.valueOf(filter.getValue())) {
				return true;
			}
			break;
		case 14:
			if (data.getIssue() > Integer.valueOf(filter.getValue())) {
				return true;
			}
			break;
		case 15:
			if (data.getIssue() < Integer.valueOf(filter.getValue())) {
				return true;
			}
			break;
		case 16:
			if (data.getIs_referenced_by_count() == Integer.valueOf(filter.getValue())){
				return true;
			}
			break;
		case 17:
			if (data.getIs_referenced_by_count() > Integer.valueOf(filter.getValue())){
				return true;
			}
			break;
		case 18:
			if (data.getIs_referenced_by_count() < Integer.valueOf(filter.getValue())){
				return true;
			}
			break;
		case 19:
			if (data.getReference_count() == Integer.valueOf(filter.getValue())) {
				return true;
			}
			break;
		case 20:
			if (data.getReference_count() > Integer.valueOf(filter.getValue())) {
				return true;
			}
			break;
		case 21:
			if (data.getReference_count() < Integer.valueOf(filter.getValue())) {
				return true;
			}
			break;
//=======================================
//			if status==0 print same date
//			if status<0 print  date1 is older then date2
//			if status>0 print  date1 is newer then date2
		case 22:
//			if (data.getIssue_date()==JournalDataLoader.StringtoDate(filter.getValue())) {
			if (data.getPub_date().compareTo(JournalDataLoader.StringtoDate(filter.getValue()))==0) {
				return true;
			}
			break;
		case 23:
//			if (data.getIssue_date()==JournalDataLoader.StringtoDate(filter.getValue())) {
			if (data.getPub_date().compareTo(JournalDataLoader.StringtoDate(filter.getValue()))>0) {
				return true;
			}
			break;
		case 24:
//			if (data.getIssue_date()==JournalDataLoader.StringtoDate(filter.getValue())) {
			if (data.getPub_date().compareTo(JournalDataLoader.StringtoDate(filter.getValue()))<0) {
				return true;
			}
			break;
		case 25:
//			if (data.getIssue_date()==JournalDataLoader.StringtoDate(filter.getValue())) {
			if (data.getIssue_date().compareTo(JournalDataLoader.StringtoDate(filter.getValue()))==0) {
				return true;
			}
			break;
		case 26:
//			if (data.getIssue_date()==JournalDataLoader.StringtoDate(filter.getValue())) {
			if (data.getIssue_date().compareTo(JournalDataLoader.StringtoDate(filter.getValue()))>0) {
				return true;
			}
			break;
		case 27:
//			if (data.getIssue_date()==JournalDataLoader.StringtoDate(filter.getValue())) {
			if (data.getIssue_date().compareTo(JournalDataLoader.StringtoDate(filter.getValue()))<0) {
				return true;
			}
			break;
		}
		return false;
	}

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
