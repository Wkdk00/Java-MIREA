package practice_4;

import java.util.Date;

public abstract class GeometricObject implements Comparable<GeometricObject> {
    private String color = "white";
    private boolean filled;
    private Date dateCreated;

    protected GeometricObject() {
        dateCreated = new Date();
    }

    protected GeometricObject(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
        dateCreated = new Date();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return "цвет: " + color + ", закрашен: " + filled;
    }

    // Абстрактные методы
    public abstract double getArea();
    public abstract double getPerimeter();

    // Реализация Comparable: сравниваем по площади
    @Override
    public int compareTo(GeometricObject o) {
        return Double.compare(this.getArea(), o.getArea());
    }

    // Статический метод max
    public static GeometricObject max(GeometricObject g1, GeometricObject g2) {
        return (g1.compareTo(g2) >= 0) ? g1 : g2;
    }
}