import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class User {
    String loc;

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
            File tempFile=new File("data\\tempFile.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter("data\\tempFile.txt"));
            {
                List<String> lines=Files.readAllLines(Paths.get("data\\"+loc+".txt"));
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
            new File("data\\"+loc+".txt").delete();
            tempFile.renameTo(new File("data\\"+loc+".txt"));
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
            return Float.parseFloat(Files.readAllLines(Paths.get("data\\"+loc+".txt")).get(1));
        } catch(Exception e) {}
        return 0;
    }
}