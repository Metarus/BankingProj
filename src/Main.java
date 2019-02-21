import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) {
        startScreen();
    }
    public static void startScreen() {
        boolean login=true;
        while(login) {
            System.out.println("1 to login, 2 to signup");
            switch(sc.nextLine()) {
                case "1":
                    login();
                    login=false;
                    break;
                case "2":
                    signup();
                    break;
                default:
                    System.out.println("Invalid entry");
                    break;
            }
        }
    }
    public static void signup() {
        String username;
        while(true) {
            username=doubleVerify("username", "Usernames");
            File[] listOfFiles = new File("data").listFiles();
            boolean notExist=true;
            for(int i=0; i<listOfFiles.length; i++) {
                if(listOfFiles[i].toString().equals("data\\"+username+".txt")) {
                    notExist=false;
                }
            }
            if(notExist) break;
            System.out.println("Username taken, please enter another");
        }
        String password=doubleVerify("password", "Passwords");
        writeToFile("data/"+username+".txt", password);
    }
    public static void login() {
        //todo
    }
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