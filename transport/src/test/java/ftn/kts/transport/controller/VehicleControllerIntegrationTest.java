package ftn.kts.transport.controller;


import ftn.kts.transport.dtos.VehicleDTO;
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
public class VehicleControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    HttpHeaders headers = new HttpHeaders();


    @Test
    public void getAllTest_OK() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImp0aSI6ImFkbWluIiwicm9sZSI6IlJPTEVfQURNSU4ifQ.6PHB8kL0X0cg6b8W0XXwYOUg6MfJvxBjqGAXvBlu75mSdmCezOFv_dx-BOOHi0Hz4zB36GHtq-9RwRPUuLUKew");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle"),
                HttpMethod.GET, entity, String.class);

        String expected = "[{\"name\":\"vozilo broj 1\"},{\"name\":\"vozilo broj 2\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void getAllTest_InvalidAuthorization() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImp0aSI6IjEyMzQiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.tG95HDEtuVXC70WwAwcVX53tYFBaovEMzgs2p02lWrJgK_3V5mCIPVbL2hEpCQ3xW2qhI80UkZKIhqWpbxncfw");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle"),
                HttpMethod.GET, entity, String.class);

        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }


    @Test
    public void getAllTest_NoAuthorization() throws Exception{
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle"),
                HttpMethod.GET, entity, String.class);

        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void addVehicleTest_OK() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImp0aSI6ImFkbWluIiwicm9sZSI6IlJPTEVfQURNSU4ifQ.6PHB8kL0X0cg6b8W0XXwYOUg6MfJvxBjqGAXvBlu75mSdmCezOFv_dx-BOOHi0Hz4zB36GHtq-9RwRPUuLUKew");
        VehicleDTO v = new VehicleDTO();
        v.setName("Test Bus");
        HttpEntity<VehicleDTO> entity = new HttpEntity<>(v, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle"),
                HttpMethod.POST, entity, String.class);
        System.out.println(response);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        String expected = "{\"vehicleName\":\"Test Bus\",\"active\":false,\"free\":false,\"freeFrom\":null}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }


    @Test
    public void addVehicleTest_VehicleWithNameAlreadyExist() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImp0aSI6ImFkbWluIiwicm9sZSI6IlJPTEVfQURNSU4ifQ.6PHB8kL0X0cg6b8W0XXwYOUg6MfJvxBjqGAXvBlu75mSdmCezOFv_dx-BOOHi0Hz4zB36GHtq-9RwRPUuLUKew");
        VehicleDTO v = new VehicleDTO();
        v.setName("vozilo broj 1");
        HttpEntity<VehicleDTO> entity = new HttpEntity<>(v, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle"),
                HttpMethod.POST, entity, String.class);
        System.out.println(response);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String expected = "{\"errorMessage\":\"Vehicle with that name already exist\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void addVehicleTest_InvalidAuthorization() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImp0aSI6IjEyMzQiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.tG95HDEtuVXC70WwAwcVX53tYFBaovEMzgs2p02lWrJgK_3V5mCIPVbL2hEpCQ3xW2qhI80UkZKIhqWpbxncfw");
        VehicleDTO v = new VehicleDTO();
        v.setName("Test Bus");
        HttpEntity<VehicleDTO> entity = new HttpEntity<>(v, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle"),
                HttpMethod.POST, entity, String.class);


        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void addVehicleTest_NoAuthorization() throws Exception{
        VehicleDTO v = new VehicleDTO();
        v.setName("Test Bus");
        HttpEntity<VehicleDTO> entity = new HttpEntity<>(v, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle"),
                HttpMethod.POST, entity, String.class);


        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    public void deleteVehicleTest_OK() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImp0aSI6ImFkbWluIiwicm9sZSI6IlJPTEVfQURNSU4ifQ.6PHB8kL0X0cg6b8W0XXwYOUg6MfJvxBjqGAXvBlu75mSdmCezOFv_dx-BOOHi0Hz4zB36GHtq-9RwRPUuLUKew");
        VehicleDTO v = new VehicleDTO();
        v.setName("Test Bus");
        HttpEntity<VehicleDTO> entity = new HttpEntity<>(v, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle/1"),
                HttpMethod.DELETE, entity, String.class);
        System.out.println(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assert.assertEquals("true", response.getBody());
    }

    @Test
    public void deleteVehicleTest_VehicleDoesNotExist() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImp0aSI6ImFkbWluIiwicm9sZSI6IlJPTEVfQURNSU4ifQ.6PHB8kL0X0cg6b8W0XXwYOUg6MfJvxBjqGAXvBlu75mSdmCezOFv_dx-BOOHi0Hz4zB36GHtq-9RwRPUuLUKew");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle/3"),
                HttpMethod.DELETE, entity, String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        String expected = "{\"errorMessage\":\"Vehicle [id=3] cannot be found!\"}";
        Assert.assertEquals(expected, response.getBody());
    }

    @Test
    public void deleteVehicleTest_InvalidAuthorization() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImp0aSI6IjEyMzQiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.tG95HDEtuVXC70WwAwcVX53tYFBaovEMzgs2p02lWrJgK_3V5mCIPVbL2hEpCQ3xW2qhI80UkZKIhqWpbxncfw");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle/3"),
                HttpMethod.DELETE, entity, String.class);


        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }


    @Test
    public void deleteVehicleTest_NoAuthorization() throws Exception{
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle/3"),
                HttpMethod.DELETE, entity, String.class);


        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void updateVehicleTest_OK() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImp0aSI6ImFkbWluIiwicm9sZSI6IlJPTEVfQURNSU4ifQ.6PHB8kL0X0cg6b8W0XXwYOUg6MfJvxBjqGAXvBlu75mSdmCezOFv_dx-BOOHi0Hz4zB36GHtq-9RwRPUuLUKew");
        VehicleDTO v = new VehicleDTO();
        v.setName("Test Bus");
        HttpEntity<VehicleDTO> entity = new HttpEntity<>(v, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle/1"),
                HttpMethod.PUT, entity, String.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String expected = "{\"id\":1,\"vehicleName\":\"Test Bus\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void updateVehicleTest_VehicleForUpdateNotFound() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImp0aSI6ImFkbWluIiwicm9sZSI6IlJPTEVfQURNSU4ifQ.6PHB8kL0X0cg6b8W0XXwYOUg6MfJvxBjqGAXvBlu75mSdmCezOFv_dx-BOOHi0Hz4zB36GHtq-9RwRPUuLUKew");
        VehicleDTO v = new VehicleDTO();
        v.setName("Test Bus");
        HttpEntity<VehicleDTO> entity = new HttpEntity<>(v, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle/3"),
                HttpMethod.PUT, entity, String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        String expected = "{\"errorMessage\":\"Vehicle with that id does not exist\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void updateVehicleTest_InvalidAuthentication() throws Exception{
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImp0aSI6IjEyMzQiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.tG95HDEtuVXC70WwAwcVX53tYFBaovEMzgs2p02lWrJgK_3V5mCIPVbL2hEpCQ3xW2qhI80UkZKIhqWpbxncfw");
        VehicleDTO v = new VehicleDTO();
        v.setName("Test Bus");
        HttpEntity<VehicleDTO> entity = new HttpEntity<>(v, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle/1"),
                HttpMethod.PUT, entity, String.class);


        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void updateVehicleTest_NoAuthorization() throws Exception{
        VehicleDTO v = new VehicleDTO();
        v.setName("Test Bus");
        HttpEntity<VehicleDTO> entity = new HttpEntity<>(v, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/rest/vehicle/1"),
                HttpMethod.PUT, entity, String.class);


        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }



    private String createURLWithPort(String url){
        return "http://localhost:" + port + url;
    }
}
