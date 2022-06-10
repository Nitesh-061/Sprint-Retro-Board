package com.niteupad.retroBoard.service;

import com.niteupad.retroBoard.model.Comment;
import com.niteupad.retroBoard.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/*
The CommentService method in the preceding code is annotated with the @Service
stereotype annotation to mark it as a Spring service. Also, it has the @Transactional
annotation.
The CommentRepository will be auto-wired using the CommentService constructor
argument. Another notable thing is that the CommentService.saveAll method is
annotated with the @Transactional annotation with rollbackFor set to the Exception
class. This means that any code inside that method will be enclosed inside a transaction
and, if an exception is thrown, JpaTransactionManager will roll back the changes it
made in the database within that transaction.
 */

@Service
@Transactional(readOnly = true)
public class CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Comment> saveAll(List<Comment> comments) {
        LOGGER.info("Saving {}", comments);
        return commentRepository.saveAll(comments);
    }

    public List<Comment> getAllCommentsForToday() {
        LocalDate localDate = LocalDate.now();
        return commentRepository.findByCreatedYearAndMonthAndDay(localDate.getYear(), localDate.getMonth().getValue(), localDate.getDayOfMonth());
    }
}
