package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PriceList implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private double oneTime;
	@Column
	private double monthly;
	@Column
	private double yearly;
	@Column
	private double studentDiscount;
	@Column
	private double seniorDiscount;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDateTime;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDateTime;
	
	public PriceList() {
		
	}

	public PriceList(Long id, double oneTime, double monthly, double yearly, double studentDiscount,
			double seniorDiscount, Date startDateTime, Date endDateTime) {
		super();
		this.id = id;
		this.oneTime = oneTime;
		this.monthly = monthly;
		this.yearly = yearly;
		this.studentDiscount = studentDiscount;
		this.seniorDiscount = seniorDiscount;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getOneTime() {
		return oneTime;
	}

	public void setOneTime(double oneTime) {
		this.oneTime = oneTime;
	}

	public double getMonthly() {
		return monthly;
	}

	public void setMonthly(double monthly) {
		this.monthly = monthly;
	}

	public double getYearly() {
		return yearly;
	}

	public void setYearly(double yearly) {
		this.yearly = yearly;
	}

	public double getStudentDiscount() {
		return studentDiscount;
	}

	public void setStudentDiscount(double studentDiscount) {
		this.studentDiscount = studentDiscount;
	}

	public double getSeniorDiscount() {
		return seniorDiscount;
	}

	public void setSeniorDiscount(double seniorDiscount) {
		this.seniorDiscount = seniorDiscount;
	}

	public Date getstartDateTime() {
		return startDateTime;
	}

	public void setstartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getendDateTime() {
		return endDateTime;
	}

	public void setendDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	
	
	
}
