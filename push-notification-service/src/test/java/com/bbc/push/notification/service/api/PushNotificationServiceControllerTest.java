package com.bbc.push.notification.service.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bbc.push.notification.service.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;

/**
 * PushNotificationServiceControllerTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PushNotificationServiceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
		
	private User user;
	
    @Before
    public void setUp() {
    	user = new User();
    }
    
    @After
    public void tearDown() {
    	
    }

    @Test
    public void testStatusEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
        		.accept(MediaType.APPLICATION_JSON_VALUE))
        		.andExpect(status().isOk())
        		.andExpect(content().string(equalTo("Push Notification Service")));
    }
    
    @Test
    public void testCreateUser() throws Exception {    	
    	user.setUsername("username1");
    	user.setAccessToken("access-token");
    	user.setCreationTime(LocalDateTime.now().withNano(0).toString());
    	user.setNumOfNotificationsPushed(0);
    	
    	String json = mapper.writeValueAsString(user);
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/create/user")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(json)
    			.accept(MediaType.APPLICATION_JSON_VALUE))
    			.andExpect(status().isCreated())
    			.andExpect(content().json(json));
    }
    
    @Test
    public void testGetAllUsers() throws Exception {    	    	
    	mockMvc.perform(MockMvcRequestBuilders.get("/users/all")
    			.accept(MediaType.APPLICATION_JSON_VALUE))
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$", hasSize(1)));
    }
        
}
