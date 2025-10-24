package practice_4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // === Задание 1 и 2: Triangle с вводом ===
        System.out.println("=== Треугольник ===");
        try {
            System.out.print("Введите сторону 1: ");
            double s1 = scanner.nextDouble();
            System.out.print("Введите сторону 2: ");
            double s2 = scanner.nextDouble();
            System.out.print("Введите сторону 3: ");
            double s3 = scanner.nextDouble();
            System.out.print("Введите цвет: ");
            String color = scanner.next();
            System.out.print("Закрашен? (true/false): ");
            boolean filled = scanner.nextBoolean();

            Triangle triangle = new Triangle(s1, s2, s3);
            triangle.setColor(color);
            triangle.setFilled(filled);

            System.out.println("Площадь: " + triangle.getArea());
            System.out.println("Периметр: " + triangle.getPerimeter());
            System.out.println("Цвет и заливка: " + triangle.toString());
            System.out.println("Закрашен: " + triangle.isFilled());

        } catch (IllegalTriangleException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Некорректный ввод.");
            scanner.nextLine(); // очистка буфера при ошибке
        }

        // === Задание 3: max() для кругов и прямоугольников ===
        System.out.println("\n=== Сравнение объектов через GeometricObject.max() ===");
        Circle c1 = new Circle(3.0);
        Circle c2 = new Circle(5.0);
        Rectangle r1 = new Rectangle(4, 5);
        Rectangle r2 = new Rectangle(3, 6);

        GeometricObject maxCircle = GeometricObject.max(c1, c2);
        GeometricObject maxRect = GeometricObject.max(r1, r2);

        System.out.println("Больший круг (площадь): " + maxCircle.getArea());
        System.out.println("Больший прямоугольник (площадь): " + maxRect.getArea());

        // Сравнение круга и прямоугольника
        GeometricObject maxMixed = GeometricObject.max(c1, r1);
        System.out.println("Больший из круга и прямоугольника (площадь): " + maxMixed.getArea());

        // ComparableCircle — используем через GeometricObject
        ComparableCircle cc1 = new ComparableCircle(2.0);
        ComparableCircle cc2 = new ComparableCircle(4.0);
        GeometricObject maxCC = GeometricObject.max(cc1, cc2);
        System.out.println("Больший ComparableCircle (площадь): " + maxCC.getArea());

        System.out.println("\n=== Colorable объекты ===");
        GeometricObject[] objects;
        try {
            objects = new GeometricObject[]{
                    new Circle(2.0),
                    new Rectangle(3, 4),
                    new Square(5),
                    new Triangle(3, 4, 5),      // теперь в try
                    new Square(2.5)
            };
        } catch (IllegalTriangleException e) {
            System.out.println("Ошибка при создании треугольника в массиве: " + e.getMessage());
            return; // или создать массив без треугольника
        }

        for (GeometricObject obj : objects) {
            System.out.println("Площадь: " + obj.getArea());
            if (obj instanceof Colorable) {
                ((Colorable) obj).howToColor();
            }
        }

        scanner.close();
    }
}