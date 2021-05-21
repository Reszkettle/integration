package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private LikePostRepository likePostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    private User sampleUser;
    private BlogPost sampleBlogPost;

    @BeforeEach
    void createSaveSampleEntities() {
        createSaveSampleUser();
        createSaveSampleBlogPost();
    }

    private void createSaveSampleBlogPost() {
        sampleBlogPost = new BlogPost();
        sampleBlogPost.setEntry("entry");
        sampleBlogPost.setLikes(new LinkedList<>());
        sampleBlogPost.setUser(sampleUser);
        blogPostRepository.save(sampleBlogPost);
    }

    private void createSaveSampleUser() {
        sampleUser = new User();
        sampleUser.setFirstName("firstname");
        sampleUser.setLastName("lastname");
        sampleUser.setEmail("email");
        sampleUser.setAccountStatus(AccountStatus.NEW);
        sampleUser = userRepository.save(sampleUser);
    }

}
