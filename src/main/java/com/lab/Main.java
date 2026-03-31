package com.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Драйвер-клас із консольним меню.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Employee> employees = new ArrayList<>();
        boolean running = true;

        System.out.println("Вітаємо у системі обліку працівників!");

        while (running) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Створити нового працівника");
            System.out.println("2. Вивести інформацію про всіх працівників");
            System.out.println("3. Завершити роботу");
            System.out.print("Оберіть дію (1-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("  Введіть ім'я: ");
                        String firstName = scanner.nextLine();

                        System.out.print("  Введіть прізвище: ");
                        String lastName = scanner.nextLine();

                        System.out.print("  Введіть відділ: ");
                        String department = scanner.nextLine();

                        System.out.print("  Введіть посаду: ");
                        String position = scanner.nextLine();

                        System.out.print("  Введіть зарплату (грн): ");
                        double salary = Double.parseDouble(scanner.nextLine());

                        System.out.print("  Введіть стаж (роки): ");
                        int experience = Integer.parseInt(scanner.nextLine());

                        // Спроба створити об'єкт (тут спрацює валідація)
                        Employee emp = new Employee(firstName, lastName, position, salary, experience, department);
                        employees.add(emp);
                        System.out.println("Працівника успішно додано!");

                    } catch (NumberFormatException e) {
                        System.out.println("Помилка вводу: Очікувалось числове значення!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Помилка валідації: " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.println("\n  СПИСОК ПРАЦІВНИКІВ");
                    if (employees.isEmpty()) {
                        System.out.println("  [Список порожній]");
                    } else {
                        for (int i = 0; i < employees.size(); i++) {
                            System.out.printf("  %2d. %s%n", i + 1, employees.get(i));
                        }
                        System.out.printf("  Всього: %d працівник(ів)%n", employees.size());
                    }
                    break;

                case "3":
                    running = false;
                    System.out.println("Роботу завершено. На все добре!");
                    break;

                default:
                    System.out.println("Некоректний вибір. Введіть 1, 2 або 3.");
            }
        }
        scanner.close();
    }
}