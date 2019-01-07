package ftn.kts.transport.dtos;

public class TicketDTO {

	private int transportType;
	private int ticketTemporal;
	private Long zoneId;
	private Long lineId;
	
	public TicketDTO() {
		
	}

	public TicketDTO(int transportType, int ticketTemporal, Long zoneId, Long lineId) {
		super();
		this.transportType = transportType;
		this.ticketTemporal = ticketTemporal;
		this.zoneId = zoneId;
		this.lineId = lineId;
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
	
	
}
