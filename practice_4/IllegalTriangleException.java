package practice_4;

public class IllegalTriangleException extends Exception {
    public IllegalTriangleException() {
        super("Недопустимые стороны треугольника: сумма любых двух сторон должна быть больше третьей");
    }

    public IllegalTriangleException(String message) {
        super(message);
    }
}
