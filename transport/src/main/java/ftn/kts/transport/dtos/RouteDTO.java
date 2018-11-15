package ftn.kts.transport.dtos;

public class RouteDTO {

	private String name;
	private String startTime;
	private String endTime;
	private String line;		// moze i ID i name videcemo
	private String vehicle;		// ista prica
	
	public RouteDTO() {
		
	}

	public RouteDTO(String name, String startTime, String endTime, String line, String vehicle) {
		super();
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.line = line;
		this.vehicle = vehicle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
