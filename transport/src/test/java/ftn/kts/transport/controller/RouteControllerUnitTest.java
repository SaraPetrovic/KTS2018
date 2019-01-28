package ftn.kts.transport.controller;

import ftn.kts.transport.controllers.RouteController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class RouteControllerUnitTest {

    @Autowired
    private RouteController routeController;

    @Test
    public void getDateTest_OK(){
        Date now = new Date();
        Date d = this.routeController.getDate(now);

        Assert.assertEquals(d.getHours(), now.getHours());
        Assert.assertEquals(d.getMinutes(), now.getMinutes());
        Assert.assertEquals(d.getSeconds(),0);
    }

    @Test
    public void sortDates_OK(){
        Set<Date> set = new HashSet<>();
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();
        set.add(date2);
        set.add(date3);
        set.add(date1);

        List<Date> list = this.routeController.sortDates(set);

        for(int i = 0; i < list.size() - 1; i++){
            Assert.assertTrue(list.get(i).before(list.get(i+1)));
        }
    }
}
