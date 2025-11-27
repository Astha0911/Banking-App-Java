import java.util.List;

public interface BankService {
    Account createAccount(String name, String email, String phone, String type, double initialDeposit);
    void deposit(int acc, double amt);
    void withdraw(int acc, double amt);
    void transfer(int from, int to, double amt);
    List<String> getStatement(int acc);
    java.util.List<Account> listAll();
}
