package com.niteupad.retroBoard.controller;

import com.niteupad.retroBoard.model.Comment;
import com.niteupad.retroBoard.model.CommentType;
import com.niteupad.retroBoard.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CommentController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<Comment> allComments = commentService.getAllCommentsForToday();
        Map<CommentType, List<Comment>> groupedComments = allComments.stream().collect(Collectors.groupingBy(Comment::getType));
        model.addAttribute("starComments", groupedComments.get(CommentType.STAR));
        model.addAttribute("deltaComments", groupedComments.get(CommentType.DELTA));
        model.addAttribute("plusComments", groupedComments.get(CommentType.PLUS));

        return "comment";
    }

    @PostMapping("/comment")
    public String createComment(@RequestParam(name = "plusComment", required = false) String plusComment,
                                @RequestParam(name = "deltaComment", required = false) String deltaComment,
                                @RequestParam(name = "starComment", required = false) String starComment) {
        List<Comment> comments = new ArrayList<>();

        if (StringUtils.isNotEmpty(plusComment)) {
            comments.add(getComment(plusComment, CommentType.PLUS));
        }

        if (StringUtils.isNotEmpty(deltaComment)) {
            comments.add(getComment(deltaComment, CommentType.DELTA));
        }

        if (StringUtils.isNotEmpty(starComment)) {
            comments.add(getComment(starComment, CommentType.STAR));
        }

        if (!comments.isEmpty()) {
            LOGGER.info("Saved {}", commentService.saveAll(comments));
        }

        return "redirect:/";
    }

    private Comment getComment(String comment, CommentType commentType) {
        Comment commentObject = new Comment();
        commentObject.setType(commentType);
        commentObject.setComment(comment);

        return commentObject;
    }
}
