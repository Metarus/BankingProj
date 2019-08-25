import java.io.*;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    static User loggedIn;
    static Scanner sc=new Scanner(System.in);

    public static void main(String[] args) {
        startScreen();
    }

    /**
     * @Author Rana
     * @Author Aroon
     * Has everything within it
     */
    public static void startScreen() {
        boolean running=true;
        while(running) {
            if(loggedIn==null) {
                System.out.println("1 to login, 2 to signup, 3 to exit");
                switch(sc.nextLine()) {
                    case "1":
                        login();
                        break;
                    case "2":
                        signup();
                        break;
                    case "3":
                        running=false;
                        break;
                    default:
                        System.out.println("Invalid entry");
                        break;
                }
            } else {
                System.out.println("LDS Financials Banking Services");
                if(loggedIn.UserPremiumYes()) {
                    System.out.println("Premium User");
                }
                System.out.println("1 to deposit, 2 to withdraw, 3 to transfer, 4 to register for premium, 5 to read account history, 6 to submit feedback, 7 to log out, 8 to exit");
                switch(sc.nextLine()) {
                    case "1":
                        deposit();
                        break;
                    case "2":
                        withdraw();
                        break;
                    case "3":
                        transfer();
                        break;
                    case "4":
                        Premium();
                        break;
                    case "5":
                        readAccountHistory();
                        break;
                    case "6":
                        System.out.println("Please enter your feedback:");
                        String Feedback = sc.nextLine();
                        submitFeedback(loggedIn.getLoc(),Feedback);
                        break;
                    case "7":
                        loggedIn=null;
                        break;
                    case "8":
                        running=false;
                        System.out.println("Exiting");
                        break;
                    default:
                        System.out.println("Invalid entry");
                        break;
                }
            }
        }
    }

    /**
     * @Author Rana
     * Runs the entire signup procedure
     */
    public static void signup() {
        String username;
        String uniqueID = UUID.randomUUID().toString();
        while(true) {
            username=doubleVerify("username", "Usernames");
            File[] listOfFiles=new File("data"+File.separator+"users").listFiles();
            boolean notExist=true;
            for(int i=0; i<listOfFiles.length; i++) {
                if(listOfFiles[i].toString().equals("data"+File.separator+"users"+File.separator+username+".txt")) {
                    notExist=false;
                }
            }
            if(notExist) break;
            System.out.println("Username taken, please enter another");
        }
        String password=doubleVerify("password", "Passwords");
        writeToFile("data"+File.separator+"users"+File.separator+username+".txt", password+"\n"+uniqueID+"\nNot Premium\n0");
    }

    /**
     * @Author Rana
     * Runs the entire login procedure
     */
    public static void login() {
        String user;
        System.out.println("Please enter your username");
        user=sc.nextLine();
        File[] listOfFiles = new File("data"+File.separator+"users").listFiles();
        boolean exists=false;
        for(int i=0; i<listOfFiles.length; i++) {
            if(listOfFiles[i].toString().equals("data"+File.separator+"users"+File.separator+user+".txt")) {
                exists=true;
            }
        }
        if(exists) {
            try {
                BufferedReader reader=new BufferedReader(new FileReader("data"+File.separator+"users"+File.separator+user+".txt"));
                System.out.println("Enter your password");
                if(reader.readLine().equals(sc.nextLine())) {
                    loggedIn=new User(user);
                } else {
                    System.out.println("Wrong password");
                }
                reader.close();
            } catch(Exception e) {}
        } else System.out.println("No user named "+user+" exists");
    }

    /**
     * @Author Rana
     * Asks for info to deposit
     */
    public static void deposit() {
        System.out.println("Please enter how much you'd like to deposit");
        float deposit;
        try {
            deposit=Float.parseFloat(sc.nextLine());
            if(deposit>0) {
                loggedIn.changeBal(deposit, "Deposit ");
                System.out.println("You now have "+loggedIn.getBal());
            } else {
                System.out.println("Please enter a positive value");
            }
        } catch(Exception e) {
            System.out.println("Please enter a valid number");
        }
    }

    /**
     * @Author Rana
     * Asks info for withdraw
     */
    public static void withdraw() {
        System.out.println("Please enter how much you'd like to withdraw");
        float withdraw;
        try {
            withdraw=Float.parseFloat(sc.nextLine());
            if(withdraw>0) {
            loggedIn.changeBal(-withdraw, "Withdraw ");
            System.out.println("You now have "+loggedIn.getBal());
            } else {
                System.out.println("Please enter a positive value");
            }
        } catch(Exception e) {
            System.out.println("Please enter a valid number");
        }
    }

    /**
     * @Author Rana
     */
    public static void transfer() {
        System.out.println("Please enter the username to whom you'd like to transfer funds");
        User transferToUser=null;
        while(true) {
            String transferTo = sc.nextLine();
            File[] listOfFiles = new File("data"+File.separator+"users").listFiles();
            boolean foundUser=false;
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].toString().equals("data"+File.separator+"users"+File.separator+transferTo+".txt")) {
                    transferToUser=new User(transferTo);
                    foundUser=true;
                }
            }
            if(foundUser) {
                break;
            } else System.out.println("User does not exist");
        }
        System.out.println("Please enter the unique ID of the user");
        if(!sc.nextLine().equals(transferToUser.getID())) {
            System.out.println("Wrong UUID");
        } else {
            System.out.println("Please enter how much you'd like to transfer");
            float transfer;
            try {
                transfer = Float.parseFloat(sc.nextLine());
                if (transfer > 0) {
                    loggedIn.changeBal(-transfer, " ->" + transferToUser.getLoc() + " ");
                    transferToUser.changeBal(transfer, " " + loggedIn.getLoc() + "->");
                    System.out.println("Transfer complete! You now have " + loggedIn.getBal());
                } else {
                    System.out.println("Please enter a positive value");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    /**
     * @Author Aroon
     * Runs through process for setting up premium account
     */
    public static void Premium() {
        float PremiumCost = 200;
        System.out.println("Registering for a premium account will cost you $200.");
        System.out.println("This money will be deducted from your account. Are you sure?");
        System.out.println("Press 1 to confirm, press 2 to cancel.");
        String confirm = sc.nextLine();
        switch(confirm){
            case "1":
                loggedIn.changeBal(-PremiumCost, "Premium ");
                loggedIn.userPremium();;
                System.out.println("You have successfully signed up for a premium account!");
                break;
            case "2":
                break;
        }
    }

    /**
     * @Author Aroon
     * @param user
     * @param feedback
     * A function which is used to submit feedback.
     */
    public static void submitFeedback(String user, String feedback){
        File[] listOfFiles = new File("data").listFiles();
        boolean exists = false;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].toString().equals("data"+File.separator+"feedback"+File.separator+user+"Feedback.txt")) {
                exists = true;
            }
        }
        if(exists){
            appendToFile("data"+File.separator+"feedback"+File.separator+user+"Feedback.txt", feedback+"\n");
        }else{
            writeToFile("data"+File.separator+"feedback"+File.separator+user+"Feedback.txt", feedback+"\n");
        }
    }

    /**
     * @Author Shailen
     * This function prints out all of the User's Account history
     */
    public static void readAccountHistory() {
        System.out.println("Account History of " + loggedIn.getLoc() + ":");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data" + File.separator + "users" + File.separator + loggedIn.getLoc() + ".txt"));
            String line = reader.readLine();
            int lineNum=0;
            while(line != null) {
                if(lineNum > 3) {
                    System.out.println(line);
                }
                //readnextline
                line = reader.readLine();
                lineNum++;
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author Rana
     * @param info Input
     * @param infoP Input plural
     * @return The input if verified
     * Double verifies a string and returns it
     */
    public static String doubleVerify(String info, String infoP) {
        String var;
        while(true) {
            System.out.println("Type your "+info);
            var=sc.nextLine();
            System.out.println("Please type it again to verify");
            if(var.equals(sc.nextLine())) {
                break;
            } else System.out.println(infoP+" are different. Please try again");
        }
        return var;
    }

    /**
     * @Author Rana
     * @param file File
     * @param write Being written
     * A function to write stuff to files
     */
    public static void writeToFile(String file, String write) {
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter(file));
            writer.write(write);
            writer.flush();
            writer.close();
        } catch(Exception e) {
            System.out.println("Error has occurred");
        }
    }

    /**
     * @Author Aroon
     * @param user
     * @param fd
     * A function which appends to a file. Created specifically for feedback submission.
     */
    public static void appendToFile(String user, String fd){
        try {
            File file1 = new File(user);
            FileWriter fr = new FileWriter(file1, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(fd + "\n");

            br.close();
            fr.close();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Error has occurred");
        }
    }
}