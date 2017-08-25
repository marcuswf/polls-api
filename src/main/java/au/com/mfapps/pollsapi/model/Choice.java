package au.com.mfapps.pollsapi.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Choice extends Model{

	private static final long serialVersionUID = -1346630051501089841L;
	
	@NotNull
	private String choice;
	
	@NotNull
	private int votes;
	
	@JsonIgnore
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name = "questionId")
	private Question question;
	
	
	public Choice(){}
	
	public Choice(String choice,Question question){
		this.choice = choice;
		this.question = question;
		this.votes=0;
	}
	
	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public String getUrl(){
		return "/questions/"+question.getId()+"/choices/"+getId();
	}
}