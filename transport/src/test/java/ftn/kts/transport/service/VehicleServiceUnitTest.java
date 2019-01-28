package ftn.kts.transport.service;


import ftn.kts.transport.dtos.VehicleDTO;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Vehicle;
import ftn.kts.transport.repositories.VehicleRepository;
import ftn.kts.transport.services.VehicleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class VehicleServiceUnitTest {

    private Vehicle v;

    @Autowired
    private VehicleService vehicleService;

    @MockBean
    private VehicleRepository vehicleRepositoryMocked;

    @Before
    public void setUp(){
        this.v = new Vehicle();
        this.v.setId(1L);
        this.v.setActive(true);
        this.v.setVehicleType(VehicleType.BUS);
        this.v.setVehicleName("Bus 1");

        Calendar c1 = Calendar.getInstance();
        c1.set(2019,2,1,11,0,0);
        this.v.setFreeFrom(c1.getTime());
        this.v.setFree(false);

        Mockito.when(vehicleRepositoryMocked.findById(1L)).thenReturn(Optional.of(v));
        Mockito.when(vehicleRepositoryMocked.findById(2L)).thenReturn(Optional.empty());

        Mockito.when(vehicleRepositoryMocked.findByVehicleName("Bus 2")).thenReturn(null);
        Mockito.when(vehicleRepositoryMocked.findByVehicleName("Bus 1")).thenReturn(v);

        Mockito.when(vehicleRepositoryMocked.getOne(1L)).thenReturn(v);
        Mockito.when(vehicleRepositoryMocked.getOne(2L)).thenReturn(null);

        List<Vehicle> list = new ArrayList<>();

        list.add(this.v);

        Mockito.when(vehicleRepositoryMocked.findAll()).thenReturn(list);

    }

    @Test
    public void addVehicleTest_VehicleNameAlreadyExist(){
        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setName("Bus 1");
        Vehicle v = this.vehicleService.addVehicle(vehicle);
        Assert.assertNull(v);
    }


    @Test
    public void addVehicleTest_OK(){
        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setName("Bus 2");

        Vehicle v = this.vehicleService.addVehicle(vehicle);
        Assert.assertNotNull(v);
        Assert.assertEquals("Bus 2", v.getVehicleName());
    }


    @Test
    public void updateVehicleTest_OK(){
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setName("Bus 3");
        Vehicle v = this.vehicleService.updateVehicle(vehicleDTO, 1L);
        Assert.assertEquals("Bus 3", v.getVehicleName());
        Mockito.verify(vehicleRepositoryMocked, Mockito.times(1)).save(v);
    }

    @Test(expected = DAOException.class)
    public void updateVehicleTest_IdNotFound(){
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setName("Bus 3");
        Vehicle v = this.vehicleService.updateVehicle(vehicleDTO, 2L);
        //Assert.assertNull(v);
        //Mockito.verify(vehicleRepositoryMocked, Mockito.times(0)).save(v);
    }


    @Test
    public void deleteVehicleTest_OK(){
        boolean res = this.vehicleService.deleteVehicle(1L);
        Assert.assertTrue(res);
        Mockito.verify(vehicleRepositoryMocked, Mockito.times(1)).save(this.v);
    }

    @Test(expected = DAOException.class)
    public void deleteVehicleTest_VehicleNotFound(){
        this.vehicleService.deleteVehicle(2l);
    }

    @Test
    public void getFreeVehicle_OK_VehicleReturned(){
        Calendar c = Calendar.getInstance();
        c.set(2019, 2, 1, 12, 0, 0);
        Vehicle v = this.vehicleService.getFreeVehicle(c.getTime(), 1000);
        Assert.assertNotNull(v);
        Assert.assertEquals(this.v, v);
        Mockito.verify(vehicleRepositoryMocked, Mockito.times(1)).save(this.v);
    }

    @Test
    public void getFreeVehicle_OK_NullReturned(){
        Calendar c = Calendar.getInstance();
        c.set(2019, 2, 1, 10, 30, 0);
        Vehicle v = this.vehicleService.getFreeVehicle(c.getTime(), 1000);
        Assert.assertNull(v);
    }
}
