import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Integer[] integerArray = new Integer[]{1, 2, 3, 4, 5};
        List<Integer> list = Arrays.asList(integerArray);
        list.stream().forEach(value -> System.out.println(value));
    }
}
