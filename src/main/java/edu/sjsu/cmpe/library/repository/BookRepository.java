package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;

public class BookRepository implements BookRepositoryInterface {
	public static int k = 0;
	/** In-memory map to store books. (Key, Value) -> (ISBN, Book) */
	private final ConcurrentHashMap<Long, Book> bookInMemoryMap;

	/** Never access this key directly; instead use generateISBNKey() */
	private long isbnKey;
	private long reviewid;
	private long authorid;

	public BookRepository(ConcurrentHashMap<Long, Book> bookMap) {
		checkNotNull(bookMap, "bookMap must not be null for BookRepository");
		bookInMemoryMap = bookMap;
		isbnKey = 0;
		reviewid = 0;
	}

	/**
	 * This should be called if and only if you are adding new books to the
	 * repository.
	 * 
	 * @return a new incremental ISBN number
	 */
	private final Long generateISBNKey() {
		// increment existing isbnKey and return the new value
		return Long.valueOf(++isbnKey);
	}

	/**
	 * This will auto-generate unique ISBN for new books.
	 */
	@Override
	public Book saveBook(Book newBook) {
		checkNotNull(newBook, "newBook instance must not be null");
		// Generate new ISBN
		Long isbn = generateISBNKey();
		newBook.setIsbn(isbn);

		// Generate author ids

		for (Author author : newBook.getAuthors()) {
			author.setId(authorid++);
		}

		bookInMemoryMap.putIfAbsent(isbn, newBook);

		return newBook;
	}

	public Long generateReviewId() {

		return Long.valueOf(++reviewid);
	}

	/**
	 * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#getBookByISBN(java.lang.Long)
	 */
	@Override
	public Book getBookByISBN(Long isbn) {
		checkArgument(isbn > 0,
				"ISBN was %s but expected greater than zero value", isbn);
		return bookInMemoryMap.get(isbn);
	}

	public void delete(Long isbn) {
		bookInMemoryMap.remove(isbn);

	}

	@Override
	public void update(Long isbn, String newstatus) {
		Book book = bookInMemoryMap.get(isbn);
		book.setStatus(newstatus);

	}

	@Override
	public void saveReview(Book book, Review review) {

		Long reviewId = generateReviewId();
		review.setId(reviewId);
		book.setReviews(review);
	}

}
