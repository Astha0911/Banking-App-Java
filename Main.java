import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final BankService service = new BankApp();

    public static void main(String[] args) {
        System.out.println("=== Banking App (3-file version) ===");

        boolean running = true;
        while (running) {
            menu();
            String choice = sc.nextLine();
            try {
                switch (choice) {
                    case "1": openAccount(); break;
                    case "2": deposit(); break;
                    case "3": withdraw(); break;
                    case "4": transfer(); break;
                    case "5": statement(); break;
                    case "6": listAccounts(); break;
                    case "0": running = false; break;
                    default: System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        System.out.println("Goodbye!");
    }

    private static void menu() {
        System.out.println("\n1. Open Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. View Statement");
        System.out.println("6. List All Accounts");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private static void openAccount() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Type (SAVINGS/CURRENT): ");
        String type = sc.nextLine();
        System.out.print("Initial deposit: ");
        double dep = Double.parseDouble(sc.nextLine());

        var acc = service.createAccount(name, email, phone, type, dep);
        System.out.println("Created: " + acc);
    }

    private static void deposit() {
        System.out.print("Account#: ");
        int acc = Integer.parseInt(sc.nextLine());
        System.out.print("Amount: ");
        double amt = Double.parseDouble(sc.nextLine());

        service.deposit(acc, amt);
        System.out.println("Deposited.");
    }

    private static void withdraw() {
        System.out.print("Account#: ");
        int acc = Integer.parseInt(sc.nextLine());
        System.out.print("Amount: ");
        double amt = Double.parseDouble(sc.nextLine());

        service.withdraw(acc, amt);
        System.out.println("Withdrawn.");
    }

    private static void transfer() {
        System.out.print("From Acc#: ");
        int a = Integer.parseInt(sc.nextLine());
        System.out.print("To Acc#: ");
        int b = Integer.parseInt(sc.nextLine());
        System.out.print("Amount: ");
        double amt = Double.parseDouble(sc.nextLine());

        service.transfer(a, b, amt);
        System.out.println("Transfer done.");
    }

    private static void statement() {
        System.out.print("Account#: ");
        int acc = Integer.parseInt(sc.nextLine());

        List<String> s = service.getStatement(acc);
        System.out.println("\n--- Statement ---");
        s.forEach(System.out::println);
    }

    private static void listAccounts() {
        var list = service.listAll();
        list.forEach(System.out::println);
    }
}
