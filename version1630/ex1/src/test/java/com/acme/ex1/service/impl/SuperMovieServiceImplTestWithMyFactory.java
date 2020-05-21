package com.acme.ex1.service.impl;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.acme.ex1.ApplicationFactory;
import com.acme.ex1.model.Movie;
import com.acme.ex1.service.MovieService;

public class SuperMovieServiceImplTestWithMyFactory {

	@Test
	public void testFind() {
		MovieService service = (MovieService) ApplicationFactory.getBean("superService");
		Stream<Movie> results = service.find("a");
		System.out.println("*****");
		List<Movie> list = results.collect(Collectors.toList());
		for (Movie movie : list) {
			System.out.println(movie.getTitle());
			assertTrue(movie.getTitle().contains("a"));
		}
	}

}
