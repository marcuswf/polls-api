package au.com.mfapps.pollsapi.repository;

import java.io.Serializable;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import au.com.mfapps.pollsapi.model.Question;

@RepositoryRestResource()
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long>, Serializable {
	
}