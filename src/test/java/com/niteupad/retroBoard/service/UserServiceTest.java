package com.niteupad.retroBoard.service;

import com.niteupad.retroBoard.model.User;
import com.niteupad.retroBoard.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void init() {
        this.userService = new UserService(userRepository);
    }

    @Test
    public void getAllCommentsForToday_HappyPath_ShouldReturn1Comment() {
        // Given
        User user = new User();
        user.setUsername("Nitesh");
        user.setPassword("Test@123");
        user.setRole("USER");

        when(userRepository.findByUsername("Nitesh")).thenReturn(user);

        // When
        UserDetails actual = userService.loadUserByUsername("Nitesh");

        // Then
        verify(userRepository, times(1)).findByUsername("Nitesh");
    }
}
