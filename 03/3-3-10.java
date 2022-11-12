public class Main {
    private static String getSomeString() {
        return null; // 이 메서드는 항상 null을 반환한다.
    }

    public static void main(String[] args) {
        String isThisNull = getSomeString();

        System.out.println(isThisNull.toUpperCase());
    }
}
