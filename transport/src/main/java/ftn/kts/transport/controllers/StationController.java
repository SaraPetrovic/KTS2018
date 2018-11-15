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
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.services.StationService;

@RestController
@RequestMapping(value = "/station")
public class StationController {

	@Autowired
	private StationService stationService;
	
	@GetMapping(path="/all")
	@Produces("application/json")
	public ResponseEntity<List<StationDTO>> getAll(){
		List<StationDTO> dtoStations = new ArrayList<>();
		for(Station s : stationService.findAll()) {
			dtoStations.add(new StationDTO(s));
		}
		return new ResponseEntity<>(dtoStations, HttpStatus.OK);
	}
	
	@PostMapping(path="/add")
	@Consumes("application/json")
	public ResponseEntity<Void> addStation(@RequestBody StationDTO stationDTO) {
		
		try {
			if(stationDTO.getLines() == null) {
				stationService.save(new Station(stationDTO.getName(), stationDTO.getAddress(), new HashSet<Line>(), true));
			}else {
				stationService.save(new Station(stationDTO.getName(), stationDTO.getAddress(), new HashSet<Line>(stationDTO.getLines()), true));
			}
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(path="delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		
		if(stationService.findById(id) != null){
			stationService.delete(id);
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
