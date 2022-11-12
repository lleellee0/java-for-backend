import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List list = new ArrayList<Integer>();
        // <Integer>는 ArrayList에 Integer 타입이 저장될 수 있다는 것을 의미한다.

        list.add(1);
        list.add(2);
        list.add(3);

        System.out.println(list.get(1));
    }
}
