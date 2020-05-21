package com.acme.ex1.dao.impl;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.model.Movie;

@Repository
public class WarnerMovieDaoImpl implements MovieDao {

	private List<Movie> someMovies = List.of(
			new Movie("Fellowship of the ring", 2001),
			new Movie("The two towers", 2003),
			new Movie("Return of the king", 2005)
			);
	
	public WarnerMovieDaoImpl() {
		System.out.println("inside ctor");
	}
	@Override
	public Stream<Movie> retrieve(String title) {
		return this.someMovies.stream().filter(m -> m.getTitle().contains(title)
			/*	
			new Predicate<Movie>() {

			@Override
			public boolean test(Movie t) {
				return t.getTitle().contains(title);
			}
		}*/);
	}
}
