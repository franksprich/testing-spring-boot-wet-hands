package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * {DESCRIPTION}
 *
 * @author Frank Sprich
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DisplayName("Test the Review repository")
class ReviewRepositoryTest {

    private final ReviewRepository repository;

    @Autowired
    ReviewRepositoryTest(ReviewRepository repository) {
        this.repository = repository;
    }

    @Nested
    @DisplayName("Inserting Tests")
    class GroupForInsertingTests {

        @Test
        public void shouldInsertAReview() {
            //Given
            Review review = Review.builder()
                    .title("title")
                    .content("content")
                    .stars(5).build();

            //When
            repository.save(review);

            //Then
            assertThat(review).isNotNull();
            assertThat(review.getId()).isGreaterThan(0);
        }

    }

    @Nested
    @DisplayName("Retrieval Tests")
    class GroupForRetrievalTests {

        @Test
        public void shouldFindAllReview() {
            //Given
            repository.save(Review.builder().title("title").content("content").stars(5).build());
            repository.save(Review.builder().title("title").content("content").stars(5).build());

            //When
            List<Review> reviews = repository.findAll();

            //Then
            assertThat(reviews).isNotNull();
            assertThat(reviews).isNotEmpty();
            assertThat(reviews.size()).isEqualTo(2);
        }

    }

}
