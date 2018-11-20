package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.Date;

public class PriceList implements Serializable {

	private Long id;
	private double oneTime;
	private double manyTime;
	private double studentDiscount;
	private double seniorDiscount;
	private Date startTime;
	private Date endTIme;
	
	
}
