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
    private String month;
    
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
        this.endDate = LocalDate.parse(endDate, formatter);

    }
    
    /**
     * Constructor for a Promotion object for when creating a new promotion in the GUI
     * @param promoName
     * @param discountPercent
     * @param description
     * @param status
     * @param startDate
     * @param endDate
     */
    public Promotion(String promoName, double discountPercent, String description, String status, String startDate, String endDate) {
        this.promoName = promoName;
        this.discountPercent = discountPercent;
        this.description = description;
        this.status = status;  
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
        this.endDate = LocalDate.parse(endDate, formatter);

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
