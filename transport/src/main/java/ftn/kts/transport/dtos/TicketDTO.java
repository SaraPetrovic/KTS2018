package ftn.kts.transport.dtos;

import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;

import java.io.File;
import java.util.Date;

public class TicketDTO {

	private Long id;
	private int transportType;
	private int ticketTemporal;
	private Long routeId;
	private Long zoneId;
	private Long lineId;
	private User user;
	private Date startTime;
	private Date endTime;
	private String qrCode;
	private boolean active;
	private double price;
	
	public TicketDTO() {
		
	}

	public TicketDTO(Long id, int transportType, int ticketTemporal, Long routeId, Long zoneId, Long lineId, User user,
			Date startDate, Date endTime, String qrCode, boolean active, double price) {
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


	public TicketDTO(int transportType, int ticketTemporal, Long routeId, Long zoneId, Long lineId, User user, Date endTime, String qrCode) {
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

	public TicketDTO(int transportType, int ticketTemporal, Long zoneId, Long lineId) {
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
		this.transportType = ticket.getTransportType().ordinal();
		this.ticketTemporal = ticket.getTicketTemporal().ordinal();
		this.qrCode = qrCode;
		this.price = ticket.getPrice();
		this.active = ticket.isActive();
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

	public int getTransportType() {
		return transportType;
	}

	public void setTransportType(int transportType) {
		this.transportType = transportType;
	}

	public int getTicketTemporal() {
		return ticketTemporal;
	}

	public void setTicketTemporal(int ticketTemporal) {
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
