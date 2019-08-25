import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class User {
    String loc; //The location of the user file
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    static LocalDateTime now = LocalDateTime.now();

    /**
     * Initializer
     * @Author Rana
     * @param user The user
     */
    User(String user) {
        loc=user;
    }

    /**
     * Changes the balance
     * @Author Rana
     * @param amount The amount it changes
     */
    void changeBal(float amount, String info) {
        try {
            now = LocalDateTime.now();
            File tempFile=new File("data"+File.separator+"users"+File.separator+"tempFile.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter("data"+File.separator+"users"+File.separator+"tempFile.txt"));
            {
                List<String> lines=Files.readAllLines(Paths.get("data"+File.separator+"users"+File.separator+loc+".txt"));
                float newBal=Float.parseFloat(lines.get(3))+amount;
                for(int i=0; i<lines.size(); i++) {
                    if(i==3) {
                        writer.write(newBal+"\n");
                    } else writer.write(lines.get(i)+"\n");
                }
                writer.write("(" + dtf.format(now) + ") "+info+amount+"\n");
                writer.flush();
                writer.close();
            }
            new File("data"+File.separator+"users"+File.separator+loc+".txt").delete();
            tempFile.renameTo(new File("data"+File.separator+"users"+File.separator+loc+".txt"));
            tempFile.delete();
        } catch(Exception e) {}
    }

    /**
     * @Author Aroon
     * Writes a line stating that the user is premium.
     */
    void userPremium() {
        try {
            File f = new File("data"+File.separator+"users"+File.separator+".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter("data"+File.separator+"users"+File.separator+".txt"));
            {
                String PremiumUser = "Premium";
                List<String> lines = Files.readAllLines(Paths.get("data"+File.separator+"users"+File.separator+loc+".txt"));
                for (int i = 0; i < lines.size(); i++) {
                    if (i == 2) {
                        writer.write(PremiumUser + "\n");
                    } else {
                        writer.write(lines.get(i) + "\n");
                    }
                }
                writer.flush();
                writer.close();
            }
            new File("data"+File.separator+"users"+File.separator+loc+".txt").delete();
            f.renameTo(new File("data"+File.separator+"users"+File.separator+loc+".txt"));
            f.delete();
        } catch(Exception e) {}
    }

    /**
     * Gets the balance
     * @Author Rana
     * @return Balance
     */
    float getBal() {
        try {
            return Float.parseFloat(Files.readAllLines(Paths.get("data"+File.separator+"users"+File.separator+loc+".txt")).get(3));
        } catch(Exception e) {}
        return 0;
    }

    /**
     * @Author Aroon
     * @return
     * Checks whether or not the user is premium.
     */
    public Boolean UserPremiumYes(){
        try {
            String YoN = (Files.readAllLines(Paths.get("data"+File.separator+"users"+File.separator+loc+".txt")).get(2));
            if(YoN.equals("Premium")){
                return true;
            } else{
                return false;
            }
        } catch (Exception e) {}
        return false;
    }

    /** @Author Shailen
     *  @return UUID
     */
    public String getID() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("data" + File.separator + "users" + File.separator + this.loc + ".txt"));
            String ID = lines.get(1);
            return ID;
        } catch (Exception var2) {
            return "Error";
        }
    }

    /**
     * @Author Rana
     * @return Location
     */
    public String getLoc() { return loc; }
}

