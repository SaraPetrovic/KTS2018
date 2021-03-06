package ftn.kts.transport.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.enums.UserTypeDemographic;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.model.LineTicket;
import ftn.kts.transport.model.PriceList;
import ftn.kts.transport.model.RouteTicket;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.ZoneTicket;
import ftn.kts.transport.repositories.PriceListRepository;

@Service
public class PriceListServiceImpl implements PriceListService {

	@Autowired
	private PriceListRepository priceListRepository;

	@Autowired
	private ZoneService zoneService;

	@Override
	public PriceList addPriceList(PriceList newPriceList) {
	
		return priceListRepository.save(newPriceList);
	}


	// OVDE BI TREBALO @TRANSACTIONAL, ne sme niko da pristupi
	// priceList repository dok se ne sacuvaju oba
	@Transactional
	@Override
	public boolean activatePriceList(Long id) {
		
		PriceList toActivate = priceListRepository.findById(id).orElseThrow(() -> 
									new DAOException("PriceList [id=" + id + "] cannot be found!"));
		
		Date dateActivated = new Date();
		
		
		
		
		// ako postoji aktivan cenovnik:
		// deaktiviraj ga, i postavi ovo vreme za END
		Optional<PriceList> activeOpt = priceListRepository.findByActive(true);
		if (activeOpt.isPresent()) {
			PriceList active = activeOpt.get();
			if (active.getId() == id) {
				throw new InvalidInputDataException("You cannot activate PriceList that is already active!");
			}
			active.setEndDateTime(dateActivated);
			active.setActive(false);
			priceListRepository.save(active);
		}
		
		// aktiviraj ga i postavi START
		toActivate.setActive(true);
		toActivate.setStartDateTime(dateActivated);
		
		priceListRepository.save(toActivate);
		return true;
	}


	@Override
	public double calculateTicketPrice(Ticket ticket) {
		PriceList activePriceList = getActivePriceList();
		if (activePriceList == null) {
			// mada se ovo nikad nece desiti ako napravim tamo Transactional!!!
			throw new DAOException("There is no active Price Lists!!! FATAL!!!", HttpStatus.NOT_FOUND);
		}
		
		UserTypeDemographic userTypeDemo = ticket.getUser().getUserTypeDemo();
		TicketTypeTemporal ticketType = ticket.getTicketTemporal();
		
		double lineDisc = activePriceList.getLineDiscount();
		double monthlyCoeff = activePriceList.getMonthlyCoeffitient();
		double seniorDisc = activePriceList.getSeniorDiscount();
		double studentDisc = activePriceList.getStudentDiscount();
		double yearlyCoeff = activePriceList.getYearlyCoeffitient();
		double oneHourCoeff = activePriceList.getOneHourCoeffitient();
		Map<Long, Double> oneTimePrices = activePriceList.getOneTimePrices();
		double finalPrice = 0;
		
		Long zoneId;
		
		if (ticket instanceof ZoneTicket) {
			zoneId = ((ZoneTicket) ticket).getZone().getId();
			finalPrice = oneTimePrices.get(zoneId);
			if (ticketType.ordinal() == 0) {
				finalPrice *= oneHourCoeff;
			} else if (ticketType.ordinal() == 1) {
				finalPrice *= monthlyCoeff;
			} else if (ticketType.ordinal() == 2) {
				finalPrice *= yearlyCoeff;
			}
		} else if (ticket instanceof LineTicket) {
			zoneId = zoneService.getZoneForLine(((LineTicket) ticket).getLine()).getId();
			finalPrice = oneTimePrices.get(zoneId);
			if (ticketType.ordinal() == 0) {
				// ovo ni ne moze - ne postoji ONE_HOUR za LINE, samo za zonu!!!
			} else if (ticketType.ordinal() == 1) {
				finalPrice *= monthlyCoeff * lineDisc;
			} else if (ticketType.ordinal() == 2) {
				finalPrice *= yearlyCoeff * lineDisc;
			}
		} else if(ticket instanceof RouteTicket){
			zoneId = zoneService.getZoneForLine(((RouteTicket) ticket).getRoute().getLine()).getId();
			finalPrice = oneTimePrices.get(zoneId);
		}
		
		if (userTypeDemo.ordinal() == 1) {
			finalPrice *= studentDisc;
		} else if (userTypeDemo.ordinal() == 2) {
			finalPrice *= seniorDisc;
		}
		
		return finalPrice;
	}


	@Override
	public PriceList getActivePriceList() {
		Optional<PriceList> activeOpt = priceListRepository.findByActive(true);
		if (activeOpt.isPresent()) {
			return activeOpt.get();
		}
		return null;
	}


	@Override
	public List<PriceList> findAll() {
		return this.priceListRepository.findAll();
	}

	
}
