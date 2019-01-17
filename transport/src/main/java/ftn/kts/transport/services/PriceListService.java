package ftn.kts.transport.services;


import ftn.kts.transport.model.PriceList;
import ftn.kts.transport.model.Ticket;

public interface PriceListService {

	PriceList addPriceList(PriceList newPriceList);
	boolean activatePriceList(Long id);
	double calculateTicketPrice(Ticket ticket);
	PriceList getActivePriceList();
}
