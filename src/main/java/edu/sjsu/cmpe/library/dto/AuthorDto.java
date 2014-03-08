package edu.sjsu.cmpe.library.dto;

import java.util.List;

import edu.sjsu.cmpe.library.domain.Author;

public class AuthorDto extends LinksDto {
	private List<Author> authors;

	public AuthorDto() {
	}

	public AuthorDto(List<Author> authors) {
		super();
		this.authors = authors;
	}

	/**
	 * 
	 * @return
	 */
	public List<Author> getAuthors() {
		return authors;
	}

	/**
	 * 
	 * @param authors
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

}
