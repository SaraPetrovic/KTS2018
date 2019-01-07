package ftn.kts.transport.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.transport.DTOconverter.DTOConverter;
import ftn.kts.transport.dtos.PriceListDTO;
import ftn.kts.transport.model.PriceList;
import ftn.kts.transport.services.PriceListService;

@RestController
public class PriceListController {

	@Autowired
	private PriceListService priceListService;
	
	@RequestMapping(
			value = "/priceList/add",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<PriceList> addNewPriceList(@RequestBody PriceListDTO priceListDTO) {
		PriceList newPriceList = DTOConverter.convertDTOtoPriceList(priceListDTO);
		PriceList ret = priceListService.addPriceList(newPriceList);
		return new ResponseEntity<PriceList>(ret, HttpStatus.CREATED);
	}

	@RequestMapping(
			value = "/priceList/{id}/activate",
			method = RequestMethod.POST
			)
	public ResponseEntity<Boolean> activatePriceList(@PathVariable("id") Long id) {
		boolean ret = priceListService.activatePriceList(id);
		return new ResponseEntity<Boolean>(ret, HttpStatus.OK);
	}
}
