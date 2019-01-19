package ftn.kts.transport.dtos;

import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;

import java.io.File;
import java.util.Date;

public class TicketDTO {

	private int transportType;
	private int ticketTemporal;
	private Long zoneId;
	private Long lineId;
	private User user;
	private Date endTime;
	private String qrCode;
	
	public TicketDTO() {
		
	}

	public TicketDTO(int transportType, int ticketTemporal, Long zoneId, Long lineId) {
		super();
		this.transportType = transportType;
		this.ticketTemporal = ticketTemporal;
		this.zoneId = zoneId;
		this.lineId = lineId;
	}

	public TicketDTO(Ticket ticket, String qrCode){
		this.user = ticket.getUser();
		this.endTime = ticket.getEndTime();
		this.transportType = ticket.getTransportType().ordinal();
		this.ticketTemporal = ticket.getTicketTemporal().ordinal();
		this.qrCode = qrCode;
	}

	public TicketDTO(Ticket ticket) {
		this.user = ticket.getUser();
		this.endTime = ticket.getEndTime();

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
}
