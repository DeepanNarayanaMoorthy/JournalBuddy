package com.journalbuddy.DataFilter;

import java.sql.Date;
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
	private String author_firstname;
	

	private String author_lastname;
	private String single_subject;
	private String single_funder;
	private String single_award;
	private Date pub_date;
	private Date issue_date;
	private String auth_seq;
	
	public String getAuth_seq() {
		return auth_seq;
	}
	public void setAuth_seq(String auth_seq) {
		this.auth_seq = auth_seq;
	}
	
	public String PrintAll() {
		return "JournalFilterData [filename=" + filename + ", doi=" + doi + ", title=" + title + ", pub_month="
				+ pub_month + ", pub_year=" + pub_year + ", container_name=" + container_name + ", volume=" + volume
				+ ", issue=" + issue + ", issue_month=" + issue_month + ", issue_year=" + issue_year
				+ ", reference_count=" + reference_count + ", is_referenced_by_count=" + is_referenced_by_count
				+ ", funders=" + funders + ", authors=" + authors + ", subjects=" + subjects + ", author_firstname="
				+ author_firstname + ", author_lastname=" + author_lastname + ", single_subject=" + single_subject
				+ ", single_funder=" + single_funder + ", single_award=" + single_award + ", pub_date=" + pub_date
				+ ", issue_date=" + issue_date + ", auth_seq=" + auth_seq + "]";
	}
	public Date getPub_date() {
		return pub_date;
	}
	public void setPub_date(Date pub_date) {
		this.pub_date = pub_date;
	}
	public Date getIssue_date() {
		return issue_date;
	}
	public void setIssue_date(Date issue_date) {
		this.issue_date = issue_date;
	}
	public String getAuthor_firstname() {
		return author_firstname;
	}
	public void setAuthor_firstname(String author_firstname) {
		this.author_firstname = author_firstname;
	}
	public String getAuthor_lastname() {
		return author_lastname;
	}
	public void setAuthor_lastname(String author_lastname) {
		this.author_lastname = author_lastname;
	}
	public String getSingle_subject() {
		return single_subject;
	}
	public void setSingle_subject(String single_subject) {
		this.single_subject = single_subject;
	}
	public String getSingle_funder() {
		return single_funder;
	}
	public void setSingle_funder(String single_funder) {
		this.single_funder = single_funder;
	}
	public String getSingle_award() {
		return single_award;
	}
	public void setSingle_award(String single_award) {
		this.single_award = single_award;
	}
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

}
