
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
import com.yourney.model.Image;
import com.yourney.model.Itinerary;
import com.yourney.model.StatusType;
import com.yourney.repository.CommentRepository;
import com.yourney.repository.ImageRepository;
import com.yourney.security.model.User;

@SpringBootTest
class ImageServiceTests {

	private static final int TEST_IMAGE_ID = 1;
	private static final int TEST_INEXISTENT_ID = 99;
	private static final int TEST_DEFAULT_IMAGE_ID = 78;

	@Autowired
	protected ImageService imageService;

	@MockBean
	private ImageRepository imageRepository;

	public Image i1 = new Image();

	@BeforeEach
	void setup() {

		// Images
		i1.setId(TEST_IMAGE_ID);
		i1.setName("Image test landmark");
		i1.setImageUrl("https://elviajista.com/wp-content/uploads/2020/06/habanacuba-730x487.jpg");
		i1.setCloudinaryId("1");

		Image defaultImage = new Image();
		defaultImage.setId(TEST_DEFAULT_IMAGE_ID);
		defaultImage.setName("Image test landmark");
		defaultImage.setImageUrl("https://elviajista.com/wp-content/uploads/2020/06/habanacuba-730x487.jpg");
		defaultImage.setCloudinaryId("1");

		// Itineraries
		
		given(this.imageRepository.findById((long) TEST_IMAGE_ID)).willReturn(Optional.of(i1));
		given(this.imageRepository.findById((long) TEST_INEXISTENT_ID)).willReturn(Optional.empty());
		given(this.imageRepository.existsById((long) TEST_IMAGE_ID)).willReturn(true);
		given(this.imageRepository.existsById((long) TEST_INEXISTENT_ID)).willReturn(false);
		given(this.imageRepository.save(any())).willReturn(i1);
	}

	@Test
	void testFindById() {

		Optional<Image> expected = this.imageService.findById(TEST_IMAGE_ID);

		assertTrue(expected.isPresent());
		assertSame(expected.get(), this.i1);

	}

	@Test
	void testFindByIdNotFound() {

		Optional<Image> expected = this.imageService.findById(TEST_INEXISTENT_ID);

		assertFalse(expected.isPresent());

	}

	@Test
	void testExistById() {

		Boolean expected = this.imageService.existsById(TEST_IMAGE_ID);

		assertTrue(expected);

	}
	
	@Test
	void testExistByIdNotFound() {

		Boolean expected = this.imageService.existsById(TEST_INEXISTENT_ID);

		assertFalse(expected);

	}

	@Test
	void testSave() {
		System.out.println(this.i1);
		Image expected = this.imageService.save(this.i1);
		System.out.println(expected);
		assertSame(expected, this.i1);
	}

}
