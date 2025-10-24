package practice_4;

import java.util.InputMismatchException;
import java.util.Scanner;

public class task1 {
    public static void main(String[] args) {
        String[] months = {"январь", "февраль", "март", "апрель", "май",
                "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
        int[] dom = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Введите целое число от 1 до 12: \n");
                int monthNumber = scanner.nextInt();

                if (monthNumber < 1 || monthNumber > 12) {
                    System.out.println("Недопустимое число");
                    continue;
                }

                if (monthNumber == 2) {
                    System.out.print("Введите год: \n");
                    int year = scanner.nextInt();
                    if (year % 4 == 0) {
                        System.out.println("Вы выбрали високосный год!");
                        System.out.println("Месяц: " + months[monthNumber - 1]);
                        System.out.println("Количество дней: " + (dom[monthNumber - 1] + 1));
                    } else {
                        System.out.println("Вы выбрали невисокосный год!");
                        System.out.println("Месяц: " + months[monthNumber - 1]);
                        System.out.println("Количество дней: " + dom[monthNumber - 1]);
                    }
                } else {
                    System.out.println("Месяц: " + months[monthNumber - 1]);
                    System.out.println("Количество дней: " + dom[monthNumber - 1]);
                }
                break;

            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число!");
                scanner.next(); // Очищаем некорректный ввод
            }
        }

        scanner.close();
    }
}