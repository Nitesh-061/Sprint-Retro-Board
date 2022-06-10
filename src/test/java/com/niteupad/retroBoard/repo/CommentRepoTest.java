package com.niteupad.retroBoard.repo;

import com.niteupad.retroBoard.model.Comment;
import com.niteupad.retroBoard.model.CommentType;
import com.niteupad.retroBoard.repository.CommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
The preceding test case uses a TestEntityManager private member auto-wired (autowiring
private members should be kept to the minimum as it is not a best practice) to the
JUnit test and persists Comment by flushing it to the temporary persistence using
the TestEntityManager.flush method and then, in one test, tests whether it can be
successfully retrieved using the
CommentRepository.findByCreatedYearAndMonthAndDay method. Furthermore, in
the next test, it tests whether it could successfully save a Comment.
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void findByCreatedYearAndMonthAndDay_HappyPath_ShouldReturn1Comment() {
        // Given
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        testEntityManager.persist(comment);
        testEntityManager.flush();

        // When
        LocalDate now = LocalDate.now();
        List<Comment> comments = commentRepository.findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonth().getValue(), now.getDayOfMonth());

        // Then
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0)).hasFieldOrPropertyWithValue("comment", "Test");
    }

    @Test
    public void save_HappyPath_ShouldSave1Comment() {
        // Given
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        // When
        Comment saved = commentRepository.save(comment);

        // Then
        assertThat(testEntityManager.find(Comment.class, saved.getId())).isEqualTo(saved);
    }
}
