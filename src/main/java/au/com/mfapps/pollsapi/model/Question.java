package au.com.mfapps.pollsapi.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question extends Model{

	private static final long serialVersionUID = -1346630051501089841L;

	@NotNull
	@JsonIgnore
	private Date publishedAt;
	
	@NotNull
	private String question;
	
	@OneToMany(mappedBy = "question")
	private Set<Choice> choices;

	public Question(){}
	
	public Question(String question){
		this.question = question;
		this.publishedAt = new Date();
	}
	
	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Set<Choice> getChoices() {
		return choices;
	}

	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
	}
	
	public String getUrl(){
		return "/questions/"+getId();
	}
	
	@JsonProperty("published_at")
	private String getConvertedPublishedAt(){
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		return df.format(getPublishedAt());
	}
}