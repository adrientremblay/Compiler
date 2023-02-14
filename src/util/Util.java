package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {

    public static String readFileAsString(String fileName) {
        try {
            return new String(Files.readAllBytes(Path.of(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
