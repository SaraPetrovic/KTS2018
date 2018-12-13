package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "LINE_AND_STATION")
public class LineAndStation {
	
	@EmbeddedId
	private LineAndStationPK lineAndStationPK;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LINE_ID", insertable=false, updatable=false)
	private Line line;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATION_ID", insertable=false, updatable=false)
	private Station station;
	@Column
	private int stationOrder;
	
	
	public LineAndStation() {
		
	}
	

	public LineAndStation(int stationOrder) {
		super();
		this.stationOrder = stationOrder;
	}


	public int getStationOrder() {
		return stationOrder;
	}

	public void setStationOrder(int stationOrder) {
		this.stationOrder = stationOrder;
	}


	@JsonBackReference
	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	@JsonBackReference
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public LineAndStationPK getLineAndStationPK() {
		return this.lineAndStationPK;
	}
	
	public void setLineAndStationPK(LineAndStationPK lineAndStationPK) {
		this.lineAndStationPK = lineAndStationPK;
	}

	
	public void addStation(Line line, Station station, int order) {
		this.line = line;
		this.station = station;
		this.stationOrder = order;
		
		setLineAndStationPK(new LineAndStationPK(line.getId(), station.getId()));
		
	}


	@Override
	public String toString() {
		String ret = "";
		ret += "\nLinija:\n";
		ret += this.line.getId() +"  |" + this.line.getName()  + "\n";
		ret += "Station:\n";
		ret += this.station.getId() + "  |" + this.station.getName() + "\n";
		ret += "PK:\n";
		ret += this.lineAndStationPK.getLineId() + "  |" + this.lineAndStationPK.getStationId() + "\n";
		ret += "ORDER: \n";
		ret += this.stationOrder;
		return ret;
	}

	

	@Embeddable
	public static class LineAndStationPK implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Column(name = "LINE_ID")
		private Long lineId;
		@Column(name = "STATION_ID")
		private Long stationId;
		
		public LineAndStationPK() {
			
		}
		
		public LineAndStationPK(Long lineId, Long stationId) {
			this.lineId = lineId;
			this.stationId = stationId;
		}
		
		
		public Long getLineId() {
			return this.lineId;
		}
		
		
		
		public Long getStationId() {
			return this.stationId;
		}
		
		
		public void setLineId(Long lineId) {
			this.lineId = lineId;
		}

		public void setStationId(Long stationId) {
			this.stationId = stationId;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (!(o instanceof LineAndStationPK)) {
				return false;
			}
			LineAndStationPK other = (LineAndStationPK) o;
			if (!(getStationId().equals(other.getStationId()))) {
				return false;
			}
			
			if (!(getLineId().equals(other.getLineId()))) {
				return false;
			}
			
			return true;
		}
		
		@Override
	    public int hashCode() {
	        return Objects.hash(getLineId(), getStationId());
	    }
		
	}
	
}
