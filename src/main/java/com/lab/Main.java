package com.lab;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Головний клас програми для керування списком працівників.
 * Реалізує консольний інтерфейс, обробку винятків та поліморфізм.
 */
public class Main {
    /**
     * Точка входу в програму.
     * Використовується ArrayList для зберігання об'єктів різних підкласів.
     */
    public static void main(String[] args) {
        // Згідно з завданням №7, використовуємо ArrayList замість класу Company
        List<Employee> employees = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            System.out.println("Вітаємо у системі обліку компанії (Лабораторна №7)!");

            while (true) {
                System.out.println("\n--- МЕНЮ ---");
                System.out.println("1. Додати нового Full-Time працівника");
                System.out.println("2. Додати нового Contract працівника");
                System.out.println("3. Вивести інформацію про всіх працівників (Поліморфізм)");
                System.out.println("4. Статистика (Кількість у списку)");
                System.out.println("5. Завершити роботу");
                System.out.print("Оберіть дію (1-5): ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        addNewEmployee(scanner, employees, true);
                        break;
                    case "2":
                        addNewEmployee(scanner, employees, false);
                        break;
                    case "3":
                        printAllEmployees(employees);
                        break;
                    case "4":
                        // Статичні члени видалено за умовою, тому виводимо розмір поточної колекції
                        System.out.println("Поточна кількість працівників у списку: " + employees.size());
                        break;
                    case "5":
                        System.out.println("Роботу завершено. Навседобре!");
                        return;
                    default:
                        System.out.println("Некоректний вибір. Введіть цифру від 1 до 5.");
                }
            }
        }
    }

    /**
     * Зчитує дані, створює об'єкт потрібного підкласу та додає до списку.
     * Демонструє обробку винятків при вводі даних.
     */
    private static void addNewEmployee(Scanner scanner, List<Employee> employees, boolean isFullTime) {
        try {
            System.out.print("Введіть ім'я: ");
            String fName = scanner.nextLine();

            System.out.print("Введіть прізвище: ");
            String lName = scanner.nextLine();

            System.out.print("Введіть базову ставку: ");
            double sal = scanner.nextDouble();

            if (isFullTime) {
                System.out.print("Введіть щомісячний бонус: ");
                double bonus = scanner.nextDouble();
                scanner.nextLine(); // Очищення буфера
                employees.add(new FullTimeEmployee(fName, lName, sal, bonus));
            } else {
                System.out.print("Введіть термін контракту (місяців): ");
                int months = scanner.nextInt();
                scanner.nextLine(); // Очищення буфера
                employees.add(new ContractEmployee(fName, lName, sal, months));
            }

            System.out.println("Працівника успішно додано до загальної колекції!");

        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу: Очікувалось числове значення!");
            scanner.nextLine(); // Очищення буфера після помилки
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        }
    }

    /**
     * Виведення списку всіх працівників.
     * Демонструє поліморфізм: виклик toString() на посиланні базового типу Employee
     * виконує реалізацію конкретного підкласу (FullTime чи Contract).
     */
    private static void printAllEmployees(List<Employee> employees) {
        System.out.println("\nСПИСОК ПРАЦІВНИКІВ (Поліморфне виведення):");
        if (employees.isEmpty()) {
            System.out.println("[Список порожній]");
        } else {
            for (int i = 0; i < employees.size(); i++) {
                // Поліморфний виклик toString()
                System.out.println((i + 1) + ". " + employees.get(i).toString());
            }
        }
    }
}