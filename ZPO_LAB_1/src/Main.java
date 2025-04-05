import java.util.function.*;

class Car {
    private String make;

    public Car(String make) {
        this.make = make;
    }

    public void printMake(UnaryOperator<String> nameProvider) {
        System.out.println(nameProvider.apply(this.make));
    }

    public String getMake(UnaryOperator<String> nameProvider) {
        return nameProvider.apply(this.make);
    }

    public Boolean isCrashable(Boolean bmwByPass) {
        if (bmwByPass) return false;
        return this.make.toLowerCase().equals("bmw");
    }
}

public class Main {
    public static void main(String[] args) {
        // 1. Odwołanie do konstruktora
        Function<String, Car> carMaker = Car::new;

        Car BMW = carMaker.apply("bmw");

        // 2. Prosty Supplier zwracający 1
        Supplier<Integer> oneSup = () -> 1;

        // 3. Wykorzystanie metody printMake i przekazanie do niej Unary Operator
        BMW.printMake((str) -> str.toUpperCase());


        // 4. Lambda w postaci bloku
        Consumer<Car> hugeMakePrinter = car -> {
            String make = car.getMake(str -> str.toUpperCase());

            System.out.println(make);
        };

        hugeMakePrinter.accept(BMW);

        // 5. Odwołanie do metody
        Predicate<Boolean> carCrasher = BMW::isCrashable;
        System.out.println(carCrasher.test(true));


    }
}