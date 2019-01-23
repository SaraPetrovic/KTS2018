package ftn.kts.transport.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import ftn.kts.transport.dtos.ZoneDTO;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.exception.StationNotFoundException;
import ftn.kts.transport.exception.ZoneNotFoundException;
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
	
	@GetMapping("/{id}")
	//@PreAuthorize("hasRole('ADMIN')")
	@Produces("application/json")
	@CrossOrigin( origins = "http://localhost:4200")
	public ResponseEntity<ZoneDTO> getZone(@PathVariable Long id) {
		Zone zone = zoneService.findById(id);
		
		return new ResponseEntity<>(new ZoneDTO(zone), HttpStatus.OK);
	}
	
	@GetMapping()
	//@PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
	@Produces("application/json")
	@CrossOrigin( origins = "http://localhost:4200")
	public ResponseEntity<List<ZoneDTO>> getAllZones() {
		List<Zone> zones = zoneService.findAll();
		List<ZoneDTO> dtoZones = new ArrayList<ZoneDTO>();
		for(Zone z : zones) {
			ZoneDTO dto = new ZoneDTO(z);
			dtoZones.add(dto);
		}
		
		return new ResponseEntity<>(dtoZones, HttpStatus.OK);
	}
	
	@PostMapping()
	//@PreAuthorize("hasRole('ADMIN')")
	@Consumes("application/json")
	@CrossOrigin( origins = "http://localhost:4200")
	public ResponseEntity<ZoneDTO> addZone(@RequestBody ZoneDTO zoneDTO) {
		
		if(zoneDTO.getName() == null || zoneDTO.getName() == "") {
			throw new InvalidInputDataException("You must entered required data", HttpStatus.BAD_REQUEST);
		}
	
		Zone subZone = null;
		if(zoneDTO.getSubZoneId() != null && zoneDTO.getSubZoneId() != 0) {
			subZone = zoneService.findById(zoneDTO.getSubZoneId());
		}
		
		Set<Station> stations = new HashSet<Station>();
		
		Zone zone = zoneService.addZone(new Zone(zoneDTO.getName(), stations, subZone, true));
		
		return new ResponseEntity<>(new ZoneDTO(zone), HttpStatus.CREATED);	
	}
	
	@DeleteMapping("/{id}")
	//@PreAuthorize("hasRole('ADMIN')")
	@CrossOrigin( origins = "http://localhost:4200")
	public ResponseEntity<Boolean> deleteZone(@PathVariable Long id) {
		zoneService.deleteZone(id);	
		
		return new ResponseEntity<>(true, HttpStatus.OK);	
	}
	
	@PutMapping("/{id}")
	//@PreAuthorize("hasRole('ADMIN')")
	@Consumes("applications/json")
	@Produces("applications/json")
	public ResponseEntity<ZoneDTO> updateZone(@PathVariable Long id, @RequestBody ZoneDTO dtoZone){
		
//		if(dtoZone.getSubZoneId() == null || dtoZone.getStations().size() == 0) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
		
		Zone zone = zoneService.findById(id);
		
		if(dtoZone.getName() == null || dtoZone.getName() == "") {
			throw new InvalidInputDataException("You must entered required data", HttpStatus.BAD_REQUEST);
		}
		
		zone.setName(dtoZone.getName());
		
//		Zone subZone = zoneService.findById(dtoZone.getSubZoneId());
//		zone.setSubZone(subZone);
		
		zoneService.save(zone);
		return new ResponseEntity<>(new ZoneDTO(zone), HttpStatus.OK);
	}
	
	public Set<Station> checkStations(Set<StationDTO> dtoStations){
		Set<Station> stations = new HashSet<Station>();
		
		for(StationDTO dtoStation : dtoStations) {
			Station station = stationService.fromDtoToStation(dtoStation);
			stations.add(station);
		}
		return stations;
		
	}
	
}
