package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.budgets.Budget;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetsTable extends DbTable {
    public BudgetsTable(Connection connection) throws SQLException {
        super(connection);

        DbField[] fields = {
                new DbField("budget_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"),
                new DbField("date", "DATE", "UNIQUE NOT NULL"),
                new DbField("amount_cents", "INTEGER", "NOT NULL")
        };
        super.createTable("budgets", fields);
    }

    public int addBudget(Budget budget) throws SQLException {
        String sql = "INSERT INTO budgets (date, amount_cents) VALUES(?, ?);";
        PreparedStatement addStatement = connection.prepareStatement(sql);

        addStatement.setDate(1, budget.date());
        addStatement.setInt(2, budget.amount().getAmountCents());

        addStatement.executeUpdate();
        return getLastRowId();
    }

    public Budget getBudget(int id) throws SQLException {
        String sql = "SELECT * FROM budgets WHERE budget_id = ?;";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        getStatement.setInt(1, id);
        ResultSet budgetSet = getStatement.executeQuery();
        if (budgetSet.next()) {
            return new Budget(
                    budgetSet.getInt("budget_id"),
                    budgetSet.getDate("date"),
                    new Currency(budgetSet.getInt("amount_cents"))
            );
        }
        return null;
    }

    /**
     * Retrieves a budget by date
     */
    public Budget getBudget(Date date) throws SQLException {
        String sql = "SELECT * FROM budgets WHERE date = ?;";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        getStatement.setDate(1, date);
        ResultSet budgetSet = getStatement.executeQuery();
        if (budgetSet.next()) {
            return new Budget(
                    budgetSet.getInt("budget_id"),
                    budgetSet.getDate("date"),
                    new Currency(budgetSet.getInt("amount_cents"))
            );
        }
        return null;
    }
    /**
     * Returns the transactions between dates
     */
    public List<Budget> getAllTransactionsBetween(Date start, Date end) throws SQLException {
        List<Budget> budgets= new ArrayList<>();
        String sql = "SELECT * FROM budgets WHERE date BETWEEN ? and ?";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        getStatement.setDate(1, start);
        getStatement.setDate(2, end);

        ResultSet rs = getStatement.executeQuery();
        while (rs.next()) {
            budgets.add(new Budget(
                    rs.getInt("budget_id"),
                    rs.getDate("date"),
                    new Currency(rs.getInt("amount_cents"))
            ));
        }
        return budgets;
    }

    public List<Budget> getAllBudgets() throws SQLException {
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT * FROM budgets;";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        ResultSet rs = getStatement.executeQuery();
        while (rs.next()) {
            budgets.add(new Budget(
                    rs.getInt("budget_id"),
                    rs.getDate("date"),
                    new Currency(rs.getInt("amount_cents"))
            ));
        }
        return budgets;
    }

    /**
     * Updates the budget
     */
    public void updateBudget(Date date, Currency amount) throws SQLException {
        String sql = "UPDATE budgets SET amount_cents = ? WHERE date = ?;";
        PreparedStatement updateStatement = connection.prepareStatement(sql);

        updateStatement.setInt(1, amount.getAmountCents());
        updateStatement.setDate(2, date);
        updateStatement.executeUpdate();
    }

    /**
     * Updates the budget
     */
    public void updateBudget(int id, Currency amount) throws SQLException {
        String sql = "UPDATE budgets SET amount_cents = ? WHERE budget_id = ?;";
        PreparedStatement updateStatement = connection.prepareStatement(sql);

        updateStatement.setInt(1, amount.getAmountCents());
        updateStatement.setInt(2, id);
        updateStatement.executeUpdate();
    }
}
