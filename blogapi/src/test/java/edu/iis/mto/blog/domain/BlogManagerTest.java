package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.services.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.lang.model.util.Types;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BlogManagerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BlogPostRepository blogPostRepository;

    @MockBean
    private LikePostRepository likePostRepository;

    @Autowired
    private BlogService blogService;

    @Captor
    private ArgumentCaptor<User> userParam;

    @Captor
    private ArgumentCaptor<LikePost> likePostParam;

    private User samplePostCreator;
    private User samplePostLiker;
    private BlogPost sampleBlogPost;

    @BeforeEach
    void setUp() {
        createSampleUsers();
        createSampleBlogPost();
    }


    private void createSampleUsers() {
        samplePostCreator = new User();
        samplePostCreator.setId(1L);
        samplePostCreator.setEmail("POST_CREATOR");
        samplePostCreator.setAccountStatus(AccountStatus.CONFIRMED);

        samplePostLiker = new User();
        samplePostLiker.setId(2L);
        samplePostLiker.setEmail("POST_LIKER");
        samplePostLiker.setAccountStatus(AccountStatus.NEW);
    }

    private void createSampleBlogPost() {
        sampleBlogPost = new BlogPost();
        sampleBlogPost.setId(1L);
        sampleBlogPost.setUser(samplePostCreator);
        sampleBlogPost.setLikes(Collections.emptyList());
        sampleBlogPost.setEntry("DEFAULT_ENTRY");
    }

    @Test
    void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        assertThat(user.getAccountStatus(), equalTo(AccountStatus.NEW));
    }

    @Test
    void shouldThrowDomainErrorWhenUserWhoLikesThePostIsNotConfirmed() {
        // given
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(samplePostLiker));
        when(blogPostRepository.findById(any(Long.class))).thenReturn(Optional.of(sampleBlogPost));

        // when & then
        DomainError domainError = assertThrows(DomainError.class, () -> blogService.addLikeToPost(samplePostLiker.getId(), sampleBlogPost.getId()));
        assertEquals(DomainError.USER_NOT_CONFIRMED, domainError.getMessage());
    }

    @Test
    void shouldSuccessfullyLikePostWhenUserIsConfirmed() {
        // given
        int expectedNumberOfCapturedLikePosts = 1;
        samplePostLiker.setAccountStatus(AccountStatus.CONFIRMED);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(samplePostLiker));
        when(blogPostRepository.findById(any(Long.class))).thenReturn(Optional.of(sampleBlogPost));
        when(likePostRepository.findByUserAndPost(any(User.class), any(BlogPost.class))).thenReturn(Optional.empty());

        // when
        blogService.addLikeToPost(samplePostLiker.getId(), sampleBlogPost.getId());

        // then
        verify(likePostRepository).save(likePostParam.capture());
        List<LikePost> capturedLikePosts = likePostParam.getAllValues();
        assertEquals(expectedNumberOfCapturedLikePosts, capturedLikePosts.size());
        LikePost capturedLikePost = capturedLikePosts.get(0);
        assertEquals(samplePostLiker, capturedLikePost.getUser());
        assertEquals(sampleBlogPost, capturedLikePost.getPost());
    }



}
