import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class readInput {

    public ArrayList<String> input;
    File file;
    BufferedReader bufferedReader;

    public void readFile(String pathname) throws IOException {
        file = new File(pathname);
        bufferedReader = new BufferedReader(new FileReader(file));
        input = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {

            //satır başına 10 column geçilirse diğer satıra atlama (format) kontrolü gerekli

            input.add(line);
        }
    }
}

