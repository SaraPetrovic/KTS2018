package ftn.kts.transport.DTOconverter;

import java.util.Calendar;
import java.util.Date;

import ftn.kts.transport.model.*;
import ftn.kts.transport.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.dtos.PriceListDTO;
import ftn.kts.transport.dtos.RouteScheduleDTO;
import ftn.kts.transport.dtos.TicketDTO;
import ftn.kts.transport.enums.TicketActivationType;
import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.services.LineService;
import ftn.kts.transport.services.ZoneService;

@Service
public class DTOConverterImpl implements DTOConverter{
	
	@Autowired
	private LineService lineService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
    private RouteService routeService;

	@Override
	public Line convertDTOtoLine(LineDTO lineDTO) {
		// ========== CHECK DATA ==========
		Line found = null;
		try {
			found = lineService.findByName(lineDTO.getName());	// throws DAO
		} catch (DAOException e) {
			found = null;
		}
		if (found != null) {
			throw new DAOException("Line [name=" + lineDTO.getName() +"] already exists!", HttpStatus.BAD_REQUEST);
		}
		int vehicle = lineDTO.getVehicleType();
		if (vehicle != 0 && vehicle != 1 && vehicle != 2) {
			throw new InvalidInputDataException("VehicleType = {BUS(0), TRAM(1), SUBWAY(2)} - bad request!");
		}
		if (lineDTO.getDuration() < 0) {
			throw new InvalidInputDataException("Duraion has to be above 0!");
		}
		
		// =========== CONVERT =============
		Line l = new Line();
		l.setName(lineDTO.getName());
		l.setTransportType(VehicleType.values()[lineDTO.getVehicleType()]);
		l.setStreetPath(lineDTO.getStreetPath());
		l.setDuration(lineDTO.getDuration());
		return l;
	}
	
	@Override
	public RouteSchedule convertDTOtoRouteSchedule(RouteScheduleDTO scheduleDTO) {
		RouteSchedule schedule = new RouteSchedule();
		schedule.setActive(true);
		schedule.setactiveFrom(scheduleDTO.getActiveFrom());
		schedule.setSaturday(scheduleDTO.getSaturday());
		schedule.setSunday(scheduleDTO.getSunday());
		schedule.setWeekday(scheduleDTO.getWeekday());
		return schedule;
	}
	
	@Override
	public Ticket convertDTOtoTicket(TicketDTO ticketDTO) {
		
		// ========= CHECK DATA =============
		
		int ticketTemporal = ticketDTO.getTicketTemporal().ordinal();
		//int transportType = ticketDTO.getTransportType().ordinal();
		
		// OVE PROVERE VISE NEMAJU SMISLA JER DTO PRIMA BAS ENUM ?? nzm kako to onda
		//if (ticketTemporal != 0 && ticketTemporal != 1 && ticketTemporal != 2 && ticketTemporal != 3) {
		//	throw new InvalidInputDataException("TicketTemporal = {ONE_HOUR_PASS (0), MONTHLY_PASS (1), YEARLY_PASS (2), ONE_TIME_PASS (3)} - bad request!");
		//}
		
//		if (transportType != 0 && transportType != 1 && transportType != 2) {
//			throw new InvalidInputDataException("TransportType = {BUS(0), TRAM(1), SUBWAY(2)} - bad request!");
//		}
		
		// =========== CONVERT =============
		
		// setuj start i end date za mesecnu
		// inace cuva u UTC timeZone, tkd kad se pokupi iz baze treba se konvertovati
		// u nasu time zone!
		Date start = null, end = null;
		if (ticketTemporal == 1) {
			start = new Date();
			//ticket.setStartTime(start);
			Calendar c = Calendar.getInstance();
			c.setTime(start);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			end = c.getTime();
			//ticket.setEndTime(end);
		} else if (ticketTemporal == 2) {
			start = new Date();
			//ticket.setStartTime(start);
			Calendar c = Calendar.getInstance();
			c.setTime(start);
			c.set(Calendar.MONTH, Calendar.DECEMBER);
			c.set(Calendar.DAY_OF_MONTH, 31);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			end = c.getTime();
			//ticket.setEndTime(end);
		}

		
		if (ticketDTO.getLineId() != null) {
			LineTicket ticket = new LineTicket();
			
			Line l = lineService.findById(ticketDTO.getLineId());		// throws DAO if not found
			
			if (start != null && end != null) {
				ticket.setStartTime(start);
				ticket.setEndTime(end);
			}
			
			ticket.setLine(l);
			// ako je one_time -> potrebna je aktivacija
			if (ticketTemporal == 0) {
				ticket.setActive(TicketActivationType.NOT_ACTIVE);	
				// ako je monthly/yearly -> aktiviraj odmah
			} else {
				ticket.setActive(TicketActivationType.ACTIVE);
			}
			ticket.setTicketTemporal(ticketDTO.getTicketTemporal());
			ticket.setTransportType(ticketDTO.getTransportType());
			return ticket;
			
			
		} else if (ticketDTO.getZoneId() != null) {
			ZoneTicket ticket = new ZoneTicket();
			
			Zone z = zoneService.findById(ticketDTO.getZoneId());		// throws ZoneNotFound if not found
			
			if (start != null && end != null) {
				ticket.setStartTime(start);
				ticket.setEndTime(end);
			}
			
			ticket.setZone(z);
			// ista prica
			if (ticketTemporal == 0) {
				ticket.setActive(TicketActivationType.NOT_ACTIVE);	
			} else {
				ticket.setActive(TicketActivationType.ACTIVE);
			}
			ticket.setTicketTemporal(ticketDTO.getTicketTemporal());
			ticket.setTransportType(ticketDTO.getTransportType());
			return ticket;
		
		} else if(ticketDTO.getRouteId() != null){

            RouteTicket ticket = new RouteTicket();

            Route route = this.routeService.getRoute(ticketDTO.getRouteId());
            ticket.setRoute(route);
            ticket.setActive(TicketActivationType.ACTIVE);
            ticket.setTicketTemporal(TicketTypeTemporal.ONE_TIME_PASS);
            ticket.setTransportType(ticketDTO.getTransportType());

            return ticket;

        } else {
			// nikad ne ulazi
			return null;
		}
	}
	
	@Override
	public PriceList convertDTOtoPriceList(PriceListDTO dto) {
		// ========== CHECK DATA ==========
		for (Long zoneId : dto.getZonePrices().keySet()) {
			zoneService.findById(zoneId);		// thorws DAO
		}
		if (dto.getLineDiscount() < 0 || dto.getMonthlyCoeff() < 0 ||
				dto.getSeniorDiscount() < 0 || dto.getStudentDiscount() < 0 ||
				dto.getYearlyCoeff() < 0) {
			throw new InvalidInputDataException("Invalid input data for Price List!");
		}
		
		// =========== CONVERT =============
		PriceList priceList = new PriceList();
		priceList.setActive(false);
		priceList.setStudentDiscount(dto.getStudentDiscount());
		priceList.setSeniorDiscount(dto.getSeniorDiscount());
		priceList.setMonthlyCoeffitient(dto.getMonthlyCoeff());
		priceList.setYearlyCoeffitient(dto.getYearlyCoeff());
		priceList.setOneTimePrices(dto.getZonePrices());
		priceList.setLineDiscount(dto.getLineDiscount());
		return priceList;
	}

}
