package ftn.kts.transport.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("line_ticket")
public class LineTicket extends Ticket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@ManyToOne(fetch = FetchType.LAZY)
	private Line line;
	
	
	public LineTicket() {
		
	}

	public LineTicket(Long id, Line line) {
		super();
		this.line = line;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}
	
	
	
}
