package ftn.kts.transport.dtos;

import java.util.Date;
import java.util.HashMap;

public class PriceListDTO {

	private HashMap<Long, Double> zonePrices = new HashMap<Long, Double>();
	private double monthlyCoeff;
	private double yearlyCoeff;
	private double onehourCoeff;
	private double studentDiscount;
	private double seniorDiscount;
	private double lineDiscount;
	private Date startDate;
	
	public PriceListDTO() {
		
	}

	public PriceListDTO(HashMap<Long, Double> zonePrices, double monthlyCoeff, double yearlyCoeff,
			double studentDiscount, double seniorDiscount, double lineDiscount) {
		super();
		this.zonePrices = zonePrices;
		this.monthlyCoeff = monthlyCoeff;
		this.yearlyCoeff = yearlyCoeff;
		this.studentDiscount = studentDiscount;
		this.seniorDiscount = seniorDiscount;
		this.lineDiscount = lineDiscount;
	}

	public PriceListDTO(HashMap<Long, Double> zonePrices, double monthlyCoeff, double yearlyCoeff, double onehourCoeff, double studentDiscount, double seniorDiscount, double lineDiscount) {
		this.zonePrices = zonePrices;
		this.monthlyCoeff = monthlyCoeff;
		this.yearlyCoeff = yearlyCoeff;
		this.onehourCoeff = onehourCoeff;
		this.studentDiscount = studentDiscount;
		this.seniorDiscount = seniorDiscount;
		this.lineDiscount = lineDiscount;
	}

	public double getOnehourCoeff() {
		return onehourCoeff;
	}

	public void setOnehourCoeff(double onehourCoeff) {
		this.onehourCoeff = onehourCoeff;
	}

	public HashMap<Long, Double> getZonePrices() {
		return zonePrices;
	}

	public void setZonePrices(HashMap<Long, Double> zonePrices) {
		this.zonePrices = zonePrices;
	}

	public double getMonthlyCoeff() {
		return monthlyCoeff;
	}

	public void setMonthlyCoeff(double monthlyCoeff) {
		this.monthlyCoeff = monthlyCoeff;
	}

	public double getYearlyCoeff() {
		return yearlyCoeff;
	}

	public void setYearlyCoeff(double yearlyCoeff) {
		this.yearlyCoeff = yearlyCoeff;
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

	public double getLineDiscount() {
		return lineDiscount;
	}

	public void setLineDiscount(double lineDiscount) {
		this.lineDiscount = lineDiscount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
	
	

	
	
	
}
