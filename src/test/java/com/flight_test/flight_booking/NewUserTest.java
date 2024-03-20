package com.flight_test.flight_booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.flight_test.flight_booking.entity.User;
import com.flight_test.flight_booking.rest.UserDetailsController;
import com.flight_test.flight_booking.service.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class NewUserTest {
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private UserDetailsService userDetailsService;
    @InjectMocks
    private UserDetailsController userDetailsController;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userDetailsController).build();
    }

    @Test
    public void login_success() throws Exception{
        User user = User.builder()
                .id(1)
                .name("abc")
                .username("abc@gmail.com")
                .password("12345").phoneNo("45678").build();
        Mockito.when(userDetailsService.loginUser("abc@gmail.com", "12345")).thenReturn(user);
        String content = objectMapper.writeValueAsString(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
    }
}

