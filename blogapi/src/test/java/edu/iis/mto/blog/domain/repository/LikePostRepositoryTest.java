package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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


    @Test
    void shouldSaveOneLikePost() {
        // given
        LikePost likePost = new LikePost();
        likePost.setPost(sampleBlogPost);
        likePost.setUser(sampleUser);
        int expectedNumberOfSavedLikePosts = 1;

        // when
        LikePost savedLikePost = likePostRepository.save(likePost);

        // then
        List<LikePost> allSavedLikePosts = likePostRepository.findAll();
        assertEquals(expectedNumberOfSavedLikePosts, allSavedLikePosts.size());
        assertEquals(savedLikePost, allSavedLikePosts.get(0));
    }
    
    @Test
    void shouldThrowExceptionWhenSavingLikePostWithUserThatHasNotBeenSaved() {
        // given
        User notSavedUser = new User();
        LikePost likePost = new LikePost();
        likePost.setPost(sampleBlogPost);
        likePost.setUser(notSavedUser);

        // when & then
        assertThrows(Exception.class, () -> likePostRepository.save(likePost));
    }

    @Test
    void shouldThrowExceptionWhenSavingLikePostWithBlogPostThatHasNotBeenSaved() {
        // given
        BlogPost notSavedBlogPost = new BlogPost();
        LikePost likePost = new LikePost();
        likePost.setPost(notSavedBlogPost);
        likePost.setUser(sampleUser);

        // when & then
        assertThrows(Exception.class, () -> likePostRepository.save(likePost));
    }



}
