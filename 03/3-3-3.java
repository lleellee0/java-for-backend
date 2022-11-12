import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Integer[] integerArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> list = Arrays.asList(integerArray);

        List evenList = new ArrayList<Integer>();

        for (int i = 0; i < list.size(); i++) {
            Integer number = list.get(i);
            if (number % 2 == 0) { // 2로 나눴을 때의 나머지가 0이면 2의 배수다.
                evenList.add(number);
            }
        }

        for (int i = 0; i < evenList.size(); i++) {
            System.out.println(evenList.get(i));
        }
    }
}
