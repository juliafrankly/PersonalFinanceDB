package com.example.personalfinancedb.database;

import com.example.personalfinancedb.model.Transaction;
import com.example.personalfinancedb.model.TransactionType;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionDAO {

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS transactions (
                id UUID PRIMARY KEY,
                amount NUMERIC(10,2) NOT NULL CHECK (amount > 0),
                description TEXT NOT NULL,
                date DATE NOT NULL,
                type VARCHAR(10) NOT NULL
            )
            """;

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabel is created or already exists");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (id, amount, description, date, type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, transaction.getId());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getdescription());
            pstmt.setDate(4, Date.valueOf(transaction.getDate()));
            pstmt.setString(5, transaction.getType().name());

            pstmt.executeUpdate();
        }
    }

    public boolean removeTransaction(UUID id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        String sql = "SELECT * FROM transactions ORDER BY date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        }

        return transactions;
    }

    public Transaction getTransactionById(UUID id) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransaction(rs);
                }
            }
        }

        return null;
    }

    public List<Transaction> getTransactionsByDate(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE date = ? ORDER BY date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(date));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        }

        return transactions;
    }

    public List<Transaction> getTransactionsByWeek(LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE date >= ? AND date < ? ORDER BY date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        }

        return transactions;
    }

    public List<Transaction> getTransactionsByMonth(int year, int month) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE EXTRACT(YEAR FROM date) = ? AND EXTRACT(MONTH FROM date) = ? ORDER BY date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, year);
            pstmt.setInt(2, month);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        }

        return transactions;
    }

    public List<Transaction> getTransactionsByYear(int year) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE EXTRACT(YEAR FROM date) = ? ORDER BY date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, year);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        }

        return transactions;
    }

    public List<Transaction> getTransactionsByType(TransactionType type) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE type = ? ORDER BY date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, type.name());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        }

        return transactions;
    }

    public double calculateBalance() throws SQLException {
        String sql = """
            SELECT 
                SUM(CASE WHEN type = 'INCOME' THEN amount ELSE -amount END) as balance
            FROM transactions
            """;

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("balance");
            }
        }

        return 0.0;
    }

    public boolean isEmpty() throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM transactions";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count") == 0;
            }
        }

        return true;
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        double amount = rs.getDouble("amount");
        String description = rs.getString("description");
        LocalDate date = rs.getDate("date").toLocalDate();
        TransactionType type = TransactionType.valueOf(rs.getString("type"));

        return new Transaction(amount, description, date, type, id);
    }
}