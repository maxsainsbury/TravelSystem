package model;

import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author Max Sainsbury
 */
public class Promotion {
    private int promoId;
    private LocalDate promoMonth;
    private String promoName;
    private int discountPercent;
    
    /**
     * Constructor for a Promotion object for when getting the information from the database
     * 
     * @param promoName
     * @param discountPercent
     * @param promoMonth
     * @param promoId 
     */
    public Promotion(String promoName, int discountPercent, String promoMonth, int promoId) {
        this.promoName = promoName;
        this.discountPercent = discountPercent;
        this.promoId = promoId;
        this.promoMonth = LocalDate.parse(promoMonth);
    }
    
    /**
     * Constructor for a Promotion object for when creating a new promotion in the GUI
     * @param promoName
     * @param discountPercent
     * @param year
     * @param month 
     */
    public Promotion(String promoName, int discountPercent, int year, int month) {
        this.promoName = promoName;
        this.discountPercent = discountPercent;
        this.promoMonth = LocalDate.of(year, month, 1);
    }

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public LocalDate getPromoMonth() {
        return promoMonth;
    }

    public void setPromoMonth(LocalDate promoMonth) {
        this.promoMonth = promoMonth;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }
    
    
}
