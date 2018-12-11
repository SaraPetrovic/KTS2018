package ftn.kts.transport.exception;

public class ZoneNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long zoneId;
	
	public ZoneNotFoundException(Long id) {
		this.zoneId = id;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}
	
}
