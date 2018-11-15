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
import ftn.kts.transport.dtos.ZoneDTO;
import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.services.StationService;
import ftn.kts.transport.services.ZoneService;

@RestController
@RequestMapping(value = "/zone")
public class ZoneController {
	
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private StationService stationService;
	
	@GetMapping("/all")
	@Produces("application/json")
	public ResponseEntity<List<ZoneDTO>> getAllZones() {
		List<Zone> zones = zoneService.findAll();
		List<ZoneDTO> dtoZones = new ArrayList<ZoneDTO>();
		for(Zone z : zones) {
			dtoZones.add(new ZoneDTO(z));
		}
		return new ResponseEntity<>(dtoZones, HttpStatus.OK);
	}
	
	@PostMapping(path="/add")
	@Consumes("application/json")
	public ResponseEntity<Void> addZone(@RequestBody ZoneDTO zoneDTO) {
		
		try{
			zoneService.save(new Zone(zoneDTO.getName(), new HashSet<Station>(), true));
		
		}catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@DeleteMapping(path="/delete/{id}")
	public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
		
		Zone z = zoneService.findById(id);
		
		if(z == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			zoneService.deleteZone(id);	
		}
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	//id zone
	@PostMapping(path="/addStations/{id}")
	@Consumes("applications/json")
	@Produces("applications/json")
	public ResponseEntity<Void> addStationsInZone(@PathVariable Long id, @RequestBody List<StationDTO> dtoStations){
		
		try {
			List<Station> stations = new ArrayList<Station>();
			//dtoStations -> stations
			for(StationDTO dtoStation : dtoStations) {
				stations.add(stationService.fromDtoToStation(dtoStation));
			}
			
			zoneService.addStations(zoneService.findById(id), stations);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
			
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
