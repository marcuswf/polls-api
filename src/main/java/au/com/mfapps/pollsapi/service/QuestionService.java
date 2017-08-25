package au.com.mfapps.pollsapi.service;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import au.com.mfapps.pollsapi.model.Choice;
import au.com.mfapps.pollsapi.model.Question;
import au.com.mfapps.pollsapi.pojo.QuestionReq;
import au.com.mfapps.pollsapi.repository.ChoiceRepository;
import au.com.mfapps.pollsapi.repository.QuestionRepository;


@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	ChoiceRepository choiceRepository;
	
	public QuestionService() {
	}
	
	public long create(QuestionReq q){
		Question question = new Question(q.getQuestion());
		questionRepository.save(question);
		Collection<Choice> choices = new ArrayList<>();

		if(q.getChoices() != null)
			q.getChoices().forEach(choice -> {
				Choice c = new Choice(choice,question);
				choices.add(c);
		});
		choiceRepository.save(choices);
		return question.getId();
	}

	public void vote(Long questionId,Long choiceId){
		Choice choice = choiceRepository.findByIdAndQuestionId(choiceId,questionId);
		choice.setVotes(choice.getVotes()+1);
		choiceRepository.save(choice);
	}
	
	public Page<Question> findAll(Pageable pageable){
		return questionRepository.findAll(pageable);
	}

	public Question findQuestion(long questionId){
		return questionRepository.findOne(questionId);
	}
}
