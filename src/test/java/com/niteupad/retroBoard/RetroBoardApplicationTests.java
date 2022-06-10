package com.niteupad.retroBoard;

import com.niteupad.retroBoard.model.Comment;
import com.niteupad.retroBoard.model.CommentType;
import com.niteupad.retroBoard.repository.CommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RetroBoardApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CommentRepository commentRepository;

	@org.junit.Test
	public void saveComments_HappyPath_ShouldReturnStatus302() throws Exception {
		// When
		ResultActions resultActions = mockMvc.perform(post("/comment").with(csrf()).with(user("nitesh").roles("USER")).requestAttr("plusComment", "Test Plus"));

		// Then
		resultActions
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"))
				.andExpect(redirectedUrl("/"));
	}

	@Test
	public void getComments_HappyPath_ShouldReturnStatus200() throws Exception {
		// Given
		Comment comment = new Comment();
		comment.setComment("Test Comment");
		comment.setType(CommentType.PLUS);
		comment.setCreatedBy("nitesh");
		commentRepository.save(comment);

		// When
		ResultActions resultActions = mockMvc.perform(get("/").with(user("nitesh").roles("USER")));

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(view().name("comment"))
				.andExpect(model().attribute("plusComments", hasSize(1)))
				.andExpect(model().attribute("plusComments", hasItem(
						allOf(
								hasProperty("createdBy", is("nitesh")),
								hasProperty("comment", is("Test Comment"))
						)
				)));

	}

}
