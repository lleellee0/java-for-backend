public interface Car {}

public class Sonata implements Car {}

public class K5 implements Car {}

public class Main {
    public static void main(String[] args) {
        Car car1 = new Sonata();
        Car car2 = new K5();
    }
}
