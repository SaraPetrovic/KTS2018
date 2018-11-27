package ftn.kts.transport.dtos;

public class RouteDTO {

	private String startTime;
	private String line;		// moze i ID i name videcemo
	private String vehicle;		// ista prica
	
	public RouteDTO() {
		
	}

	public RouteDTO(String startTime, String line, String vehicle) {
		super();
		this.startTime = startTime;
		this.line = line;
		this.vehicle = vehicle;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	
	
}
