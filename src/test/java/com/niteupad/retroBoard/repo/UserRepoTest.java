package com.niteupad.retroBoard.repo;

import com.niteupad.retroBoard.model.User;
import com.niteupad.retroBoard.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/*
One test, findByUsername_HappyPath_ShouldReturn1User, in the preceding test case
tests the UserRepository.findByUsername method by verifying whether it returns the
expected User object with a matching username. The other
test, save_HappyPath_ShouldSave1User, tests for the correct persistence of a User
object.
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername_HappyPath_ShouldReturn1User() throws Exception {
        // Given
        User user = new User();
        user.setUsername("Nitesh");
        user.setPassword("Test@123");
        user.setRole("USER");
        testEntityManager.persist(user);
        testEntityManager.flush();

        // When
        User actual = userRepository.findByUsername("Nitesh");

        // Then
        assertThat(actual).isEqualTo(user);
    }

    @Test
    public void save_HappyPath_ShouldSave1User() throws Exception {
        // Given
        User user = new User();
        user.setUsername("Nitesh");
        user.setPassword("Test@123");
        user.setRole("USER");

        // When
        User actual = userRepository.save(user);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }


}