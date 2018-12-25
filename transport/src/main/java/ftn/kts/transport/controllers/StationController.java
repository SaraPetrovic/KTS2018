package ftn.kts.transport.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.dtos.StationDTO;
import ftn.kts.transport.exception.StationNotFoundException;
import ftn.kts.transport.model.LineAndStation;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.services.StationService;

@RestController
@RequestMapping(value = "/station")
public class StationController {

	@Autowired
	private StationService stationService;
	
	@GetMapping(path="/all")
	public ResponseEntity<List<StationDTO>> getAll(){
		List<StationDTO> dtoStations = new ArrayList<>();
		for(Station s : stationService.findAll()) {
			dtoStations.add(new StationDTO(s));
		}
		return new ResponseEntity<>(dtoStations, HttpStatus.OK);
	}
	
	@PostMapping(path="/add")
	@Consumes("application/json")
	public ResponseEntity<StationDTO> addStation(@RequestBody StationDTO stationDTO) {
		
		Station station = stationService.save(new Station(stationDTO.getAddress(), stationDTO.getName(), new HashSet<LineAndStation>(), true));
		return new ResponseEntity<>(new StationDTO(station), HttpStatus.CREATED);	
		
	}
	
	@DeleteMapping(path="delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		boolean rez = stationService.delete(id);
		if(!rez) {
			throw new StationNotFoundException(id);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}		
	
	@PostMapping(path="/update")
	@Consumes("applications/json")
	@Produces("applications/json")
	public ResponseEntity<StationDTO> updateZone(@RequestBody StationDTO dtoStation){
		
		Station station = stationService.findById(dtoStation.getId());
		if(station == null) {
			throw new StationNotFoundException(dtoStation.getId());
		}
		station.setAddress(dtoStation.getAddress());
		station.setName(dtoStation.getName());
		
		stationService.save(station);
		return new ResponseEntity<>(new StationDTO(station), HttpStatus.OK);
	}
}
