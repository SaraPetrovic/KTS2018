package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PriceList implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="price")
	@MapKeyColumn(name="zoneName")
	@ElementCollection
	@CollectionTable(name="OneTimePrices", joinColumns=@JoinColumn(name = "price_list_id"))
	private Map<String, Double> oneTimeZonePrices;
	@Column(name="price")
	@MapKeyColumn(name="zoneName")
	@ElementCollection
	@CollectionTable(name="MonthlyPrices", joinColumns=@JoinColumn(name = "price_list_id"))
	private Map<String, Double> monthlyZonePrices;
	@Column(name="price")
	@MapKeyColumn(name="zoneName")
	@ElementCollection
	@CollectionTable(name="YearlyPrices", joinColumns=@JoinColumn(name = "price_list_id"))
	private Map<String, Double> yearlyZonePrices;
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

	public PriceList(Long id, Map<String, Double> oneTime, Map<String, Double> monthly, Map<String, Double> yearly, double studentDiscount,
			double seniorDiscount, Date startDateTime, Date endDateTime) {
		super();
		this.id = id;
		this.oneTimeZonePrices = oneTime;
		this.monthlyZonePrices = monthly;
		this.yearlyZonePrices = yearly;
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

	public Map<String, Double> getOneTime() {
		return oneTimeZonePrices;
	}

	public void setOneTime(Map<String, Double> oneTime) {
		this.oneTimeZonePrices = oneTime;
	}

	public Map<String, Double> getMonthly() {
		return monthlyZonePrices;
	}

	public void setMonthly(Map<String, Double> monthly) {
		this.monthlyZonePrices = monthly;
	}

	public Map<String, Double> getYearly() {
		return yearlyZonePrices;
	}

	public void setYearly(Map<String, Double> yearly) {
		this.yearlyZonePrices = yearly;
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
