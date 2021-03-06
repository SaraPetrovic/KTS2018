package ftn.kts.transport.services;

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Route;
import ftn.kts.transport.model.Vehicle;
import ftn.kts.transport.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public void addRoute(Route route){
        this.routeRepository.save(route);
    }

    @Override
    public List<Route> getRoutes(){
        return this.routeRepository.findAll();
    }

    @Override
    public Route getRoute(Long id){
        return this.routeRepository.findById(id).orElseThrow(() -> new DAOException("Route {id=" + id + "} can not be found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Route getRouteByVehicle(Vehicle vehicle){
        return this.routeRepository.findByVehicleAndActive(vehicle, true);
    }

	@Override
	public Set<Route> findByLine(Line l) {
		return this.routeRepository.findByLine(l);
	}

}
