package com.mcfly.poll.controller;

import com.mcfly.poll.controller.user_role.UserController;
import com.mcfly.poll.payload.user_role.UserIdentityAvailability;
import com.mcfly.poll.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;

    @Test
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
        assertThat(userService).isNotNull();
    }

    @Test
    public void checkUsernameAvailabilitySecured() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/checkUsernameAvailability").param("username", RandomStringUtils.randomAlphabetic(5)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


    @WithMockUser(username="fakeUsername")
    @Test
    public void checkExistingUsernameAvailabilityTest() throws Exception {
        final String fakeExistingUsername = "Fake existing user";
        Mockito.when(userService.checkUsernameAvailability(fakeExistingUsername)).thenReturn(new UserIdentityAvailability(false));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/checkUsernameAvailability").param("username", fakeExistingUsername))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(false));

        Mockito.when(userService.checkUsernameAvailability("No such username")).thenReturn(new UserIdentityAvailability(false));
    }

    @WithMockUser(username="fakeUsername")
    @Test
    public void checkAbsentUsernameAvailabilityTest() throws Exception {
        final String fakeAbsentUsername = "Fake absent user";
        Mockito.when(userService.checkUsernameAvailability(fakeAbsentUsername)).thenReturn(new UserIdentityAvailability(true));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/checkUsernameAvailability").param("username", fakeAbsentUsername))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(true));
    }

    /*
        TODO
        @RequestMapping("/api/user")

        @GetMapping("/me")
        @PreAuthorize("hasRole('USER')")

        @GetMapping("/checkUsernameAvailability")

        @GetMapping("/checkEmailAvailability")
     */
}
