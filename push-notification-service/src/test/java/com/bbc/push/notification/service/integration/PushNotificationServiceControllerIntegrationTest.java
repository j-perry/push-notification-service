package com.bbc.push.notification.service.integration;

import java.net.URL;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.bbc.push.notification.service.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PushNotificationServiceControllerIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private URL base;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
	}
	
	@Test
	public void testCreateUser() throws Exception {
		String accessToken = "abcd1234";
		String username = "Jon";
		
		User user = new User();
		user.setUsername(username);
		user.setAccessToken(accessToken);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
		
		String createUserEndpoint = base.toString() + "/create/user";
		ResponseEntity<User> response = restTemplate.exchange(createUserEndpoint, HttpMethod.POST, entity, User.class);
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(response.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));
		assertThat(response.getBody(), is(notNullValue()));
		assertThat(response.getBody().getAccessToken(), equalTo(accessToken));
		assertThat(response.getBody().getUsername(), equalTo(username));
	}

}
