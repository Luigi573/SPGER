package mx.uv.fei;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("test/dependencies/resources/DatabaseAccess.properties");
            System.out.println(path);
    }
}
