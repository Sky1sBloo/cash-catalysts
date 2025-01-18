package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.budgets.Budget;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.*;

public class BudgetsTable extends DbTable {
    public BudgetsTable(Connection connection) throws SQLException {
        super(connection);

        DbField[] fields = {
                new DbField("budget_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"),
                new DbField("date", "DATE", "NOT NULL"),
                new DbField("amount_cents", "INTEGER", "UNIQUE NOT NULL")
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
