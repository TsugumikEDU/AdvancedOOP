import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Zadanie 3
        var arr = new ArrayList<Integer>();

        arr.add(10);
        arr.add(3);
        arr.add(80);

        System.out.println("== Zadanie 3");
        System.out.println(arr.stream().min(Integer::compareTo).get());

        // Zadanie 4
        System.out.println("== Zadanie 4");
        arr.stream().filter(number -> number % 2 == 0).forEach(System.out::println);

        // Zadanie 5
        var personArr = new ArrayList<Person>();
        personArr.add(new Person("Maciej", 20));
        personArr.add(new Person("Damian", 21));
        personArr.add(new Person("Jakub", 22));

        System.out.println("== Zadanie 5");
        personArr.stream().sorted(
                Comparator.comparing(Person::getNick).
                        thenComparing(Person::getAge)).
                        forEach(System.out::println);

        // Zadanie 6
        var punktXYZarr = new ArrayList<PunktXYZ>();
        punktXYZarr.add(new PunktXYZ(1, 10, 15));
        punktXYZarr.add(new PunktXYZ(2, 20, 30));
        punktXYZarr.add(new PunktXYZ(0, -10, 80));

        System.out.println("== Zadanie 6");
        List<PunktXY> punkty = punktXYZarr.stream().map(punktXYZ -> new PunktXY(punktXYZ.x, punktXYZ.y)).toList();

        for (PunktXY punkt : punkty) {
            System.out.println(punkt);
        }

        // Zadanie 7
        var eagles = new Group("Eagles", personArr);

        var bikersMembers = new ArrayList<Person>();
        bikersMembers.add(new Person("Stefan", 20));
        bikersMembers.add(new Person("Grzegorz", 21));
        bikersMembers.add(new Person("Aleksander", 22));

        var bikers = new Group("Bikers", bikersMembers);

        List<Group> groups = Arrays.asList(eagles, bikers);

        List<Person> allMembers = groups.stream()
                .flatMap(group -> group.getMembers().stream())
                .collect(Collectors.toList());

        System.out.println("== Zadanie 7");
        System.out.println(allMembers);

        // Zadanie 8
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println("== Zadanie 8");
        System.out.println("Suma:");
        System.out.println(integerList.stream().reduce(Integer::sum).get());

        System.out.println("Mnożenie:");
        System.out.println(integerList.stream().reduce(1, (a, b) -> a * b));

        // Zadanie 9
        var uuids = new ArrayList<String>();

        for (int i = 0; i < 1000000; i++) {
            uuids.add(UUID.randomUUID().toString());
        }

        test1(uuids);
        test2(uuids);
    }

    public static void test1(ArrayList<String> arr) {
        arr.stream().sorted().toList();
    }

    public static void test2(ArrayList<String> arr) {
        arr.parallelStream().sorted().toList();
    }
}