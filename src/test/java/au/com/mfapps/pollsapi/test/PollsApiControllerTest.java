package au.com.mfapps.pollsapi.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import au.com.mfapps.pollsapi.controller.PollsApiController;
import au.com.mfapps.pollsapi.model.Question;
import au.com.mfapps.pollsapi.pojo.QuestionReq;
import au.com.mfapps.pollsapi.service.QuestionService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PollsApiController.class, secure = false)
public class PollsApiControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private QuestionService questionService;
	
	public PollsApiControllerTest() {
	}
	
	/**
	 * Retrieve the Entry Point [GET]
	 */
	@Test
	public void testEntryPoint() throws Exception{
		String uri = "/";
		
		MvcResult result = mockMvc.perform(get(uri)).andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		String resultContent = result.getResponse().getContentAsString();
		assertThat(resultContent).isNotNull();
		assertThat(resultContent).isNotEmpty();
		JSONObject jsonObject = new JSONObject(resultContent);
		assertThat(jsonObject.getString("questions_url")).isEqualTo("/questions");
	}
	
	/**
	 * View a Questions Detail [GET] 
	 * Response 200
	 */
	@Test
	public void testQuestionDetail() throws Exception{
		
        when(questionService.findQuestion(any(Long.class))).thenAnswer(
				new Answer<Object>() {
				@Override
				public Object answer(InvocationOnMock invocation) throws Throwable {
					Question q = new Question("Favourite programming language?");
					return q;
				}
			}
			);
        
		String uri = "/questions/1";
		MvcResult result = mockMvc.perform(get(uri)).andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		String resultContent = result.getResponse().getContentAsString();
		assertThat(resultContent).isNotNull();
		assertThat(resultContent).isNotEmpty();
	}
	
	/**
	 * Vote on a Choice [POST]
	 * Response 201
	 */
	@Test
	public void testVote() throws Exception{
		
		String uri = "/questions/1/choices/1";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON).content("")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(result.getResponse().containsHeader("location")).isTrue();
		assertThat(result.getResponse().getRedirectedUrl()).contains("/questions");
	}
	
	/**
	 * Create a New Question [POST] 
	 * Response 201 	
	 */
	@Test
	public void testCreate() throws Exception{
		
        when(questionService.create(any(QuestionReq.class))).thenAnswer(
				new Answer<Object>() {
				@Override
				public Object answer(InvocationOnMock invocation) throws Throwable {
					return 1;
				}
			}
			);
		String uri = "/questions";
		String jsonContent = "{\"question\": \"Favourite programming language?\",\"choices\": [\"Swift\",\"Python\",\"Objective-C\",\"Ruby\"]}}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON).content(jsonContent)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(result.getResponse().containsHeader("location")).isTrue();
		assertThat(result.getResponse().getRedirectedUrl()).contains("/questions/1");
	}
	
}
