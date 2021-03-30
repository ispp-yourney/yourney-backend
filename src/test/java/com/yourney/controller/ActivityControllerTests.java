package com.yourney.controller;

import static org.mockito.BDDMockito.given;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.yourney.config.SecurityConfig;
import com.yourney.model.Activity;
import com.yourney.service.ActivityService;

@WebMvcTest(value = ActivityController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfig.class)
public class ActivityControllerTests {

	private static final int TEST_ACTIVITY_ID = 1;

	@Autowired
	protected ActivityController activityController;

	@MockBean
	private ActivityService activityService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		
		//Activity
		Activity a1 = new Activity(); //'Sevilla Airport', 50, 600, 37.4180000, -5.8931100, 'SVQ', 'Sevilla'

//		a1.setId(1);
//		a1.setTitle("Activity 1");
//		a1.setDescription("Description 1");
//		a1.setDay(1);
//		a1.setCreateDate(LocalDateTime.now());
//		given(this.activityService.findById(ActivityControllerTests.TEST_ACTIVITY_ID)).willReturn(a1);
		
		
		//Itinerary
		

	}

}
