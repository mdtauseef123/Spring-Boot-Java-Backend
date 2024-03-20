package com.flight_test.flight_booking;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.rest.FlightScheduleController;
import com.flight_test.flight_booking.service.FlightScheduleService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class FlightBookingApplicationTests {
	@Test
	void contextLoads() {
	}

	private MockMvc mockMvc;
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private FlightScheduleService flightScheduleService;
	@InjectMocks
	private FlightScheduleController flightScheduleController;

	@BeforeEach
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(flightScheduleController).build();
	}
	FlightSchedule f1 = new FlightSchedule(1, "A009", "Test", "A", "B", LocalDateTime.of(2024, 3, 21, 15, 30, 0),
			LocalDateTime.of(2024, 3, 21, 15, 30, 0), 121.0,120.0,130.0,20,20,20);

	@Test
	public void getAllFlights_success() throws Exception{
		List<FlightSchedule> records = new ArrayList<>(Arrays.asList(f1));
		Mockito.when(flightScheduleService.getAllFlight()).thenReturn(records);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/getAllFlight")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
	}
}
