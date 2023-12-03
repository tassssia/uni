package task4;

public class SampleClass extends SuperClass implements SampleInterface {

    private int field1;
    private String field2;

    public SampleClass(int field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public int getField1() {
        return field1;
    }
    public void setField1(int field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }
    public void setField2(String field2) {
        this.field2 = field2;
    }
}