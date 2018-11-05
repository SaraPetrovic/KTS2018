package ftn.kts.transport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
//	@OneToOne
//	private Ticketable ticketable;
	@Column
	private double price;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User user;
	
	public Ticket() {
		
	}

//	public Ticket(Ticketable ticketable, double price) {
//		super();
//		this.ticketable = ticketable;
//		this.price = price;
//	}
//
//	public Ticket(Long id, Ticketable ticketable, double price) {
//		super();
//		this.id = id;
//		this.ticketable = ticketable;
//		this.price = price;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Ticketable getTicketable() {
//		return ticketable;
//	}
//
//	public void setTicketable(Ticketable ticketable) {
//		this.ticketable = ticketable;
//	}
//
//	public double getPrice() {
//		return price;
//	}
//
//	public void setPrice(double price) {
//		this.price = price;
//	}
	
	
	
}
