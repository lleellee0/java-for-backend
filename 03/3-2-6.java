import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List list = new ArrayList<String>();

        list.add("public");       // ["public"]
        list.add("static");       // ["public", "static"]
        list.add("void");         // ["public", "static", "void"]

        // for 문으로 List를 순회할 수 있다.
        for (int i = 0; i < list.size(); i++) { // list.size()는 리스트의 크기를 반환한다.
            System.out.println(list.get(i));    // i번째 요소가 출력된다.
        }

        list.remove(1);     // 1번째 요소인 "static"이 제거된다. -> ["public", "void"]
        int voidIndex = list.indexOf("void");   // void의 인덱스인 1이 반환된다.
        System.out.println("void의 index = " + voidIndex);
    }
}
