import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class User {
    String loc; //The location of the user file

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
    void changeBal(float amount) {
        try {
            File tempFile=new File("data"+File.separator+"tempFile.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter("data"+File.separator+"tempFile.txt"));
            {
                List<String> lines=Files.readAllLines(Paths.get("data"+File.separator+loc+".txt"));
                float newBal=Float.parseFloat(lines.get(1))+amount;
                for(int i=0; i<lines.size(); i++) {
                    if(i==1) {
                        writer.write(newBal+"\n");
                    } else writer.write(lines.get(i)+"\n");
                }
                writer.write(amount+"\n");
                writer.flush();
                writer.close();
            }
            new File("data"+File.separator+loc+".txt").delete();
            tempFile.renameTo(new File("data"+File.separator+loc+".txt"));
            tempFile.delete();
        } catch(Exception e) {}
    }

    /**
     * Gets the balance
     * @Author Rana
     * @return Balance
     */
    float getBal() {
        try {
            return Float.parseFloat(Files.readAllLines(Paths.get("data"+File.separator+loc+".txt")).get(1));
        } catch(Exception e) {}
        return 0;
    }
}