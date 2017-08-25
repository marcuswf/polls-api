package au.com.mfapps.pollsapi.controller;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import au.com.mfapps.pollsapi.model.Question;
import au.com.mfapps.pollsapi.pojo.QuestionReq;
import au.com.mfapps.pollsapi.service.QuestionService;

@RestController
public class PollsApiController {

	@Autowired
	QuestionService questionService;
	
	
	/**
	 * Retrieve the Entry Point [GET]
	 */
	@RequestMapping(value= "/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<String> entryPoint() {
		JSONObject response = new JSONObject();
		
		try {
			response.put("questions_url","/questions");
		} catch (JSONException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}
	
	/**
	 * View a Questions Detail [GET] 
	 * Response 200
	 */
	@RequestMapping(value = "/questions/{questionId}", method = RequestMethod.GET)
	public Question viewQuestionDetail(@PathVariable Long questionId) {
		return  questionService.findQuestion(questionId);
	}
	
	/**
	 * Vote on a Choice [POST]
	 * Response 201
	 */
	@RequestMapping(value = "/questions/{questionId}/choices/{choiceId}", method = RequestMethod.POST)
	public ResponseEntity<Void> vote(@PathVariable Long questionId, @PathVariable Long choiceId) {

		questionService.vote(questionId, choiceId);
		return response201("/questions/{questionId}",questionId);
	}
	
	/**
	 * List All Questions [GET]
	 * Response 200 
	 */
	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public Page<Question> listAllQuestions(Pageable pageable,HttpServletResponse response){
		Page<Question> page = questionService.findAll(pageable);
		if(page.getNumber() < (page.getTotalPages()-1))
		response.addHeader( "link", "</questions?page="+(page.getNumber()+1)+">; rel=\"next\"");
		return page;
	}

	/**
	 * Create a New Question [POST] 
	 * Response 201 	
	 */
	@RequestMapping(value = "/questions", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody QuestionReq question) {

		if(question != null){
			long questionId = questionService.create(question);
			
			return response201("/questions/{questionId}",questionId);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private ResponseEntity<Void> response201(String path, long id){
		final URI location = ServletUriComponentsBuilder
	            .fromCurrentServletMapping().path(path).build()
	            .expand(id).toUri();
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		return new ResponseEntity<>(headers,HttpStatus.CREATED);
	}
	
}
