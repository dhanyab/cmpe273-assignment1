package edu.sjsu.cmpe.library.dto;

import java.util.List;

import edu.sjsu.cmpe.library.domain.Review;

public class ReviewDto extends LinksDto {
	private List<Review> reviews;

	public ReviewDto() {
		}


	public ReviewDto(List<Review> reviews) {
		super();
		this.reviews=reviews;
		}


	/**
	 * @return the review
	 */
	public List<Review> getReview() {
		return reviews;
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(List<Review> reviews) {
		this.reviews = reviews;
	}
	

}
