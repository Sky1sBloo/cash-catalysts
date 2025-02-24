package org.CashCatalysts.CashCatalysts.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DbTable {
    protected final Connection connection;

    // Constructor without creating table
    public DbTable(Connection connection) {
        this.connection = connection;
    }

    // Constructor with table definition
    public DbTable(Connection connection, String tableName, DbField[] fields) throws SQLException {
        this.connection = connection;
        String tableSchema = tableSchema(tableName, fields);
        connection.createStatement().execute(tableSchema);
    }

    /**
     * Method to define the table schema
     *
     * @param tableName Name of the table
     * @param fields    All fields of the table, see DbField
     */
    public void createTable(String tableName, DbField[] fields) throws SQLException {
        connection.createStatement().execute(tableSchema(tableName, fields));
    }

    /**
     * Method to define the table schema
     *
     * @param tableName   Name of the table
     * @param fields      All fields of the table, see DbField
     * @param constraints All constraints of the table
     */
    public void createTable(String tableName, DbField[] fields, String[] constraints) throws SQLException {
        connection.createStatement().execute(tableSchema(tableName, fields, constraints));
    }

    /**
     * Gets the last row id of added insert
     * Generally used after registering a new primary key
     *
     * @return The last row id inserted
     */
    protected int getLastRowId() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("select last_insert_rowid();");
        if (rs.next()) {
            return rs.getInt(1);
        }

        throw new SQLException("Get last user id ResultSet doesn't return anything");
    }

    /**
     * Method to define the table schema
     *
     * @param tableName Name of the table
     * @param fields    All fields of the table, see DbField
     * @return Schematic representation of the table
     */
    private String tableSchema(String tableName, DbField[] fields) {
        StringBuilder schema = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");
        for (DbField field : fields) {
            schema.append(field.toString()).append(", ");
        }
        schema.deleteCharAt(schema.length() - 2);
        schema.append(");");
        return schema.toString();
    }


    /**
     * Method to define the table schema
     *
     * @param tableName Name of the table
     * @param fields    All fields of the table, see DbField
     * @return Schematic representation of the table
     */
    private String tableSchema(String tableName, DbField[] fields, String[] constraints) {
        StringBuilder schema = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");
        for (DbField field : fields) {
            schema.append(field.toString()).append(", ");
        }
        for (String constraint : constraints) {
            schema.append(constraint).append(", ");
        }
        schema.deleteCharAt(schema.length() - 2);
        schema.append(");");
        return schema.toString();
    }
}
