package ftn.kts.transport.controllers;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.DTOconverter.DTOConverter;
import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.services.LineService;
import ftn.kts.transport.services.ZoneService;

@RestController
@RequestMapping(value = "/line")
public class LineController {

	@Autowired
	private LineService lineService;
	@Autowired
	private DTOConverter dtoConverter;
	@Autowired
	private ZoneService zoneService;

	
	
	@GetMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<Line>> getLines(){
		return ResponseEntity.status(HttpStatus.OK).body(this.lineService.getAllLines());
	}

	@GetMapping(value = "/{type}")
	@Produces("application/json")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<Line>> getLinesByType(@PathVariable("type") String type){
		VehicleType vt;
		try{
			vt = VehicleType.valueOf(type.toUpperCase());
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		return ResponseEntity.ok(this.lineService.getAllLinesByType(vt));
	}
	
	@GetMapping(path = "/zone/{id}/{type}")
	@Produces("application/json")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Set<Line>> getLinesByZoneAndTransportType(@PathVariable("id") Long id, @PathVariable("type") String type) {
		VehicleType v;
		try {
			v = VehicleType.valueOf(type.toUpperCase());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
		Zone z = zoneService.findById(id);
		
		return ResponseEntity.ok(this.lineService.getAllLinesByZoneAndTransportType(z, v));
		
		
	}


	@PostMapping
	@Consumes("application/json")
	@Produces("application/json")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Line> addLine(@RequestBody LineDTO lineDTO) {
		Line ret = null;
		Line l = dtoConverter.convertDTOtoLine(lineDTO);
		ret = lineService.addLineMethod(l, lineDTO);
		
		return new ResponseEntity<Line>(ret, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Line> updateLine(@RequestBody LineDTO updatedLine, @PathVariable("id") long id) {
		Line ret = lineService.updateLine(updatedLine, id);
		return new ResponseEntity<Line>(ret, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	@Produces("application/json")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Line> deleteLine(@PathVariable("id") long id) {
		Line ret = lineService.deleteLine(id);
		return new ResponseEntity<Line>(ret, HttpStatus.OK);
	}


	@PutMapping(path = "/{id}/updateStations")
	@Consumes("application/json")
	@Produces("application/json")
	public ResponseEntity<Line> updateStations(@PathVariable("id") long id, @RequestBody LineDTO newStations) {
		Line ret = lineService.updateLineStations(id, newStations);
		return new ResponseEntity<Line>(ret, HttpStatus.OK);
	}
	

	
}
