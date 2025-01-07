package org.CashCatalysts.CashCatalysts.Database;

/**
 * Class representing a definition of a database field
 * @param name Name of the field
 * @param type Datatype of the field (see: sqlite.org for supported types)
 * @param settings Additional settings for the field (ex. PRIMARY KEY, AUTOINCREMENT, NOT NULL)
 */
public record DbField(String name, String type, String settings) {
    public DbField(String name, String type) {
        this(name, type, "");
    }

    public String toString() {
        return name + " " + type + " " + settings;
    }
}
