public class Main {
    public static void main(String[] args) {
        // 익명 클래스 코드
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });

        // 람다 표현식 코드
        list.sort((Comparator<String>) (str1, str2) -> str1.compareTo(str2));
    }
}
