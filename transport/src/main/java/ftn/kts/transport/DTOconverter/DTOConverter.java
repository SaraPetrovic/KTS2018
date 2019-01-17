package ftn.kts.transport.DTOconverter;


import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.dtos.PriceListDTO;
import ftn.kts.transport.dtos.RouteScheduleDTO;
import ftn.kts.transport.dtos.TicketDTO;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.PriceList;
import ftn.kts.transport.model.RouteSchedule;
import ftn.kts.transport.model.Ticket;

public interface DTOConverter {
	
	public Line convertDTOtoLine(LineDTO lineDTO);
	public RouteSchedule convertDTOtoRouteSchedule(RouteScheduleDTO scheduleDTO);
	public Ticket convertDTOtoTicket(TicketDTO ticketDTO);
	public PriceList convertDTOtoPriceList(PriceListDTO dto);
	
}


