import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class BankApp implements BankService {

    private final Map<Integer, Account> accounts = new HashMap<>();
    private int nextAcc = 1000;
    private void validateAmount(double amt) {
        if (amt <= 0) throw new RuntimeException("Amount must be > 0");
    }

    private void validateEmail(String email) {
        if (!email.contains("@")) throw new RuntimeException("Invalid email");
    }

    private Account find(int acc) {
        Account a = accounts.get(acc);
        if (a == null) throw new RuntimeException("Account not found: " + acc);
        return a;
    }

    @Override
    public Account createAccount(String name, String email, String phone, String type, double initialDeposit) {
        validateEmail(email);
        validateAmount(initialDeposit);

        AccountType atype;
        try { atype = AccountType.valueOf(type.toUpperCase()); }
        catch (Exception e) { throw new RuntimeException("Invalid account type"); }

        Account acc = new Account(nextAcc++, name, email, phone, atype, initialDeposit);
        accounts.put(acc.accNumber, acc);

        return acc;
    }

    @Override
    public void deposit(int acc, double amt) {
        validateAmount(amt);
        Account a = find(acc);
        a.balance += amt;
        a.addTransaction("DEPOSIT", amt);
    }

    @Override
    public void withdraw(int acc, double amt) {
        validateAmount(amt);
        Account a = find(acc);
        if (a.balance < amt) throw new RuntimeException("Insufficient balance");
        a.balance -= amt;
        a.addTransaction("WITHDRAW", amt);
    }

    @Override
    public void transfer(int from, int to, double amt) {
        validateAmount(amt);
        if (from == to) throw new RuntimeException("Same account");

        Account A = find(from);
        Account B = find(to);

        if (A.balance < amt) throw new RuntimeException("Insufficient balance");

        A.balance -= amt;
        B.balance += amt;

        A.addTransaction("TRANSFER_OUT", amt + " → Acc# " + to);
        B.addTransaction("TRANSFER_IN", amt + " ← Acc# " + from);
    }

    @Override
    public List<String> getStatement(int acc) {
        return new ArrayList<>(find(acc).transactions);
    }

    @Override
    public List<Account> listAll() {
        return new ArrayList<>(accounts.values());
    }
}

class Account {
    int accNumber;
    String name, email, phone;
    AccountType type;
    double balance;
    List<String> transactions = new ArrayList<>();

    public Account(int accNumber, String name, String email, String phone,
                   AccountType type, double balance) {
        this.accNumber = accNumber;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.balance = balance;
        addTransaction("INITIAL", balance);
    }

    void addTransaction(String type, double amt) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        transactions.add(time + " | " + type + " | Rs " + amt + " | Bal: " + balance);
    }

    void addTransaction(String type, String details) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        transactions.add(time + " | " + type + " | " + details + " | Bal: " + balance);
    }

    @Override
    public String toString() {
        return "Acc#" + accNumber + " | " + name + " | " + type + " | Bal: " + balance;
    }
}

enum AccountType {
    SAVINGS, CURRENT
}

