package com.acme.ex4.endpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



import com.acme.ex3.ApplicationConfig;
import com.acme.ex4.WebAppInitializer.RestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={RestConfiguration.class, ApplicationConfig.class})
@WebAppConfiguration
public class BookEndpointTest {


	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
	}
	
	@Test
	public void getBook200() throws Exception {
		this.mockMvc.perform(
				get("/books/1")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk());
	}
	
	@Test 
	public void getBooks200() throws Exception {
		this.mockMvc.perform(
				get("/books")
				.param("title", "%25%25")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk());
	}
	
	@Test 
	public void getBooks400() throws Exception {
		this.mockMvc.perform(
				get("/books")
				.param("title", "a")
				.accept(MediaType.APPLICATION_JSON)				
				)
		.andExpect(status().isBadRequest());
	}
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test 
	public void getSearch200() throws Exception {
		String json = mapper.writeValueAsString(Map.of("title", "%%",  "authorName",  "%%"));
		this.mockMvc.perform(
				post("/searches")
				.param("domain", "book")
				.content(json)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk());
	}
	
	@Test 
	public void getSearch400() throws Exception {
		String json = mapper.writeValueAsString(Map.of("title", "a",  "authorName",  "%%"));
		this.mockMvc.perform(
				post("/searches")
				.param("domain", "book")
				.content(json)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isBadRequest());
	}
}
