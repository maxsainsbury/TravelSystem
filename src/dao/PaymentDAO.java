package dao;

import java.util.ArrayList;
import model.Payment;
import utility.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Ebba de Groot
 */
public class PaymentDAO { 
    
    private Payment payment;

    // Default constructor
    public PaymentDAO() { }
    
    public boolean addPaymentRecord(Payment payment) {
        String query = """
                       INSERT INTO payment
                       (booking_id, employee_id, payment_date, amount, payment_method, status) 
                       VALUES(?,?,?,?,?,?);
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, payment.getBookingId());
            preparedStatement.setInt(2, payment.getEmployeeId());
            preparedStatement.setString(3, payment.getPaymentDate().toString());
            preparedStatement.setDouble(4, payment.getAmount());
            preparedStatement.setString(5, payment.getPaymentMethod());
            preparedStatement.setString(6, payment.getStatus());
            
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<Payment> fetchAllPayments(){
        ArrayList<Payment> payments = new ArrayList<>();
        String query = """
                       SELECT *
                       FROM payment
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                payment = new Payment(
                resultSet.getInt("booking_id"),
                resultSet.getInt("employee_id"),
                resultSet.getDouble("amount"),
                resultSet.getString("payment_method"), 
                resultSet.getString("status"),   
                resultSet.getString("payment_date"),
                resultSet.getInt("payment_id")
                );
                payments.add(payment);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
    
    public Payment fetchPaymentById(int paymentId) throws Exception{
        String query = """
                       SELECT *
                       FROM payment
                       WHERE payment_id = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, paymentId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                payment = new Payment(
                resultSet.getInt("booking_id"),
                resultSet.getInt("employee_id"),
                resultSet.getDouble("amount"),
                resultSet.getString("payment_method"), 
                resultSet.getString("status"),   
                resultSet.getString("payment_date"),
                resultSet.getInt("payment_id")
                );
            } else {
                throw new Exception();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payment;
    }
    
    public Payment fetchPaymentByBookingId(int bookingId) throws Exception{
        String query = """
                       SELECT *
                       FROM payment
                       WHERE booking_id = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, bookingId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                payment = new Payment(
                resultSet.getInt("booking_id"),
                resultSet.getInt("employee_id"),
                resultSet.getDouble("amount"),
                resultSet.getString("payment_method"), 
                resultSet.getString("status"),   
                resultSet.getString("payment_date"),
                resultSet.getInt("payment_id")
                );
            } else {
                throw new Exception();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payment;
    }
    
}
