package com.acme.ex1.dao.impl;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.model.Movie;

@Repository
public class FoxMovieDaoImpl implements MovieDao {

	private List<Movie> someMovies = List.of(
			new Movie("A new hope", 1977),
			new Movie("Empire strikes back", 1980),
			new Movie("Return of the jedi", 1983)
			);
	
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
