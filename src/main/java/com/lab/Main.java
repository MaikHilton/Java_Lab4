package com.lab;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Клас-драйвер програми.
 * Реалізує консольне меню для взаємодії з користувачем та обробку винятків вводу.
 */
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            Company company = new Company();

            System.out.println("Вітаємо у системі обліку компанії!");

            while (true) {
                System.out.println("\n--- МЕНЮ ---");
                System.out.println("1. Створити нового працівника");
                System.out.println("2. Клонувати останнього працівника");
                System.out.println("3. Вивести інформацію про всіх працівників");
                System.out.println("4. Статистика");
                System.out.println("5. Завершити роботу");
                System.out.print("Оберіть дію (1-5): ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        createNewEmployee(scanner, company);
                        break;
                    case "2":
                        cloneLastEmployee(company);
                        break;
                    case "3":
                        printAllEmployees(company);
                        break;
                    case "4":
                        System.out.println("Загальна кількість створених об'єктів Employee за весь час: "
                                + Employee.getTotalEmployees());
                        break;
                    case "5":
                        System.out.println("Роботу завершено. Навседобре!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Некоректний вибір. Введіть цифру від 1 до 5.");
                }
            }
        }
    }

    /**
     * Зчитує дані з клавіатури, створює працівника та додає його до компанії.
     */
    private static void createNewEmployee(Scanner scanner, Company company) {
        try {
            System.out.print("Введіть ім'я: ");
            String fName = scanner.nextLine();

            System.out.print("Введіть прізвище: ");
            String lName = scanner.nextLine();

            System.out.print("Введіть відділ: ");
            String dep = scanner.nextLine();

            System.out.println("Доступні посади:");
            Position[] positions = Position.getAvailablePositions();
            for (int i = 0; i < positions.length; i++) {
                System.out.println((i + 1) + ". " + positions[i]);
            }
            System.out.print("Оберіть номер посади: ");
            int posIndex = scanner.nextInt();
            if (posIndex < 1 || posIndex > positions.length) {
                throw new IllegalArgumentException("Некоректний номер посади.");
            }
            Position position = positions[posIndex - 1];

            System.out.print("Введіть зарплату (грн): ");
            double sal = scanner.nextDouble();

            System.out.print("Введіть стаж (роки): ");
            int exp = scanner.nextInt();
            scanner.nextLine();

            Employee newEmp = new Employee(fName, lName, dep, position, sal, exp);
            company.addEmployee(newEmp);
            System.out.println("Працівника успішно додано!");

        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу: Очікувалось числове значення!");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації: " + e.getMessage());
            if (scanner.hasNextLine() && !scanner.hasNextInt() && !scanner.hasNextDouble()) {
                scanner.nextLine();
            }
        }
    }

    /**
     * Демонстрація роботи конструктора копіювання.
     */
    private static void cloneLastEmployee(Company company) {
        List<Employee> list = company.getAllEmployees();
        if (list.isEmpty()) {
            System.out.println("Список порожній. Спочатку створіть хоча б одного працівника.");
            return;
        }

        try {
            Employee lastAdded = list.get(list.size() - 1);
            Employee clone = new Employee(lastAdded);
            company.addEmployee(clone);
            System.out.println("Останнього працівника успішно клоновано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка клонування: " + e.getMessage());
        }
    }

    /**
     * Виведення списку всіх працівників.
     */
    private static void printAllEmployees(Company company) {
        List<Employee> list = company.getAllEmployees();
        System.out.println("\nСПИСОК ПРАЦІВНИКІВ компанії:");
        if (list.isEmpty()) {
            System.out.println("[Список порожній]");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i).toString());
            }
        }
        System.out.println("Всього у компанії: " + company.getEmployeesCount() + " працівник(ів).");
    }
}