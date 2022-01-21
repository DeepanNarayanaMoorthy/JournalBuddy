package com.journalbuddy.DataFilter;

import java.text.ParseException;
import java.util.List;

import com.journalbuddy.DataFilter.JournalDataLoader;
public class Filter {

	public static float calculate (String string1, String string2) {
		if(string1==null) {
			string1="";
		}
		if(string2==null) {
			string2="";
		}
		int[][] distances=new int[string1.length()+1][string2.length()+1];
		
		for (int i=1; i<=string1.length();i++) {
			distances[i][0]=i;
		}
		
		for (int j=1; j<=string2.length(); j++) {
			distances[0][j]=j;
		}
		
		for(int i=1; i<=string1.length(); i++) {
			for (int j=1; j<=string2.length(); j++) {
				if (string1.charAt(i-1)==string2.charAt(j-1)) {
					distances[i][j]=distances[i-1][j-1];
				} else {
					distances[i][j]=minimum(distances[i-1][j],distances[i][j-1],distances[i-1][j-1])+1;
				}
			}
		}
		
		return distances[string1.length()][string2.length()];
	}

	private static int minimum(int i, int j, int k) {
		return Math.min(i,Math.min(j, k));
	}
	
	public static boolean calculateResult(String a, String b, float factor) {
		float valuee=calculate(a, b);
		if(a==null) {
			a="";
		}
		if(b==null) {
			b="";
		}
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

}
