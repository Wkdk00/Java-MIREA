package practice_4;

public class ComparableCircle extends Circle {
    public ComparableCircle() {
        super();
    }

    public ComparableCircle(double radius) {
        super(radius);
    }

    public int compareByRadius(ComparableCircle other) {
        return Double.compare(this.getRadius(), other.getRadius());
    }
}