package com.mcfly.poll.service;

import com.mcfly.poll.payload.user_role.UserIdentityAvailability;
import com.mcfly.poll.payload.user_role.UserSummary;
import com.mcfly.poll.repository.polling.PollRepository;
import com.mcfly.poll.repository.polling.VoteRepository;
import com.mcfly.poll.repository.user_role.UserRepository;
import com.mcfly.poll.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PollRepository pollRepository;
    @Mock
    VoteRepository voteRepository;
    @InjectMocks
    UserService userService;

    @Test
    void contextLoads() {
        assertThat(userService).isNotNull();
    }

    @Test
    void userSummary() {
        final UserService userServiceMock = Mockito.mock();
        final UserSummary userSummaryToReturn = new UserSummary(1L, "username", "name");
        Mockito.when(userServiceMock.getUserSummary(Mockito.any(UserPrincipal.class)))
                .thenReturn(userSummaryToReturn);
        final UserSummary userSummary = userServiceMock.getUserSummary(new UserPrincipal());
        assertThat(userSummary).usingRecursiveComparison().isEqualTo(userSummaryToReturn);
    }

    @Test
    void checkEmailAvailability() {
        final String existingEmail = "existing@email.com";
        final String absentEmail = "absent@email.com";
        Mockito.when(userRepository.existsByEmail(existingEmail))
                .thenReturn(Boolean.TRUE);
        Mockito.when(userRepository.existsByEmail(absentEmail))
                .thenReturn(Boolean.FALSE);
        final UserIdentityAvailability actualExisting = userService.checkEmailAvailability(existingEmail);
        assertThat(actualExisting.getAvailable()).isEqualTo(Boolean.FALSE);
        final UserIdentityAvailability actualAbsent = userService.checkEmailAvailability(absentEmail);
        assertThat(actualAbsent.getAvailable()).isEqualTo(Boolean.TRUE);
        Mockito.verify(userRepository, Mockito.times(2)).existsByEmail(Mockito.any());
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getUserProfile() {

    }
}
