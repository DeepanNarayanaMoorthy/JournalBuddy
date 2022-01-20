package com.journalbuddy.JournalDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JournalData {
	
	private String filename;
	private String doi;
	private String title;
	private int pub_month;
	private int pub_year;
	private String container_name;
	private int volume;

	private int issue;
	private int issue_month;
	private int issue_year;
	private int reference_count;
	private int is_referenced_by_count;
	private List<HashMap<String, String>> funders;
	private List<HashMap<String, String>> authors;
	private List<String> subjects;
	
	public String getFilename() {
		return filename;
	}
	public List<HashMap<String, String>> getFunders() {
		return funders;
	}
	public void setFunders(List<HashMap<String, String>> funders) {
		this.funders = funders;
	}
	public List<HashMap<String, String>> getAuthors() {
		return authors;
	}
	public void setAuthors(List<HashMap<String, String>> authors) {
		this.authors = authors;
	}
	public List<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getPub_month() {
		return pub_month;
	}
	public void setPub_month(int pub_month) {
		this.pub_month = pub_month;
	}
	public String getContainer_name() {
		return container_name;
	}
	public void setContainer_name(String container_name) {
		this.container_name = container_name;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public int getIssue() {
		return issue;
	}
	public void setIssue(int issue) {
		this.issue = issue;
	}
	public int getIssue_month() {
		return issue_month;
	}
	public void setIssue_month(int issue_month) {
		this.issue_month = issue_month;
	}

	public int getReference_count() {
		return reference_count;
	}
	public void setReference_count(int reference_count) {
		this.reference_count = reference_count;
	}
	public int getIs_referenced_by_count() {
		return is_referenced_by_count;
	}
	public void setIs_referenced_by_count(int is_referenced_by_count) {
		this.is_referenced_by_count = is_referenced_by_count;
	}
	
	public int getPub_year() {
		return pub_year;
	}
	public void setPub_year(int pub_year) {
		this.pub_year = pub_year;
	}
	public int getIssue_year() {
		return issue_year;
	}
	public void setIssue_year(int issue_year) {
		this.issue_year = issue_year;
	}
	public void PrintAll() {
		System.out.print("getContainer_name\n");
		System.out.print(getContainer_name());
		System.out.print("getDoi\n");
		System.out.print(getDoi());
		System.out.print("getFilename\n");
		System.out.print(getFilename());
		System.out.print("getIs_referenced_by_count\n");
		System.out.print(getIs_referenced_by_count());
		System.out.print("getIssue\n");
		System.out.print(getIssue());
		System.out.print("getIssue_year\n");
		System.out.print(getIssue_year());
		System.out.print("getIssue_month\n");
		System.out.print(getIssue_month());
		System.out.print("getPub_year\n");
		System.out.print(getPub_year());
		System.out.print("getPub_month\n");
		System.out.print(getPub_month());
		System.out.print("getReference_count\n");
		System.out.print(getReference_count());
		System.out.print("getTitle\n");
		System.out.print(getTitle());
		System.out.print("getVolume\n");
		System.out.print(getVolume());
		System.out.print("getAuthors\n");
		System.out.print(getAuthors());
		System.out.print("getFunders\n");
		System.out.print(getFunders());
		System.out.print("getSubjects\n");
		System.out.print(getSubjects());
		System.out.print("\n");
	}
}
