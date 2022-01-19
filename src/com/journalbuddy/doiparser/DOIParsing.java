package com.journalbuddy.doiparser;

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

  public static void main(String[] args) throws IOException, JSONException {
    JSONObject json = readJsonFromUrl("https://api.crossref.org/works/10.1109/TMI.2015.2476354");
    JSONArray asd=(JSONArray) ((JSONObject) ((JSONObject) json.get("message")).get("published")).get("date-parts");
    System.out.println(json.toString());
    System.out.println(((JSONArray) asd.get(0)).get(0));
    System.out.println(((JSONArray) asd.get(0)).get(1));
   
  }
}
 