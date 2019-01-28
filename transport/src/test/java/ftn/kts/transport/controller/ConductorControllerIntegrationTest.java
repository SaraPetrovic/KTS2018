package ftn.kts.transport.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ConductorControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    HttpHeaders headers = new HttpHeaders();

    @Test
    public void checkInTest_OK() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb25kdWN0b3IiLCJqdGkiOiIxMjMiLCJyb2xlIjoiUk9MRV9DT05EVUNURVIifQ.R96YTPfdom-vD-jMOnuS9d8QW4wIqKpOG4JKqSp2-3xeQLUqo-4Nly8NBtqp9Vxy-iEyyiH2oLChEyVps0BVWg");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/conductor/checkin/1"),
                HttpMethod.GET, entity, String.class);
        System.out.println(response);
        String expected = "{\"id\":1,\"line\":{\"id\":1,\"name\":\"7A\",\"transportType\":\"BUS\",\"stationSet\":[{\"lineAndStationPK\":{\"lineId\":1,\"stationId\":7},\"stationOrder\":5},{\"lineAndStationPK\":{\"lineId\":1,\"stationId\":1},\"stationOrder\":1},{\"lineAndStationPK\":{\"lineId\":1,\"stationId\":2},\"stationOrder\":2},{\"lineAndStationPK\":{\"lineId\":1,\"stationId\":5},\"stationOrder\":4},{\"lineAndStationPK\":{\"lineId\":1,\"stationId\":3},\"stationOrder\":3}],\"streetPath\":[\"Kisacka 2\",\"Kisacka 1\",\"Fruskogorska 3\",\"Zarka Zrenjanina 1\",\"Jovana Subotica 3\",\"Jovana Subotica 1\",\"Jovana Subotica 2\",\"Bulevar Cara Lazara 5\",\"Bulevar Cara Lazara 6\",\"Strazilovska 2\",\"Bulevar Mihajla Pupina 1\",\"Strazilovska 1\",\"Sentandrejski Put 2\",\"Fruskogorska 2\",\"Sentandrejski Put 1\",\"Fruskogorska 1\",\"Kisacka 4\",\"Kisacka 3\"],\"active\":true,\"description\":null,\"duration\":1000},\"date\":\"2019-01-31T23:00:00.000+0000\",\"vehicle\":{\"id\":1,\"vehicleType\":\"BUS\",\"vehicleName\":\"vozilo broj 1\",\"active\":true,\"free\":true,\"freeFrom\":\"00:00:00\"},\"active\":true}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }


    @Test
    public void checkInTest_RouteNotFound() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb25kdWN0b3IiLCJqdGkiOiIxMjMiLCJyb2xlIjoiUk9MRV9DT05EVUNURVIifQ.R96YTPfdom-vD-jMOnuS9d8QW4wIqKpOG4JKqSp2-3xeQLUqo-4Nly8NBtqp9Vxy-iEyyiH2oLChEyVps0BVWg");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/conductor/checkin/2"),
                HttpMethod.GET, entity, String.class);
        System.out.println(response);
        String expected = "{'errorMessage':'Route for that vehicle not found'}";
        //Assert.assertEquals(expected, response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void checkInTest_VehicleNotFound() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb25kdWN0b3IiLCJqdGkiOiIxMjMiLCJyb2xlIjoiUk9MRV9DT05EVUNURVIifQ.R96YTPfdom-vD-jMOnuS9d8QW4wIqKpOG4JKqSp2-3xeQLUqo-4Nly8NBtqp9Vxy-iEyyiH2oLChEyVps0BVWg");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/conductor/checkin/3"),
                HttpMethod.GET, entity, String.class);
        System.out.println(response);
        String expected = "{'errorMessage': 'Vehicle not found!'}";
        //Assert.assertEquals(expected, response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void checkInTest_InvalidAuthorization() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImp0aSI6IjEyMzQiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.tG95HDEtuVXC70WwAwcVX53tYFBaovEMzgs2p02lWrJgK_3V5mCIPVbL2hEpCQ3xW2qhI80UkZKIhqWpbxncfw");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/conductor/checkin/1"),
                HttpMethod.GET, entity, String.class);

        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void checkInTest_NoAuthorization() throws Exception{

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/conductor/checkin/1"),
                HttpMethod.GET, entity, String.class);

        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    private String createURLWithPort(String url){
        return "http://localhost:" + port + url;
    }
}
