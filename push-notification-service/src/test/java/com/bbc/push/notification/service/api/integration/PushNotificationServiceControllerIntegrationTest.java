package com.bbc.push.notification.service.api.integration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletContext;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bbc.push.notification.service.model.Note;
import com.bbc.push.notification.service.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PushNotificationServiceControllerIntegrationTest {
	
	private static final Logger log = LoggerFactory.getLogger(PushNotificationServiceControllerIntegrationTest.class);
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private ServletContext servletContext;
	
	private URL base;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private String createUserEndpoint;
	
	private User userOne;
	private User userTwo;
	
	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/" + servletContext.getContextPath());
		this.createUserEndpoint = base.toString() + "/create/user";
		
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		this.userOne = new User();
		userOne.setUsername("Jon");
		userOne.setAccessToken("accessToken");
    	userOne.setCreationTime(now.format(formatter));
    	userOne.setNumOfNotificationsPushed(0);
    	
    	this.userTwo = new User();
    	userTwo.setUsername("Simon");
    	userTwo.setAccessToken("efgh5678");
    	userTwo.setCreationTime(now.format(formatter));
    	userTwo.setNumOfNotificationsPushed(0);
	}
	
	@Test
	public void testCreateUser() throws Exception {
		String accessToken = "accessToken";
		String username = "Jon";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(userOne, headers);
		
		log.info(createUserEndpoint);
		ResponseEntity<User> response = restTemplate.exchange(createUserEndpoint, HttpMethod.POST, entity, User.class);
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(response.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));
		assertThat(response.getBody(), is(notNullValue()));
		assertThat(response.getBody().getAccessToken(), equalTo(accessToken));
		assertThat(response.getBody().getUsername(), equalTo(username));
	}
	
	@Test
	public void testGetAllUsers() throws Exception {
		final int length = 2;
    	
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<User> userOneEntity = new HttpEntity<User>(userOne, headers);
    	ResponseEntity<User> postResponseOne = restTemplate.exchange(createUserEndpoint, HttpMethod.POST, userOneEntity, User.class);
		assertThat(postResponseOne.getStatusCode(), equalTo(HttpStatus.CREATED));
		
		HttpEntity<User> userTwoEntity = new HttpEntity<User>(userTwo, headers);
    	ResponseEntity<User> postResponseTwo = restTemplate.exchange(createUserEndpoint, HttpMethod.POST, userTwoEntity, User.class);
		assertThat(postResponseTwo.getStatusCode(), equalTo(HttpStatus.CREATED));
    	
		headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> allUsersEntity = new HttpEntity<String>("parameters", headers);
		
		String getAllUsersEndpoint = base.toString() + "/users/all";
		ResponseEntity<User[]> response = restTemplate.exchange(getAllUsersEndpoint, HttpMethod.GET, allUsersEntity, User[].class);
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));
		assertThat(response.getBody(), is(notNullValue()));
		assertThat(response.getBody().length, equalTo(length));
	}
	
	@Test
	public void testCreatePush() throws Exception {
		final String createPostEndpoint = base.toString() + "/create/push?username=";
		final String username = "Jon";
		Note note = new Note();
		note.setBody("Hello, this message has been sent from JUnit 4!");
		note.setTitle("Integration Test");
		note.setType("note");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<User> entity = new HttpEntity<User>(userOne, headers);
		
		ResponseEntity<User> createResponse = restTemplate.exchange(createUserEndpoint, HttpMethod.POST, entity, User.class);
		assertThat(createResponse.getStatusCode(), equalTo(HttpStatus.CREATED));
		
		HttpEntity<Note> postEntity = new HttpEntity<Note>(note, headers);
		log.info(new String(createPostEndpoint + username));
		ResponseEntity<User> postResponse = restTemplate.exchange(new String(createPostEndpoint + username), HttpMethod.POST, postEntity, User.class);
		log.info("postResponse: " + postResponse.toString());
		
		assertThat(postResponse.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(postResponse.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));
		assertThat(postResponse.getBody(), is(notNullValue()));
		assertThat(postResponse.getBody().getUsername(), equalTo(username));
		assertThat(postResponse.getBody().getAccessToken(), equalTo(userOne.getAccessToken()));
		assertThat(postResponse.getBody().getCreationTime(), equalTo(userOne.getCreationTime()));
		assertThat(postResponse.getBody().getNumOfNotificationsPushed(), equalTo(1));
	}

}
