package com.aroha.pet.payload;

public class MentorFeedbackResponse {

	private String feedbackDate;
	private int questionId;
	private String mentorName;
	private long mentorId;
	private long learnerId;
	private String learnerName;
	private String feedback;
	private String question;
	private String resulstr;
	private String exceptionStr;
	private String sqlStr;
	private int notification;
	private String query_date;
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getMentorName() {
		return mentorName;
	}
	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}
	public long getMentorId() {
		return mentorId;
	}
	public void setMentorId(long mentorId) {
		this.mentorId = mentorId;
	}
	public long getLearnerId() {
		return learnerId;
	}
	public void setLearnerId(long learnerId) {
		this.learnerId = learnerId;
	}
	public String getLearnerName() {
		return learnerName;
	}
	public void setLearnerName(String learnerName) {
		this.learnerName = learnerName;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getResulstr() {
		return resulstr;
	}
	public void setResulstr(String resulstr) {
		this.resulstr = resulstr;
	}
	public String getExceptionStr() {
		return exceptionStr;
	}
	public void setExceptionStr(String exceptionStr) {
		this.exceptionStr = exceptionStr;
	}
	public String getSqlStr() {
		return sqlStr;
	}
	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}
	public int getNotification() {
		return notification;
	}
	public void setNotification(int notification) {
		this.notification = notification;
	}
	public String getQuery_date() {
		return query_date;
	}
	public void setQuery_date(String query_date) {
		this.query_date = query_date;
	}
	public String getFeedbackDate() {
		return feedbackDate;
	}
	public void setFeedbackDate(String feedbackDate) {
		this.feedbackDate = feedbackDate;
	} 
	
	
	
}
