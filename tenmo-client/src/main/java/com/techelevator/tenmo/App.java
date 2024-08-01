package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.UserService;

import java.math.BigDecimal;

public class  App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AuthenticatedUser currentUser;
    private UserService userService=null;

    private Account currentBalance;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        userService=new UserService(API_BASE_URL,currentUser);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }


    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        // TODO Auto-generated method stub
        userService.printUserBalance();
    }

    private void viewTransferHistory() {
        // TODO Auto-generated method stub
        userService.printTransferSentAndReceived();
        Transfer transferToGetDetail=consoleService.promptForTransferId("Please enter transfer ID to view details: ", userService.getAllTransferSent(), userService.getAllTransferReceived());
        if (transferToGetDetail==null){
            System.out.println("No transfer found.");
        } else{
            userService.printTransferDetails(transferToGetDetail);
        }

    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub
        userService.printPendingTransfer();
        Transfer transferToUpdate = consoleService.promptForPendingTransferId("Please enter transfer ID to approve/reject: ",userService.getPendingTransferRequestsWhenSender());
        if(transferToUpdate!=null){
            int optionSelected=consoleService.promptForOption("Please choose an option:");
            userService.updatePendingRequest(transferToUpdate,optionSelected);
        }else{
            System.out.println("You can't process any pending request");
        }
    }

    private void sendBucks() {
        // TODO Auto-generated method stub
        userService.printUserList();
        User userToSentTo = null;

        int userIdInput = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel):");

        if (userIdInput == 0) {
            System.out.println("Operation cancelled by user.");
            return;
        }

        userToSentTo = getUserById(userIdInput, userService.getRegisteredUser());

        if (userToSentTo == null) {
            System.out.println("Invalid user ID. Operation cancelled.");
            sendBucks();
            return;
        } else if (userToSentTo.getId() == currentUser.getUser().getId()) {
            System.out.println("You cannot send TEBucks to yourself.");
            return;
        }

        BigDecimal amount = consoleService.promptForBigDecimal("Enter amount:");
        BigDecimal zero = new BigDecimal(0);

        if (amount.compareTo(zero) <= 0) {
            System.out.println("Amount must be greater than $0.00");
            return;
        } else if (amount.compareTo(userService.getAuthenticatedUserBalance().getCurrentBalance()) > 0) {
            System.out.println("Insufficient funds");
            return;
        }

        System.out.println("You wanna send TE Bucks to " + userToSentTo.getUsername() + " amount:" + amount);
        Transfer transfer = userService.sendTeBucks(currentUser.getUser(), userToSentTo, amount);

        if (transfer != null) {
            System.out.println(userToSentTo.getUsername() + " successfully received " + transfer.getTransferType().getName() + " of " + transfer.getAmount() + " Bucks");
        } else {
            System.out.println("Failed to send request");
        }
    }

    private User getUserById(int userId, User[] userList) {
        for (User user : userList) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    private void requestBucks() {
        // TODO Auto-generated method stub
        userService.printUserList();
        User userToRequestFrom = null;
        int userIdInput = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel):");

        if (userIdInput == 0) {
            System.out.println("Operation cancelled by user.");
            return;
        }

        userToRequestFrom = getUserById(userIdInput, userService.getRegisteredUser());

        if (userToRequestFrom == null) {
            System.out.println("Invalid user ID. Operation cancelled.");
            requestBucks();
            return;
        }

        if (userToRequestFrom.getId() == currentUser.getUser().getId()) {
            System.out.println("You cannot request TEBucks from yourself.");
        } else {
            BigDecimal amount = consoleService.promptForBigDecimal("Enter amount:");
            BigDecimal zero = new BigDecimal(0);
            if (amount.compareTo(zero) <= 0) {
                System.out.println("Request must positive and greater than zero.");
            } else {
                System.out.println("You want to request TE Bucks from " + userToRequestFrom.getUsername() + " amount:" + amount);
                userService.requestTeBucks(userToRequestFrom, amount);
            }
        }
    }
}
