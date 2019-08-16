import java.io.*;
import java.util.Scanner;

public class Main {

    static User loggedIn;
    static Scanner sc=new Scanner(System.in);

    public static void main(String[] args) {
        startScreen();
    }

    /**
     * @Author Rana
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
                System.out.println("1 to deposit, 2 to withdraw, 3 to logout, 4 to exit");
                switch(sc.nextLine()) {
                    case "1":
                        deposit();
                        break;
                    case "2":
                        withdraw();
                        break;
                    case "3":
                        loggedIn=null;
                        break;
                    case "4":
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
        while(true) {
            username=doubleVerify("username", "Usernames");
            File[] listOfFiles=new File("data").listFiles();
            boolean notExist=true;
            for(int i=0; i<listOfFiles.length; i++) {
                if(listOfFiles[i].toString().equals("data"+File.separator+username+".txt")) {
                    notExist=false;
                }
            }
            if(notExist) break;
            System.out.println("Username taken, please enter another");
        }
        String password=doubleVerify("password", "Passwords");
        writeToFile("data"+File.separator+username+".txt", password+"\n0");
    }

    /**
     * @Author Rana
     * Runs the entire login procedure
     */
    public static void login() {
        String user;
        System.out.println("Please enter your username");
        user=sc.nextLine();
        File[] listOfFiles = new File("data").listFiles();
        boolean exists=false;
        for(int i=0; i<listOfFiles.length; i++) {
            if(listOfFiles[i].toString().equals("data"+File.separator+user+".txt")) {
                exists=true;
            }
        }
        if(exists) {
            try {
                BufferedReader reader=new BufferedReader(new FileReader("data"+File.separator+user+".txt"));
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
                loggedIn.changeBal(deposit);
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
            loggedIn.changeBal(-withdraw);
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
}