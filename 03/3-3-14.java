import java.util.Optional;

public class Main {
    private static Optional<String> getSomeString() {
        return Optional.ofNullable("public static void");
    }

    public static void main(String[] args) {
        Optional<String> str = getSomeString();

        str.ifPresent((string) -> System.out.println(string.toUpperCase()));
    }
}
