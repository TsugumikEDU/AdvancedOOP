public class Person {
    private String nick;
    private int age;

    Person(String nick, int age) {
        this.nick = nick;
        this.age = age;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return nick + ": " + age;
    }
}
