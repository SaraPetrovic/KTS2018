package ftn.kts.transport.service;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Route;
import ftn.kts.transport.repositories.RouteRepository;
import ftn.kts.transport.services.RouteService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class RouteServiceUnitTest {

	@Autowired
	private RouteService routeService;
	
	@MockBean
    private RouteRepository routeRepository;

	@Test
	public void getRouteTestOK() {
		Route route = new Route(Long.valueOf(1), null, null);
		
		Mockito.when(routeRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(route));
		
		Route rez = routeService.getRoute(route.getId());
		
		assertNotNull(rez);
	}
	
	@Test(expected=DAOException.class)
	public void getRouteTestNotFound() {
		Route route = new Route(Long.valueOf(1), null, null);
		
		Mockito.when(routeRepository.findById(Long.valueOf(2))).thenReturn(null);
		
		routeService.getRoute(route.getId());
		
	}
}
