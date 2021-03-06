package ftn.kts.transport.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Route;
import ftn.kts.transport.model.RouteSchedule;
import ftn.kts.transport.model.Vehicle;
import ftn.kts.transport.services.LineService;
import ftn.kts.transport.services.RouteScheduleService;
import ftn.kts.transport.services.RouteService;
import ftn.kts.transport.services.VehicleService;

@RestController
@RequestMapping(value = "/")
public class RouteController {


    @Autowired
    private RouteService routeService;

    @Autowired
    private LineService lineService;

    @Autowired
    private VehicleService vehicleService;
    
    @Autowired
    private RouteScheduleService routeScheduleService;


    @GetMapping(path = "route/line/{id}")
    @Produces("application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Set<Route>> getRoutesByLine(@PathVariable("id") Long id) {
    	Line found = this.lineService.findById(id);
    	return ResponseEntity.ok(this.routeService.findByLine(found));
    }
    
    public Date getDate(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
        calendar.set(Calendar.MINUTE, date.getMinutes());
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    @Scheduled(cron = "00 00 2 * * *")
    public void generateRoutes(){
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        List<Line> lines = this.lineService.getAllLines();

        RouteSchedule rs;

        for(Line line : lines) {
            List<RouteSchedule> routeScheduleList = this.routeScheduleService.getScheduleByLine(line.getId());
            if(routeScheduleList.size() == 0)
                break;
            rs = routeScheduleList.get(0);
            if (dayOfWeek == 6) {

                for(Date date : sortDates(rs.getSunday())){
                    Vehicle v = this.vehicleService.getFreeVehicle(date, line.getDuration());
                    if(v != null)
                        this.routeService.addRoute(new Route(line, getDate(date), v));
                }
            }else if(dayOfWeek == 5){
                for(Date date : sortDates(rs.getSaturday())){
                    Vehicle v = this.vehicleService.getFreeVehicle(date, line.getDuration());
                    if(v != null)
                        this.routeService.addRoute(new Route(line, getDate(date), v));
                }
            }else{
                for(Date date : sortDates(rs.getWeekday())){
                    Vehicle v = this.vehicleService.getFreeVehicle(date, line.getDuration());
                    if(v != null)
                        this.routeService.addRoute(new Route(line, getDate(date), v));
                }
            }
        }
    }

    public List<Date> sortDates(Set<Date> set){
        List<Date> dates = new ArrayList<>(set);

        Collections.sort(dates, (Date o1, Date o2) -> {
                return o1.compareTo(o2);
            }
        );

        return dates;
    }

}
