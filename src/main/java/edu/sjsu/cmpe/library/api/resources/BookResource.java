package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotEmpty;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
	/** bookRepository instance */
	private final BookRepositoryInterface bookRepository;

	/**
	 * BookResource constructor
	 * 
	 * @param bookRepository
	 *            a BookRepository instance
	 */
	public BookResource(BookRepositoryInterface bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GET
	@Path("/{isbn}")
	@Timed(name = "view-book")
	public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
		Book book = bookRepository.getBookByISBN(isbn.get());
		BookDto bookResponse = new BookDto(book);
		bookResponse.addLink(new LinkDto("view-book", "/books/"
				+ book.getIsbn(), "GET"));
		bookResponse.addLink(new LinkDto("update-book", "/books/"
				+ book.getIsbn(), "PUT"));
		bookResponse.addLink(new LinkDto("delete-book", "/books/"
				+ book.getIsbn(), "DELETE"));
		bookResponse.addLink(new LinkDto("create-review", "/books/"
				+ book.getIsbn(), "POST"));
		if (!book.getReviews().isEmpty())
			bookResponse.addLink(new LinkDto("view-all-reviews", "/books/"
					+ book.getIsbn(), "GET"));
		return bookResponse;
	}

	@POST
	@Timed(name = "create-book")
	public Response createBook(@Valid Book request) {
		// Store the new book in the BookRepository so that we can retrieve it.
		Book savedBook = bookRepository.saveBook(request);
		String location = "/books/" + savedBook.getIsbn();
		LinksDto linkResponse = new LinksDto();
		linkResponse.addLink(new LinkDto("view-book", location, "GET"));
		linkResponse.addLink(new LinkDto("update-book", location, "PUT"));
		linkResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
		linkResponse.addLink(new LinkDto("create-review",
				location + "/reviews", "POST"));

		return Response.status(201).entity(linkResponse).build();
	}

	@DELETE
	@Path("/{isbn}")
	@Timed(name = "delete-book")
	public Response deleteBookByIsbn(@PathParam("isbn") LongParam isbn) {
		bookRepository.delete(isbn.get());
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("create-book", "/books", "POST"));
		return Response.ok(links).entity(links).build();
	}

	@PUT
	@Path("/{isbn}")
	@Timed(name = "update-book")
	public Response updateBookByIsbn(@PathParam("isbn") LongParam isbn,
			@QueryParam("status") String newstatus) {
		Book book = bookRepository.getBookByISBN(isbn.get());
		bookRepository.update(isbn.get(), newstatus);
		LinksDto bookResponse = new LinksDto();
		bookResponse.addLink(new LinkDto("view-book", "/books/"
				+ book.getIsbn(), "GET"));
		bookResponse.addLink(new LinkDto("update-book", "/books/"
				+ book.getIsbn(), "PUT"));
		bookResponse.addLink(new LinkDto("delete-book", "/books"
				+ book.getIsbn(), "DELETE"));
		bookResponse.addLink(new LinkDto("create-review", "/books"
				+ book.getIsbn(), "POST"));
		if (!book.getReviews().isEmpty())

			bookResponse.addLink(new LinkDto("view-all-reviews", "/books"
					+ book.getIsbn(), "GET"));

		return Response.status(201).entity(bookResponse).build();

	}

	@POST
	@Path("/{isbn}/reviews")
	public Response createReview(Review review,
			@PathParam("isbn") LongParam isbn) {

		Book book = bookRepository.getBookByISBN(isbn.get());
		bookRepository.saveReview(book, review);
		LinksDto reviewResponse = new LinksDto();

		String location = "/books/" + book.getIsbn();
		reviewResponse.addLink(new LinkDto("view-review", location
				+ "/reviews/" + review.getId(), "GET"));

		return Response.status(201).entity(reviewResponse).build();
	}

	@GET
	@Path("/{isbn}/reviews/{id}")
	public ReviewDto getReviewById(@PathParam("isbn") LongParam isbn,
			@PathParam("id") LongParam id) {

		Book book = bookRepository.getBookByISBN(isbn.get());
		Review review = book.getReviewById(id.get());
		ReviewDto reviewResponse = new ReviewDto();
		List<Review> reviewList = new ArrayList<Review>();
		reviewList.add(review);
		reviewResponse.setReview(reviewList);
		reviewResponse.addLink(new LinkDto("view-review", "/books/"
				+ book.getIsbn() + "/reviews/" + review.getId(), "GET"));

		return reviewResponse;

	}

	@GET
	@Path("/{isbn}/reviews")
	public ReviewDto getAllReviews(@PathParam("isbn") LongParam isbn) {

		Book book = bookRepository.getBookByISBN(isbn.get());
		ReviewDto reviewResponse = new ReviewDto();
		reviewResponse.setReview(book.getReviews());

		return reviewResponse;

	}

	@GET
	@Path("/{isbn}/authors/{id}")
	public AuthorDto getAuthorById(@PathParam("isbn") LongParam isbn,
			@PathParam("id") LongParam id) {

		Book book = bookRepository.getBookByISBN(isbn.get());
		Author author = book.getAuthorById(id.get());
		AuthorDto authorResponse = new AuthorDto();
		List<Author> authorList = new ArrayList<Author>();
		authorList.add(author);
		authorResponse.setAuthors(authorList);
		authorResponse.addLink(new LinkDto("view-author", "/books/"
				+ book.getIsbn() + "/authors/" + author.getId(), "GET"));

		return authorResponse;

	}

	@GET
	@Path("/{isbn}/authors")
	public AuthorDto getAuthors(@PathParam("isbn") LongParam isbn) {

		Book book = bookRepository.getBookByISBN(isbn.get());
		AuthorDto authorResponse = new AuthorDto();
		authorResponse.setAuthors(book.getAuthors());

		return authorResponse;

	}

}
