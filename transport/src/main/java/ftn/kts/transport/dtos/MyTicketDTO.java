package ftn.kts.transport.dtos;

import java.util.Date;

import ftn.kts.transport.enums.TicketActivationType;
import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.LineTicket;
import ftn.kts.transport.model.Route;
import ftn.kts.transport.model.RouteTicket;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.Zone;
import ftn.kts.transport.model.ZoneTicket;

public class MyTicketDTO {

	private Long id;
	private VehicleType transportType;
	private TicketTypeTemporal ticketTemporal;
	private Date startTime;
	private Date endTime;
	private String qrCode;
	private TicketActivationType active;
	private double price;
	private Route route;
	private Zone zone;
	private Line line;
	
	public MyTicketDTO() {
		
	}
	
	public MyTicketDTO(Long id, VehicleType transportType, TicketTypeTemporal ticketTemporal, Date startTime,
			Date endTime, String qrCode, TicketActivationType active, double price, Route route, Zone zone, Line line) {
		super();
		this.id = id;
		this.transportType = transportType;
		this.ticketTemporal = ticketTemporal;
		this.startTime = startTime;
		this.endTime = endTime;
		this.qrCode = qrCode;
		this.active = active;
		this.price = price;
		this.route = route;
		this.zone = zone;
		this.line = line;
	}

	public MyTicketDTO(Ticket ticket, String qrCode){
		this.id = ticket.getId();
		this.startTime = ticket.getStartTime();
		this.endTime = ticket.getEndTime();
		this.transportType = ticket.getTransportType();
		this.ticketTemporal = ticket.getTicketTemporal();
		this.qrCode = qrCode;
		this.price = ticket.getPrice();
		this.active = ticket.getActive();
		if(ticket instanceof RouteTicket) {
			this.route = ((RouteTicket) ticket).getRoute();
		}else {
			this.route = null;
		}
		if(ticket instanceof ZoneTicket) {
			this.zone = ((ZoneTicket) ticket).getZone();
		}else {
			this.zone = null;
		}
		if(ticket instanceof LineTicket) {
			this.line = ((LineTicket) ticket).getLine();
		}else {
			this.line = null;
		}
		System.out.println("START DATE" + ticket.getStartTime() + " END DATE: " + ticket.getEndTime());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VehicleType getTransportType() {
		return transportType;
	}

	public void setTransportType(VehicleType transportType) {
		this.transportType = transportType;
	}

	public TicketTypeTemporal getTicketTemporal() {
		return ticketTemporal;
	}

	public void setTicketTemporal(TicketTypeTemporal ticketTemporal) {
		this.ticketTemporal = ticketTemporal;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public TicketActivationType getActive() {
		return active;
	}

	public void setActive(TicketActivationType active) {
		this.active = active;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}
	
}
