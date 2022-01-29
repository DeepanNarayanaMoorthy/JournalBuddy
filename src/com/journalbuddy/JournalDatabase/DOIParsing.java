package com.journalbuddy.JournalDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.journalbuddy.JournalDatabase.JournalData;

public class DOIParsing {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }
  
  public static JournalData getJournalData(String filename, String doi) throws JSONException, IOException {
	  System.out.print(doi);
	  JSONObject json;
	  String DOI2;
	  try {
		  DOI2=doi.substring(0, doi.length() - 1);
		  json= readJsonFromUrl("https://api.crossref.org/works/"+DOI2);
		  doi=DOI2;
	} catch (Exception e2) {
		json= readJsonFromUrl("https://api.crossref.org/works/"+doi);
	}
	  json=(JSONObject) json.get("message");
	  JournalData curdata=new JournalData();

	  curdata.setFilename(filename);
	  curdata.setDoi(doi.replace(System.getProperty("line.separator"),""));
	  try {
		curdata.setContainer_name((String) ((JSONArray) json.get("container-title")).get(0));
		System.out.print("setContainer_name");
	} catch (JSONException e1) {
		// TODO Auto-generated catch block
		curdata.setContainer_name("NOT_FOUND");
	}
	  try {
		curdata.setIs_referenced_by_count((int) json.get("is-referenced-by-count"));
		System.out.println("setIs_referenced_by_count");
	} catch (JSONException e1) {
		// TODO Auto-generated catch block
		curdata.setIs_referenced_by_count(-1);
	}
	  if(json.has("fieldName")) {
	  curdata.setIssue((int) json.get("issue"));
	  System.out.println("setIssue");
	  } else {
		  curdata.setIssue(-1);
	  }
	  try {
		curdata.setIssue_year((int) ((JSONArray) ((JSONArray) ((JSONObject) json.get("issued")).get("date-parts")).get(0)).get(0));
		System.out.println("setIssue_year");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		curdata.setIssue_year(-1);
	}
	  try {
		curdata.setIssue_month((int) ((JSONArray) ((JSONArray) ((JSONObject) json.get("issued")).get("date-parts")).get(0)).get(1));
		System.out.println("setIssue_month");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		curdata.setIssue_month(-1);
	}
	  try {
		curdata.setPub_year((int) ((JSONArray) ((JSONArray) ((JSONObject) json.get("published")).get("date-parts")).get(0)).get(0));
		System.out.println("setPub_year");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		curdata.setPub_year(-1);
	}
	  try {
		curdata.setPub_month((int) ((JSONArray) ((JSONArray) ((JSONObject) json.get("published")).get("date-parts")).get(0)).get(1));
		System.out.println("setPub_month");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		curdata.setPub_month(-1);
	}
	  try {
		curdata.setReference_count((int) json.get("references-count"));
		System.out.println("setReference_count");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		curdata.setReference_count(-1);
	}
	  try {
		curdata.setTitle((String) ((JSONArray) json.get("title")).get(0));
		System.out.println("setTitle");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		curdata.setTitle("NOT_FOUND");
	}
	  try {
		curdata.setVolume(Integer.valueOf((String) json.get("volume")));
		System.out.println("setVolume");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		curdata.setVolume(-1);
	}
	 
	  JSONArray jArray ;
    List<HashMap<String, String>> authorshash=new ArrayList<HashMap<String, String>>();
    if(json.has("author")) {
    	jArray = (JSONArray)json.get("author"); 
	    if (jArray != null) { 
	       for (int i=0;i<jArray.length();i++){ 
	    	   HashMap<String, String> temphash=new HashMap<String, String>();
	    	   try {
				temphash.put("given", (String) ((JSONObject) jArray.get(i)).get("given"));
			} catch (JSONException e) {
				temphash.put("given","NOT_FOUND");
				e.printStackTrace();
			}
	    	   try {
				temphash.put("family", (String) ((JSONObject) jArray.get(i)).get("family"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				temphash.put("family","NOT_FOUND");
				e.printStackTrace();
			}
	    	   try {
				temphash.put("sequence", (String) ((JSONObject) jArray.get(i)).get("sequence"));
			} catch (JSONException e) {
				temphash.put("sequence","NOT_FOUND");
				e.printStackTrace();
			}
	    	   authorshash.add(temphash);
	       } 
	    }
	    curdata.setAuthors(authorshash);
    } else {
    	curdata.setAuthors(null);
    }
    
    String tempAward;
    if(json.has("funder")) {
    	List<HashMap<String, String>> fundershash=new ArrayList<HashMap<String, String>>();
	    jArray = (JSONArray)json.get("funder"); 
	    if (jArray != null) { 
	       for (int i=0;i<jArray.length();i++){
	    	   HashMap<String, String> tempfunderhash=new HashMap<String, String>();
	    	   try {
				tempAward=(String)((JSONArray) ((JSONObject) jArray.get(i)).get("award")).get(0);
			} catch (JSONException e) {
				tempAward="NOT_FOUND";
			}
	    	   tempfunderhash.put("funder",(String) ((JSONObject) jArray.get(i)).get("name"));
	    	   tempfunderhash.put("award",tempAward);
	    	   fundershash.add(tempfunderhash);       
	    	   } 
	    }
	    curdata.setFunders(fundershash);
    } else {
    	curdata.setFunders(null);
    }
    

    
    List<String> subjects=new ArrayList<String>();
    if(json.has("subject")) {
	    jArray = (JSONArray)json.get("subject"); 
	    if (jArray != null) { 
	       for (int i=0;i<jArray.length();i++){ 
	    	   subjects.add((String)jArray.get(i));
	       } 
	    }
	    curdata.setSubjects(subjects);
    } else {
    	curdata.setSubjects(null);
    }
    
    
	return curdata;
	  
  }
  public static void main(String[] args) throws IOException, JSONException {
    JSONObject json = readJsonFromUrl("https://api.crossref.org/works/10.1109/TMI.2015.2476354");
    json=(JSONObject) json.get("message");
    
    System.out.print("setContainer_name\n");
    String tempvar=(String) ((JSONArray) json.get("container-title")).get(0);
    System.out.print(tempvar);
    
    System.out.print("setIs_referenced_by_count\n");
    int tempint=(int) json.get("is-referenced-by-count");
    System.out.print(tempint);
    
    System.out.print("setIssue_date\n");
    tempint=(int) ((JSONArray) ((JSONArray) ((JSONObject) json.get("issued")).get("date-parts")).get(0)).get(0);
    System.out.print(tempint);
    
    System.out.print("setAuthors\n");
    List<HashMap<String, String>> templistt=new ArrayList<HashMap<String, String>>();
    JSONArray jArray = (JSONArray)json.get("author"); 
    if (jArray != null) { 
       for (int i=0;i<jArray.length();i++){ 
    	   HashMap<String, String> temphash=new HashMap<String, String>();
    	   temphash.put("given", (String) ((JSONObject) jArray.get(i)).get("given"));
    	   temphash.put("family", (String) ((JSONObject) jArray.get(i)).get("family"));
    	   temphash.put("sequence", (String) ((JSONObject) jArray.get(i)).get("sequence"));
    	   templistt.add(temphash);
       } 
    }
    System.out.print(templistt.toString());
    
    
//    System.out.print("setIssue_month\n");
//    System.out.print(tempint);
//    
//    System.out.print("setPub_date\n");
//    tempint=(int) json.get("is-referenced-by-count");
//    System.out.print(tempint);
//    
//    System.out.print("setPub_month\n");
//    tempint=(int) json.get("is-referenced-by-count");
//    System.out.print(tempint);
    
//    JSONArray qwe=(JSONArray) ((JSONObject) ((JSONObject) asd).get("published")).get("date-parts");
//    System.out.println(json.toString());
//    JSONArray qwe=(JSONArray) ((JSONObject) ((JSONObject) asd).get("published")).get("date-parts");
//    System.out.println(((JSONArray) qwe.get(0)).get(0));
//    System.out.println(((JSONArray) qwe.get(0)).get(1));
   
  }
}
 