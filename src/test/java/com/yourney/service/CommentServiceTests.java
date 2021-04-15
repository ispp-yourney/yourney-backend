
package com.yourney.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.yourney.model.Comment;
import com.yourney.model.Itinerary;
import com.yourney.model.StatusType;
import com.yourney.repository.CommentRepository;
import com.yourney.security.model.User;


@SpringBootTest
class CommentServiceTests {

	private static final long TEST_COMMENT_ID_1 = 1;
	private static final long TEST_COMMENT_ID_NOT_FOUND = 4;
	private static final String	TEST_USER_USERNAME	= "user1";
	private static final String	TEST_USER_EMAIL	= "testuser@email.com";
	
	@Autowired
	protected CommentService commentService;
	
	@MockBean
	private CommentRepository commentRepository;

	public Comment c1 = new Comment();
	public User auth1 = new User();
	
	@BeforeEach
	void setup() {
		
		//User
		auth1.setId((long)1);
		auth1.setEmail(TEST_USER_EMAIL);
		auth1.setFirstName("Name 1");
		auth1.setLastName("Surname 1");
		auth1.setPassword("user1");
		auth1.setUsername(TEST_USER_USERNAME);
		auth1.setPlan(0);
		
		
		
		//Itineraries
		Itinerary it1 = new Itinerary();
	    
	    it1.setId((long)1);
	    it1.setName("itinerary test 1");
	    it1.setDescription("lorem ipsum 1");
	    it1.setStatus(StatusType.PUBLISHED);
	    it1.setBudget(10.);
	    it1.setEstimatedDays(2);
	    it1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it1.setViews(0);
	    it1.setAuthor(auth1);
	    
		it1.toString();
		it1.hashCode();

		
	    //Comments
	  	c1.setId(TEST_COMMENT_ID_1);
	  	c1.setContent("Comentario de prueba");
	  	c1.setRating(4);
		c1.setItinerary(it1);
		c1.setAuthor(auth1);
		
		
		given(this.commentRepository.findById((long) TEST_COMMENT_ID_1)).willReturn(Optional.of(c1));
		given(this.commentRepository.findById((long) TEST_COMMENT_ID_NOT_FOUND)).willReturn(Optional.empty());
		given(this.commentRepository.save(any())).willReturn(c1);
	
	}
	
	@Test
	void testFindById() {

		Optional<Comment> expected = this.commentService.findById(TEST_COMMENT_ID_1);

		assertTrue(expected.isPresent());
		assertSame(expected.get(), this.c1);
		
		
	}
	
	@Test
	void testFindByIdNotFound() {

		Optional<Comment> expected = this.commentService.findById(TEST_COMMENT_ID_NOT_FOUND);

		assertFalse(expected.isPresent());
		
	}
	
	@Test
	void testSave() {

		Comment expected = this.commentService.save(this.c1); 

		assertSame(expected, this.c1);
	}
	
}
