import java.util.ArrayList;
import java.util.List;

class Transaction {
    private int transactionId;
    private String expenseOrIncome;
    private String name;
    private String type;
    private String date;
    private double amount;

    public Transaction(int transactionId, String expenseOrIncome, String name, String type, String date, double amount) {
        this.transactionId = transactionId;
        this.expenseOrIncome = expenseOrIncome;
        this.name = name;
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "TransactionID: " + transactionId + ", Expense/Income: " + expenseOrIncome +
               ", Name: " + name + ", Type: " + type + ", Date: " + date + ", Amount: " + amount;
    }
}

public class Handler {
    private List<Transaction> transactions = new ArrayList<>();
    private int currentId = 1; // To generate unique transaction IDs

    public void addTransaction(String expenseOrIncome, String name, String type, String date, double amount) {
        Transaction transaction = new Transaction(currentId++, expenseOrIncome, name, type, date, amount);
        transactions.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public Transaction getTransactionById(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId() == id) {
                return transaction;
            }
        }
        return null;
    }


