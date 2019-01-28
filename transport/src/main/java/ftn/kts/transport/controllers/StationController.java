package ftn.kts.transport.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.dtos.StationDTO;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.model.LineAndStation;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.services.StationService;

@RestController
public class StationController {

	@Autowired
	private StationService stationService;
	
	@GetMapping()
	@Produces("application/json")
	@CrossOrigin( origins = "http://localhost:4200")
	public ResponseEntity<List<StationDTO>> getAll(){
		List<StationDTO> dtoStations = new ArrayList<>();
		for(Station s : stationService.findAll()) {
			dtoStations.add(new StationDTO(s));
		}
		return new ResponseEntity<>(dtoStations, HttpStatus.OK);
	}
	
	@PostMapping("/rest/station")
	@PreAuthorize("hasRole('ADMIN')")
	@Produces("applications/json")
	@Consumes("applications/json")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<StationDTO> addStation(@RequestBody StationDTO stationDTO) {
		
		if(stationDTO.getAddress() == null || stationDTO.getName() == null || stationDTO.getLocation().getX() == 0 || stationDTO.getLocation().getY() == 0 ) {
			throw new InvalidInputDataException("You must entered required data", HttpStatus.BAD_REQUEST);
		}
		Station station = stationService.save(new Station(stationDTO.getAddress(), stationDTO.getName(), new HashSet<LineAndStation>(), true, stationDTO.getLocation().getX(), stationDTO.getLocation().getY()));
		return new ResponseEntity<>(new StationDTO(station), HttpStatus.OK);	
		
	}
	
	@DeleteMapping(path="/rest/station/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		stationService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}		
	
	@PutMapping(path="/rest/station/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Consumes("applications/json")
	@Produces("applications/json")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<StationDTO> updateStation(@PathVariable Long id, @RequestBody StationDTO dtoStation){
		
		Station station = stationService.findById(id);
	
		if(dtoStation.getAddress() == null || dtoStation.getAddress() == "" 
				|| dtoStation.getName() == "" || dtoStation.getName() == null) {
			throw new InvalidInputDataException("You must entered required data", HttpStatus.BAD_REQUEST);
		}
		
		station.setAddress(dtoStation.getAddress());
		station.setName(dtoStation.getName());
		
		stationService.save(station);
		return new ResponseEntity<>(new StationDTO(station), HttpStatus.OK);
	}
}
