package model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Max Sainsbury
 */
public class Payment {
    private int paymentId;
    private int bookingId;
    private int employeeId;
    private LocalDate paymentDate;
    private double amount;
    private String paymentMethod;
    private String status;
    
    
    /**
     * Constructor for Payment class for when getting from the database
     * @param bookingId
     * @param employeeId
     * @param amount
     * @param paymentMethod
     * @param status
     * @param paymentDate
     * @param paymentId 
     */
    public Payment(int bookingId, int employeeId, double amount, String paymentMethod, String status, String paymentDate, int paymentId) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.employeeId = employeeId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.paymentDate = LocalDate.parse(paymentDate, formatter);
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }
    
    /**
     * Constructor for Payment class for when creating a new payment
     * @param bookingId
     * @param employeeId
     * @param amount
     * @param paymentMethod
     * @param status
     * @param paymentYear
     * @param paymentMonth
     * @param paymentDay 
     */
    public Payment(int bookingId, int employeeId, double amount, String paymentMethod, String status, int paymentYear, int paymentMonth, int paymentDay) {
        this.bookingId = bookingId;
        this.employeeId = employeeId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentDate = LocalDate.of(paymentYear, paymentMonth, paymentDay);
    }
    
    /**
     * Constructor for a Payment object for when making one in the GUI
     * @param bookingId
     * @param employeeId
     * @param amount
     * @param paymentMethod
     * @param status 
     */
   public Payment(int bookingId, int employeeId, double amount, String paymentMethod, String status, String paymentDate) {
        this.bookingId = bookingId;
        this.employeeId = employeeId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.paymentDate = LocalDate.parse(paymentDate, formatter);
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}


