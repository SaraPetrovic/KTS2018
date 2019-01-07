package ftn.kts.transport.DTOconverter;


import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.dtos.PriceListDTO;
import ftn.kts.transport.dtos.RouteScheduleDTO;
import ftn.kts.transport.dtos.TicketDTO;
import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.LineTicket;
import ftn.kts.transport.model.PriceList;
import ftn.kts.transport.model.RouteSchedule;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.model.ZoneTicket;

public interface DTOConverter {

	public static Line convertDTOtoLine(LineDTO lineDTO) {
		Line l = new Line();
		l.setName(lineDTO.getName());
		l.setTransportType(VehicleType.values()[lineDTO.getVehicleType()]);
		return l;
	}
	
	public static RouteSchedule convertDTOtoRouteSchedule(RouteScheduleDTO scheduleDTO) {
		RouteSchedule schedule = new RouteSchedule();
		schedule.setActive(true);
		schedule.setactiveFrom(scheduleDTO.getActiveFrom());
		schedule.setSaturday(scheduleDTO.getSaturday());
		schedule.setSunday(scheduleDTO.getSunday());
		schedule.setWeekday(scheduleDTO.getWeekday());
		return schedule;
	}
	
	public static Ticket convertDTOtoTicket(TicketDTO ticketDTO) {
		
		if (ticketDTO.getLineId() != -1) {
			LineTicket ticket = new LineTicket();
			// samo prosledi id od Line, pa cu getovati iz servisa
			Line l = new Line();
			l.setId(ticketDTO.getLineId());
			ticket.setLine(l);
			ticket.setActive(false);
			ticket.setTicketTemporal(TicketTypeTemporal.values()[ticketDTO.getTicketTemporal()]);
			ticket.setTransportType(VehicleType.values()[ticketDTO.getTransportType()]);
			return ticket;
			
			
		} else if (ticketDTO.getZoneId() != -1) {
			ZoneTicket ticket = new ZoneTicket();
			// ista prica
			Zone z = new Zone();
			z.setId(ticketDTO.getZoneId());
			ticket.setZone(z);
			ticket.setActive(false);
			ticket.setTicketTemporal(TicketTypeTemporal.values()[ticketDTO.getTicketTemporal()]);
			ticket.setTransportType(VehicleType.values()[ticketDTO.getTransportType()]);
			return ticket;
		
		} else {
			return null;
		}
	}
	
	public static PriceList convertDTOtoPriceList(PriceListDTO dto) {
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


