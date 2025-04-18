package pl.blazejdrozd.zpo_lab_4;

public class SampleClass {
    private String name = "Default Name";
    private int count = 10;
    private boolean active = true;
    private String longText = "Loooooooooooooooooooooooooooooooooooooooong text";
    private double value = 123.45;
    private char letter = 'A';

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getLongText() { return longText; }
    public void setLongText(String longText) { this.longText = longText; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    public char getLetter() { return letter; }
    public void setLetter(char letter) { this.letter = letter; }
}