package model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Max Sainsbury
 */
public class Promotion {
    private int promoId;
    private String description;
    private String promoName;
    private double discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    
    /**
     * Constructor for a Promotion object for when getting the information from the database
     * @param promoName
     * @param discountPercent
     * @param description
     * @param status
     * @param startDate
     * @param endDate
     * @param promoId 
     */
    public Promotion(String promoName, double discountPercent, String description, String status, String startDate, String endDate, int promoId) {
        this.promoName = promoName;
        this.discountPercent = discountPercent;
        this.description = description;
        this.status = status;
        this.promoId = promoId;
        //Set up a formatter to tell the program how to interpret the value given by the date column in the Promotion database
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //turn the value from the database into a LocalDate object
        this.startDate = LocalDate.parse(startDate, formatter);
        this.endDate = LocalDate.parse(endDate, formatter);
    }
    
    /**
     * Constructor for a Promotion object for when creating a new promotion in the GUI
     * @param promoName
     * @param discountPercent
     * @param year
     * @param month 
     */
    public Promotion(String promoName, double discountPercent, String description, String status, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        this.promoName = promoName;
        this.discountPercent = discountPercent;
        this.description = description;
        this.status = status;
        //take the inputed year and month values, and set a Localdate object to the first of that month
        this.startDate = LocalDate.of(startYear, startMonth, startDay);
        this.endDate = LocalDate.of(endYear, endMonth, endDay);
    }

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
    
}
