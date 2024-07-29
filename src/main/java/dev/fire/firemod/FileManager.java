package dev.fire.firemod;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileManager {
    public static Path Path() {
        Path path = Firemod.MC.runDirectory.toPath().resolve(Firemod.MOD_ID);
        path.toFile().mkdir();
        return path;
    }

    public static Path writeFile(String fileName, String content) throws IOException {
        return writeFile(fileName, content, false);
    }

    private static Path writeFile(String fileName, String content, boolean doCharSet) throws IOException {
        Path path = Path().resolve(fileName);
        Files.deleteIfExists(path);
        Files.createFile(path);
        Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);

        return path;
    }

    public static String readFile(String fileName, Charset charset) throws IOException {
        return Files.readString(Path().resolve(fileName), charset);
    }

    public static boolean exists(String fileName) {
        return Files.exists(Path().resolve(fileName));
    }

    public static String readFile(String fileName) throws IOException {
        return readFile(fileName, StandardCharsets.UTF_8);
    }
}