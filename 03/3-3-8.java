import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] lowercaseArray = new String[]{"public", "static", "void"};
        List<String> lowercaseList = Arrays.asList(lowercaseArray);
        List<String> uppercaseList = lowercaseList.stream()
                .map(value -> value.toUpperCase()).toList();
        uppercaseList.stream().forEach(value -> System.out.println(value));
    }
}
