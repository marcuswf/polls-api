package au.com.mfapps.pollsapi.repository;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import au.com.mfapps.pollsapi.model.Choice;
import au.com.mfapps.pollsapi.model.Question;

@RepositoryRestResource()
public interface ChoiceRepository extends PagingAndSortingRepository<Choice, Long>, Serializable {
	
	Collection<Choice> findByQuestion(Question question);
	
	Choice findByIdAndQuestionId(Long choiceId,Long questionId);
}