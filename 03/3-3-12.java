import java.util.Optional;

public class Main {
    private static Optional<String> getSomeString() {
        return Optional.ofNullable("public static void");
    }

    public static void main(String[] args) {
        Optional<String> isThisNull = getSomeString();

        isThisNull.ifPresent(str -> System.out.println(str.toUpperCase())); // PUBLIC STATIC VOID가 출력된다.
    }
}
