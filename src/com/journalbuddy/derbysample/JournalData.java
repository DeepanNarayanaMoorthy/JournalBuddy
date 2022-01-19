package com.journalbuddy.derbysample;

public class JournalData {
	
	private String filename;
	private String doi;
	private String title;
	private String subtitle;
	private int pub_month;
	private int pub_date;
	private String container_name;
	private int volume;
	private int issue;
	private int issue_month;
	private int issue_date;
	private int reference_count;
	private int is_referenced_by_count;
	
	public String getFilename() {
		return filename;
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
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public int getPub_month() {
		return pub_month;
	}
	public void setPub_month(int pub_month) {
		this.pub_month = pub_month;
	}
	public int getPub_date() {
		return pub_date;
	}
	public void setPub_date(int pub_date) {
		this.pub_date = pub_date;
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
	public int getIssue_date() {
		return issue_date;
	}
	public void setIssue_date(int issue_date) {
		this.issue_date = issue_date;
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
}
