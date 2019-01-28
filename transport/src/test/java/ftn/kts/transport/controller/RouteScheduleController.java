package ftn.kts.transport.controller;

import static ftn.kts.transport.constants.UserConstants.DB_USER_ADMIN;
import static ftn.kts.transport.constants.UserConstants.DB_USER_CLIENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.dtos.RouteScheduleDTO;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.RouteSchedule;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RouteScheduleController {

	@Autowired
	private TestRestTemplate restTemplate;
	
	private RouteScheduleDTO dto = new RouteScheduleDTO();
	
	
	@Before
	public void setup() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
		Set<Date> weekday = new HashSet<Date>();
		Set<Date> saturday = new HashSet<Date>();
		Set<Date> sunday = new HashSet<Date>();
		Date activeFrom, weekday1, weekday2, weekday3, saturday1, sunday1;
		try {
			activeFrom = formatter.parse("01.12.2018. 05:00");
			weekday1 = formatter2.parse("12:00");
			weekday2 = formatter2.parse("13:00");
			weekday3 = formatter2.parse("14:00");
			saturday1 = formatter2.parse("15:00");
			sunday1 = formatter2.parse("16:00");
			weekday.add(weekday3);
			weekday.add(weekday2);
			weekday.add(weekday1);
			saturday.add(saturday1);
			sunday.add(sunday1);
			dto.setActiveFrom(activeFrom);
			dto.setWeekday(weekday);
			dto.setSaturday(saturday);
			dto.setSunday(sunday);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void getScheduleForLine_PASS_Test() {
		ResponseEntity<List> responseEntity = 
				restTemplate.getForEntity("/schedule/line/1/", List.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void getSchedulesForLine_LineNotFound_Test() {
		
			ResponseEntity<RouteSchedule> responseEntity = 
					restTemplate.getForEntity("/schedule/line/-1/", RouteSchedule.class);
			
			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void addSchedule_PASS_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<RouteScheduleDTO> entity = new HttpEntity<RouteScheduleDTO>(dto, headers);
		
		
		ResponseEntity<RouteSchedule> response = restTemplate.exchange("/rest/schedule/line/1", HttpMethod.POST, entity, RouteSchedule.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody().getWeekday());
		assertNotNull(response.getBody().getSunday());
		assertNotNull(response.getBody().getSaturday());
		assertNotNull(response.getBody().getactiveFrom());
		assertEquals(dto.getWeekday().size(), response.getBody().getWeekday().size());
		assertEquals(dto.getSunday().size(), response.getBody().getSaturday().size());
		assertEquals(dto.getSaturday().size(), response.getBody().getSunday().size());
	}
	
	@Test
	public void addSchedule_LineNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<RouteScheduleDTO> entity = new HttpEntity<RouteScheduleDTO>(dto, headers);
		
		
		ResponseEntity<RouteSchedule> response = restTemplate.exchange("/rest/schedule/line/-1", HttpMethod.POST, entity, RouteSchedule.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void addSchedule_Unauthorized_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<RouteScheduleDTO> entity = new HttpEntity<RouteScheduleDTO>(dto, headers);
		
		
		ResponseEntity<RouteSchedule> response = restTemplate.exchange("/rest/schedule/line/1", HttpMethod.POST, entity, RouteSchedule.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

	}
	
	@Test
	public void addSchedule_MissingToken_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		
		HttpEntity<RouteScheduleDTO> entity = new HttpEntity<RouteScheduleDTO>(dto, headers);
		
		
		ResponseEntity<RouteSchedule> response = restTemplate.exchange("/rest/schedule/line/1", HttpMethod.POST, entity, RouteSchedule.class);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

	}
	
	@Test
	public void addSchedule_InvalidToken_Test() {
		String invalidToken = "invalid token";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + invalidToken);
		
		HttpEntity<RouteScheduleDTO> entity = new HttpEntity<RouteScheduleDTO>(dto, headers);
		
		
		ResponseEntity<RouteSchedule> response = restTemplate.exchange("/rest/schedule/line/1", HttpMethod.POST, entity, RouteSchedule.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void x1deleteSchedule_PASS_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);
		
		
		ResponseEntity<Boolean> response = restTemplate.exchange("/rest/schedule/1", HttpMethod.DELETE, entity, Boolean.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	
	@Test
	public void updateSchedule_PASS_Test() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
		Set<Date> weekday = new HashSet<Date>();
		Set<Date> saturday = new HashSet<Date>();
		Set<Date> sunday = new HashSet<Date>();
		Date activeFrom, weekday1, weekday2, weekday3, saturday1, sunday1;
		try {
			activeFrom = formatter.parse("02.02.2019. 06:00");
			weekday1 = formatter2.parse("09:00");
			weekday2 = formatter2.parse("08:00");
			weekday3 = formatter2.parse("15:00");
			saturday1 = formatter2.parse("16:00");
			sunday1 = formatter2.parse("19:00");
			weekday.add(weekday3);
			weekday.add(weekday2);
			weekday.add(weekday1);
			saturday.add(saturday1);
			sunday.add(sunday1);
			dto.setActiveFrom(activeFrom);
			dto.setWeekday(weekday);
			dto.setSaturday(saturday);
			dto.setSunday(sunday);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_ADMIN);
		
		HttpEntity<RouteScheduleDTO> entity = new HttpEntity<RouteScheduleDTO>(dto, headers);
		
		
		ResponseEntity<RouteSchedule> response = restTemplate.exchange("/rest/line/1/schedule/1/update", HttpMethod.PUT, entity, RouteSchedule.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody().getWeekday());
		
	}
	
	@Test
	public void updateSchedule_InvalidToken_Test() {
		String invalidToken = "invalid Token";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + invalidToken);
		
		HttpEntity<RouteScheduleDTO> entity = new HttpEntity<RouteScheduleDTO>(dto, headers);
		
		
		ResponseEntity<RouteSchedule> response = restTemplate.exchange("/rest/line/1/schedule/1/update", HttpMethod.PUT, entity, RouteSchedule.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}
	
	@Test
	public void updateSchedule_MissingToken_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		
		HttpEntity<RouteScheduleDTO> entity = new HttpEntity<RouteScheduleDTO>(dto, headers);
		
		
		ResponseEntity<RouteSchedule> response = restTemplate.exchange("/rest/line/1/schedule/1/update", HttpMethod.PUT, entity, RouteSchedule.class);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

	}
	
	@Test
	public void updateSchedule_Unauthorized_Test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestMeplate");
		headers.add("Authorization", "Bearer " + DB_USER_CLIENT);
		
		HttpEntity<RouteScheduleDTO> entity = new HttpEntity<RouteScheduleDTO>(dto, headers);
		
		
		ResponseEntity<RouteSchedule> response = restTemplate.exchange("/rest/line/1/schedule/1/update", HttpMethod.PUT, entity, RouteSchedule.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

	}
	

}
