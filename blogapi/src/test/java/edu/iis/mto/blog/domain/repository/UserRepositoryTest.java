package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user;

    private final static String SAMPLE_NOT_MATCHING_FIRSTNAME = "Jakub";
    private final static String SAMPLE_NOT_MATCHING_LASTNAME = "Reszka";
    private final static String SAMPLE_NOT_MATCHING_EMAIL = "gmail";



    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Bean");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);
    }

    @Test
    void shouldFindNoUsersIfRepositoryIsEmpty() {

        List<User> users = repository.findAll();

        assertThat(users, hasSize(0));
    }

    @Test
    void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user);
        List<User> users = repository.findAll();

        assertThat(users, hasSize(1));
        assertThat(users.get(0)
                        .getEmail(),
                equalTo(persistedUser.getEmail()));
    }

    @Test
    void shouldStoreANewUser() {

        User persistedUser = repository.save(user);

        assertThat(persistedUser.getId(), notNullValue());
    }

    @Test
    void shouldNotFindUserNorByFirstNameLastNameAndEmail() {
        // given
        User savedUser = repository.save(user);
        final int expectedCountOfUsersFound = 0;

        // when
        List<User> usersFound = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(SAMPLE_NOT_MATCHING_FIRSTNAME, SAMPLE_NOT_MATCHING_LASTNAME, SAMPLE_NOT_MATCHING_EMAIL);

        // then
        int countOfUsersFound = usersFound.size();
        assertEquals(expectedCountOfUsersFound, countOfUsersFound);
    }

    @Test
    void shouldFindUserByFirstNameIgnoringCase() {
        // given
        User savedUser = repository.save(user);
        final int expectedCountOfUsersFound = 1;

        // when
        List<User> usersFound = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("jan", SAMPLE_NOT_MATCHING_LASTNAME, SAMPLE_NOT_MATCHING_EMAIL);

        // then
        int countOfUsersFound = usersFound.size();
        assertEquals(expectedCountOfUsersFound, countOfUsersFound);
        assertEquals(savedUser, usersFound.get(0));
    }

    @Test
    void shouldFindUserByLastNameIgnoringCase() {
        // given
        User savedUser = repository.save(user);
        final int expectedCountOfUsersFound = 1;

        // when
        List<User> usersFound = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(SAMPLE_NOT_MATCHING_FIRSTNAME, "bean", SAMPLE_NOT_MATCHING_EMAIL);

        // then
        int countOfUsersFound = usersFound.size();
        assertEquals(expectedCountOfUsersFound, countOfUsersFound);
        assertEquals(savedUser, usersFound.get(0));
    }

}
