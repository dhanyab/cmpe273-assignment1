package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;



public class Book {
    private long isbn; 
    
    @NotEmpty
    private String title;
    
    @NotEmpty
    private String publicationdate; 
    
    private String lang;
    private int numpages;
    private String status="available";
    @NotEmpty
    private List<Author> authors = new ArrayList<Author>();
    private List<Review> reviews = new ArrayList<Review>();
	
    public Review getReviewById(long id)
    {
    	for(Review review : reviews)
    	{
    		if(review.getId() == id)
    			return review;
    	}
		return null;
    }
    
    public Author getAuthorById(long id)
    {
    	for(Author author : authors)
    	{
    		if(author.getId() == id)
    			return author;
    	}
		return null;
    }
    
    /**

	// add more fields here
	/**
	 * @return the isbn
	 */
	/**
	 * @return the isbn
	 */
	public long getIsbn() {
		return isbn;
	}
	/**
	 * @return the reviews
	 */
	public List<Review> getReviews() {
		return reviews;
	}
	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(Review review) {
		this.reviews.add(review);
	}
	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the publicationdate
	 */
	public String getPublication() {
		return publicationdate;
	}
	/**
	 * @param publicationdate the publicationdate to set
	 */
	public void setPublication(String publicationdate) {
		this.publicationdate = publicationdate;
	}
	/**
	 * @return the lang
	 */
	public String getLanguage() {
		return lang;
	}
	/**
	 * @param lang the lang to set
	 */
	public void setLanguage(String lang) {
		this.lang = lang;
	}
	/**
	 * @return the numpages
	 */
	public int getNumpages() {
		return numpages;
	}
	/**
	 * @param numpages the numpages to set
	 */
	public void setNumpages(int numpages) {
		this.numpages = numpages;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	 /**
		 * @return the authors
		 */
		public List<Author> getAuthors() {
			return authors;
		}
		/**
		 * @param authors the authors to set
		 */
		public void setAuthors(List<Author> authors) {
			this.authors = authors;
		}
		
		
}