package ftn.kts.transport.dtos;

import ftn.kts.transport.enums.TicketActivationType;
import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Route;
import ftn.kts.transport.model.RouteTicket;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;

public class TicketDTO {

	private Long id;
	private VehicleType transportType;
	private TicketTypeTemporal ticketTemporal;
	private Long routeId;
	private Long zoneId;
	private Long lineId;
	private User user;
	private Date startTime;
	private Date endTime;
	private String qrCode;
	private TicketActivationType active;
	private double price;
	private Route route;
	
	public TicketDTO() {
		
	}

	public TicketDTO(Long id, VehicleType transportType, TicketTypeTemporal ticketTemporal, Long routeId, Long zoneId, Long lineId, User user,
			Date startDate, Date endTime, String qrCode, TicketActivationType active, double price) {
		super();
		this.id = id;
		this.transportType = transportType;
		this.ticketTemporal = ticketTemporal;
		this.routeId = routeId;
		this.zoneId = zoneId;
		this.lineId = lineId;
		this.user = user;
		this.startTime = startDate;
		this.endTime = endTime;
		this.qrCode = qrCode;
		this.active = active;
		this.price = price;
	}


	public TicketDTO(VehicleType transportType, TicketTypeTemporal ticketTemporal, Long routeId, Long zoneId, Long lineId, User user, Date endTime, String qrCode) {
		this.transportType = transportType;
		this.ticketTemporal = ticketTemporal;
		this.routeId = routeId;
		this.zoneId = zoneId;
		this.lineId = lineId;
		this.user = user;
		this.endTime = endTime;
		this.qrCode = qrCode;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public TicketDTO(VehicleType transportType, TicketTypeTemporal ticketTemporal, Long zoneId, Long lineId) {
		super();
		this.transportType = transportType;
		this.ticketTemporal = ticketTemporal;
		this.zoneId = zoneId;
		this.lineId = lineId;
	}

	public TicketDTO(Ticket ticket, String qrCode){
		this.id = ticket.getId();
		this.user = ticket.getUser();
		this.startTime = ticket.getStartTime();
		this.endTime = ticket.getEndTime();
		this.transportType = ticket.getTransportType();
		this.ticketTemporal = ticket.getTicketTemporal();
		this.qrCode = qrCode;
		this.price = ticket.getPrice();
		this.active = ticket.getActive();
		if(ticket instanceof RouteTicket) {
			this.route = ((RouteTicket) ticket).getRoute();
			System.out.println("TICKET DTO ROUTEEEEEE!!!!!!!!");
		}else {
			this.route = null;
		}
		System.out.println("START DATE" + ticket.getStartTime() + " END DATE: " + ticket.getEndTime());
	}

	public TicketDTO(Ticket ticket) {
		this.user = ticket.getUser();
		this.endTime = ticket.getEndTime();

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

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Date getStartDate() {
		return startTime;
	}

	public void setStartDate(Date startDate) {
		this.startTime = startDate;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
}
