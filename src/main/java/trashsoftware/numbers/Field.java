package trashsoftware.numbers;

public class Field<A extends FieldAble, B extends FieldExtendable> implements FieldAble {

    protected A a;
    protected A b;
    protected B extension;

    public Field(A a, A b, B extension) {
        this.a = a;
        this.b = b;
        this.extension = extension;
    }

    @Override
    public String toString() {
        return a + "+" + b + extension;
    }
}
