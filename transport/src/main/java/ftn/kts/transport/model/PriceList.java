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
	@MapKeyColumn(name="zoneId")
	@ElementCollection
	@CollectionTable(name="OneTimePrices", joinColumns=@JoinColumn(name = "price_list_id"))
	private Map<Long, Double> oneTimePrices;
	@Column
	private double monthlyCoeffitient;
	@Column
	private double yearlyCoeffitient;
	@Column
	private double studentDiscount;
	@Column
	private double seniorDiscount;
	@Column
	private double lineDiscount;  			// discount u odnosu na zonsku mesecnu/godisnju ako hoces samo za jednu liniju
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDateTime;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDateTime;
	@Column
	private boolean active;
	
	public PriceList() {
		
	}

	public PriceList(Long id, Map<Long, Double> oneTimePrices, double monthlyCoeffitient, double yearlyCoeffitient,
			double studentDiscount, double seniorDiscount, Date startDateTime, Date endDateTime, boolean active, 
			double lineDiscount) {
		super();
		this.id = id;
		this.oneTimePrices = oneTimePrices;
		this.monthlyCoeffitient = monthlyCoeffitient;
		this.yearlyCoeffitient = yearlyCoeffitient;
		this.studentDiscount = studentDiscount;
		this.seniorDiscount = seniorDiscount;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.active = active;
		this.lineDiscount = lineDiscount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<Long, Double> getOneTimePrices() {
		return oneTimePrices;
	}

	public void setOneTimePrices(Map<Long, Double> oneTimePrices) {
		this.oneTimePrices = oneTimePrices;
	}

	public double getMonthlyCoeffitient() {
		return monthlyCoeffitient;
	}

	public void setMonthlyCoeffitient(double monthlyCoeffitient) {
		this.monthlyCoeffitient = monthlyCoeffitient;
	}

	public double getYearlyCoeffitient() {
		return yearlyCoeffitient;
	}

	public void setYearlyCoeffitient(double yearlyCoeffitient) {
		this.yearlyCoeffitient = yearlyCoeffitient;
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

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public double getLineDiscount() {
		return lineDiscount;
	}

	public void setLineDiscount(double lineDiscount) {
		this.lineDiscount = lineDiscount;
	}

	
	
}
