import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Integer[] integerArray = new Integer[]{1, 1, 1, 1, 2, 2, 2, 3, 3, 4};
        List<Integer> list = Arrays.asList(integerArray);
        List<Integer> distinctList = list.stream().distinct().toList();
        distinctList.stream().forEach(value -> System.out.println(value));
    }
}
