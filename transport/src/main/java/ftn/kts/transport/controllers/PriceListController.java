package ftn.kts.transport.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.DTOconverter.DTOConverter;
import ftn.kts.transport.dtos.PriceListDTO;
import ftn.kts.transport.model.PriceList;
import ftn.kts.transport.services.PriceListService;

@RestController
@RequestMapping(value = "/priceList")
public class PriceListController {

	@Autowired
	private PriceListService priceListService;
	@Autowired
	private DTOConverter dtoConverter;
	
	
	@PostMapping
	@Produces("application/json")
	@Consumes("application/json")
	public ResponseEntity<PriceList> addNewPriceList(@RequestBody PriceListDTO priceListDTO) {
		PriceList newPriceList = dtoConverter.convertDTOtoPriceList(priceListDTO);
		PriceList ret = priceListService.addPriceList(newPriceList);
		return new ResponseEntity<PriceList>(ret, HttpStatus.CREATED);
	}

	@PostMapping(path = "/{id}/activate")
	public ResponseEntity<Boolean> activatePriceList(@PathVariable("id") Long id) {
		boolean ret = priceListService.activatePriceList(id);
		return new ResponseEntity<Boolean>(ret, HttpStatus.OK);
	}
}
