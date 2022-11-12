import java.util.Optional;

public class Main {
    private static Optional<String> getSomeString() {
        return Optional.empty(); // null을 반환하는 것이 아니라 비어있는 Optional을 반환한다.
    }

    public static void main(String[] args) {
        Optional<String> isThisNull = getSomeString();

        isThisNull.ifPresent(str -> System.out.println(str.toUpperCase()));
    }
}
