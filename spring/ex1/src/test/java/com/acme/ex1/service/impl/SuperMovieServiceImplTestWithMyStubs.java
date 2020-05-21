package com.acme.ex1.service.impl;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.acme.ex1.ApplicationFactory;
import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.model.Movie;
import com.acme.ex1.service.MovieService;

public class SuperMovieServiceImplTestWithMyStubs {

	@Test
	public void testFind() {
		MovieDao stub1 = new MovieDao() {
			
			@Override
			public Stream<Movie> retrieve(String title) {
				// TODO Auto-generated method stub
				return Stream.of(new Movie("m1", 2000));
			}
		};
		MovieDao stub2 = x -> Stream.of(new Movie("m2", 2001));
		
				
		SuperMovieServiceImpl service = new SuperMovieServiceImpl(Set.of(stub1, stub2));
		Stream<Movie> results = service.find("m");
		assertEquals(2, results.count());
	}

}
