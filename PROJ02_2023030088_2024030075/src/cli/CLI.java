package cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import managers.*;
import model.accounts.BankAccount;
import model.bills.Bill;
import model.transactions.Deposit;
import model.transactions.Payment;
import model.transactions.Transfer;
import model.transactions.Withdrawal;
import model.user.*;
import model.user.User.UserType;
import storage.StorableList;

public class CLI {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        String filePath = "./data/";
        AccountManager.getInstance().loadAccounts(filePath, new StorableList<>());
        BillManager.getInstance().loadAllBills(filePath);
        StandingOrderManager.getInstance().loadAllOrders(filePath);
        StatementManager.getInstance().loadStatements(filePath, new StorableList<>());
        UserManager.getInstance().loadUsers(filePath, new StorableList<>());
        
        while (true) {
            try {
                System.out.println("0. Exit");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.print("Select: ");
                int option = Integer.parseInt(sc.nextLine());
                if (option == 0) {
                    AccountManager.getInstance().saveAccounts(filePath, false);
                    BillManager.getInstance().saveAllBills(filePath, false);
                    StandingOrderManager.getInstance().saveAllOrders(filePath, false);
                    StatementManager.getInstance().saveStatements(filePath, false);
                    UserManager.getInstance().saveUsers(filePath, false);
                    break;
                }
                else if (option == 1) login();
                else if (option == 2) register();
                else {
                    System.out.println("Not a valid option.");
                }
            } catch (Exception e) {
                System.out.println("Not a valid option; err" + e.getMessage());
            }
        }
        sc.close();
    }

    private static void register() {
        System.out.print("Username: ");
        String userName;
        while (true) {
            userName = sc.nextLine();
            if (!UserManager.getInstance().userNameExists(userName)){
                break;
            }
            System.out.println("Username is unavailable. Please select another one: ");
        }

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Legal Name: ");
        String legalName = sc.nextLine();

        System.out.println("Account type: ");
        System.out.println("1. Individual");
        System.out.println("2.Company");
        while (true) {
            System.out.println("Select: ");
            int option = Integer.parseInt(sc.nextLine());
            if(option == 1) {
                UserManager.getInstance().register(UserType.Individual, legalName, userName, password);
                break;
            } else if (option == 2) {
                UserManager.getInstance().register(UserType.Company, legalName, userName, password);
                break;
            }
        }
        main(null);
    }

    private static void login() {
        System.out.print("Username: ");
        String userName = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        User user = UserManager.getInstance().authenticate(userName, password);
        if (user != null) {
            if (user instanceof Admin) adminMenu((Admin) user);
            else if (user instanceof Company) businessMenu((Company) user);
            else individualMenu((Individual) user);
        } else {
            System.out.println("Invalid credentials\n");
        }
    }

    private static void individualMenu(Individual user) {
        while (true) {
            try {
                System.out.println("\nOverview");
                AccountManager.getInstance().printAccountsFor(user.getVAT());
                System.out.println("1. Withdraw");
                System.out.println("2. Deposit");
                System.out.println("3. Transfer");
                System.out.println("4. Pay Bill");
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1: executeWithdrawal(user); break;
                    case 2: executeDeposit(user); break;
                    case 3: executeTranfer(user); break;
                    case 4: executePayBill(user); break;
                    default: return;
                }
            }catch (Exception e) {
                System.out.println("Invalid choice. Please try again");
            }
        }
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    private static String getCurrentDate() {
        return java.time.ZonedDateTime.now()
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private static void executeWithdrawal(Individual user) {
        System.out.println("Enter IBAN: ");
        String iban = sc.nextLine();
        BankAccount targetIban = AccountManager.getInstance().getAccountMap().get(iban);
        System.out.println("Enter amount: ");
        float amount = Float.parseFloat(sc.nextLine());
        System.out.println("Enter reason (description): ");
        String description = sc.nextLine();
        Withdrawal withdrawal = new Withdrawal(generateId(), user.getVAT(), description, getCurrentDate(), targetIban, amount);
        TransactionManager.getInstance().executeTransaction(withdrawal);
        System.out.println("Withdrawal executed successfully.");
        System.out.println("New balance: " + targetIban.getBalance());
    }

    private static void executeDeposit(Individual user) {
        System.out.println("Enter IBAN: ");
        String iban = sc.nextLine();
        BankAccount targetIban = AccountManager.getInstance().getAccountMap().get(iban);
        System.out.println("Enter amount: ");
        float amount = Float.parseFloat(sc.nextLine());
        System.out.println("Enter reason (description): ");
        String description = sc.nextLine();
        Deposit deposit = new Deposit(generateId(), user.getVAT(), description, getCurrentDate(), targetIban, amount);
        TransactionManager.getInstance().executeTransaction(deposit);
        System.out.println("Deposit executed successfully.");
        System.out.println("New balance: " + targetIban.getBalance());
    }

    private static void executeTranfer(Individual user) {
        System.out.println("From IBAN: ");
        String fromIban = sc.nextLine();
        BankAccount fromAccount = AccountManager.getInstance().getAccountMap().get(fromIban);
        System.out.println("To IBAN: ");
        String toIban = sc.nextLine();
        BankAccount toAccount = AccountManager.getInstance().getAccountMap().get(toIban);
        System.out.println("Enter amount: ");
        float amount = Float.parseFloat(sc.nextLine());
        System.out.println("Enter reason (description): ");
        String description = sc.nextLine();
        Transfer transfer = new Transfer(generateId(), user.getVAT(), description, getCurrentDate(), fromAccount, toAccount, amount);
        TransactionManager.getInstance().executeTransaction(transfer);
        System.out.println("Transfer executed successfully.");
        System.out.println("New balance: " + fromAccount.getBalance());
    }

    private static void executePayBill(Individual user) {
        System.out.print("Bill ID: ");
        String billId = sc.nextLine();
        System.out.print("Amount: ");
        float amount = Float.parseFloat(sc.nextLine());
        System.out.println("Enter reason (description): ");
        String description = sc.nextLine();
        var tx = new Payment(generateId(), user.getVAT(), description, getCurrentDate(), billId, amount);
        TransactionManager.getInstance().executeTransaction(tx);
        System.out.println("Payment successful");
        System.out.println("New balance: " + AccountManager.getInstance().getAccountMap().get(user.getVAT()).getBalance());
    }

    private static void businessMenu(Company user) {
        while (true) {
            try {
                System.out.println("\nOverview");
                AccountManager.getInstance().printAccountsFor(user.getVAT());

                System.out.println("1. Load Issued Bills");
                System.out.println("2. Show Paid Bills");

                int option = Integer.parseInt(sc.nextLine());

                switch(option) {
                    case 1: BillManager.getInstance().printIssuedBillsFor(user.getVAT()); break;
                    case 2: BillManager.getInstance().printPaidBillsFor(user.getVAT()); break;
                    default: return;
                }
            } catch(Exception e) {
                System.out.println("Invalid choice. Please try again");
            }
        }
    }

    private static void adminMenu(Admin user) {
        while (true) {
            try {
                System.out.println("\nAdmin Menu");
                System.out.println("1. Customers");
                System.out.println("2. Bank Accounts");
                System.out.println("3. Company Bills");
                System.out.println("4. List Standing Orders");
                System.out.println("5. Pay Customer's Bills");
                System.out.println("6. Simulate Time Passing");

                int option = Integer.parseInt(sc.nextLine());

                switch(option) {
                    case 1:
                        System.out.println("1. Show Customers");
                        System.out.println("2. Show Customer Details");
                        System.out.println("3. Return");
                        
                        int opt = Integer.parseInt(sc.nextLine());

                        switch(opt) {
                            case 1: UserManager.getInstance().printAllUsers(); break;
                            case 2: showCustomerDetails(); break;
                            case 3: return; 
                        }
                    case 2:
                        System.out.println("1. Show Bank Accounts");
                        System.out.println("2. Show Bank Account Info");
                        System.out.println("3. Show Bank Account Statements");
                        System.out.println("4. Return");

                        int optt = Integer.parseInt(sc.nextLine());

                        switch(optt) {
                            case 1: AccountManager.getInstance().printAllAccounts(); break;
                            case 2: showAccountInfo(); break;
                            case 3: showAccountStatements(); break;
                            case 4: return; 
                        }
                    case 3:
                        System.out.println("1. Show Issued Bills");
                        System.out.println("2. Show Paid Bills");
                        System.out.println("3. Load Company Bills");
                        System.out.println("4. Return");

                        int opttt = Integer.parseInt(sc.nextLine());

                        switch(opttt) {
                            case 1: BillManager.getInstance().printIssuedBills(); break;
                            case 2: BillManager.getInstance().printPaidBills(); break;
                            case 3: loadCompanyBills(); break;
                            case 4: return;
                        }
                    case 4: StandingOrderManager.getInstance().printAllOrders(); break;
                    case 5: payCustomerBill(); break;
                    case 6: simulateTime(); break;
                }
            } catch (Exception e) {
                System.out.println("Invalid choice. Please try again");
            }
        }
    }

    ////////////////////////// ADMIN ACTIONS //////////////////////////

    private static void showCustomerDetails() {
        System.out.print("Enter VAT Number: ");
        String vatNumber = sc.nextLine();
        UserManager.getInstance().printCustomerDetails(vatNumber);
    }

    private static void showAccountInfo() {
        System.out.print("Enter IBAN: ");
        String iban = sc.nextLine();
        AccountManager.getInstance().printAccountDetailsForIban(iban);
    }

    private static void showAccountStatements() {
        System.out.print("Enter IBAN: ");
        String iban = sc.nextLine();
        StatementManager.getInstance().printStatementsForIban(iban);
    }

    private static void loadCompanyBills() {
        System.out.println("Enter date-file path: ");
        String filePath = sc.nextLine();
        BillManager.getInstance().loadIssued(filePath, new StorableList<>());
    }

    private static void payCustomerBill(){
        System.out.println("Enter Customer VAT Number: ");
        
        String vatNumber = sc.nextLine();
        List<Bill> pending = BillManager.getInstance().getPendingBills(vatNumber);
        if (pending.isEmpty()) {
            System.out.println("No pending bills.");
            return;
        }
        for (int i = 0; i < pending.size(); i++) {
            Bill bill = pending.get(i);
            System.out.println((i+1)+") " + bill.getRf() + " - " + bill.getAmount() + "due " + bill.getDueDate());
        }
        System.out.print("Choose bill: ");
        int option = Integer.parseInt(sc.nextLine()) - 1;
        BillManager.getInstance().payBill(pending.get(option));
        System.out.println("Bill paid successfully.");
    }

    private static void simulateTime() {
        System.out.println("Enter which date you want to simulate each day to (YYYY-MM-DD): ");
        String targetDateString = sc.nextLine();
        LocalDate end = LocalDate.parse(targetDateString);
        AccountManager accManager = AccountManager.getInstance();
        StandingOrderManager standingOrderManager = StandingOrderManager.getInstance();

        for(LocalDate date = LocalDate.now(); date.isBefore(end) || date.isEqual(end); date = date.plusDays(1)) {
            accManager.applyDailyInterest();
            if (date.getDayOfMonth() == 1) {
                accManager.applyMonthlyFees();
            }
            standingOrderManager.executeDue(date);
        }
    } 

    
}
