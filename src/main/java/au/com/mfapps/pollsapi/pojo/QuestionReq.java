package au.com.mfapps.pollsapi.pojo;

import java.util.Collection;


public class QuestionReq{

	private String question;
	
	private Collection<String> choices;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Collection<String> getChoices() {
		return choices;
	}

	public void setChoices(Collection<String> choices) {
		this.choices = choices;
	}
}