package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.model.Line;
import ftn.kts.transport.services.LineService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ExampleTest {

	@Autowired
	private LineService service;
	
	@Test
	public void someTest() {
		Line l = service.findById(1L);
		System.out.println(l.getName());
		assertEquals("7A", l.getName());
	}
}
