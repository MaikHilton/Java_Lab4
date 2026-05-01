package com.lab;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Спільна колекція для всіх типів об'єктів (порожня на старті)
        List<Employee> employees = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            boolean running = true;

            while (running) {
                System.out.println("\n=== ГОЛОВНЕ МЕНЮ (Лабораторна №8) ===");
                System.out.println("1. Створити новий об’єкт");
                System.out.println("2. Вивести інформацію про всі об’єкти");
                System.out.println("0. Завершити роботу програми");
                System.out.print("Оберіть дію: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        createObjectMenu(scanner, employees);
                        break;
                    case "2":
                        printAllEmployees(employees);
                        break;
                    case "0":
                        System.out.println("Роботу завершено.");
                        running = false;
                        break;
                    default:
                        System.out.println("Некоректний вибір. Спробуйте ще раз.");
                }
            }
        }
    }

    /**
     * Підменю для вибору типу об'єкта та його створення.
     */
    private static void createObjectMenu(Scanner scanner, List<Employee> employees) {
        System.out.println("\n--- ОБЕРІТЬ ТИП ПРАЦІВНИКА ---");
        System.out.println("1. Штатний працівник (FullTimeEmployee)");
        System.out.println("2. Контрактник (ContractEmployee)");
        System.out.println("3. Менеджер (Manager)");
        System.out.println("4. Стажер (Intern)");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");

        String typeChoice = scanner.nextLine().trim();

        if (typeChoice.equals("0")) {
            return; // Повернення без створення об'єкта
        }

        try {
            System.out.print("Введіть ім'я: ");
            String fName = scanner.nextLine();
            System.out.print("Введіть прізвище: ");
            String lName = scanner.nextLine();
            System.out.print("Введіть базову ставку/зарплату: ");
            double salary = scanner.nextDouble();

            switch (typeChoice) {
                case "1":
                    System.out.print("Введіть бонус: ");
                    double bonusFT = scanner.nextDouble();
                    employees.add(new FullTimeEmployee(fName, lName, salary, bonusFT));
                    System.out.println("Штатного працівника створено!");
                    break;
                case "2":
                    System.out.print("Введіть термін контракту (місяців): ");
                    int months = scanner.nextInt();
                    employees.add(new ContractEmployee(fName, lName, salary, months));
                    System.out.println("Контрактника створено!");
                    break;
                case "3":
                    System.out.print("Введіть бонус: ");
                    double bonusMgr = scanner.nextDouble();
                    System.out.print("Введіть розмір команди (осіб): ");
                    int teamSize = scanner.nextInt();
                    employees.add(new Manager(fName, lName, salary, bonusMgr, teamSize));
                    System.out.println("Менеджера створено!");
                    break;
                case "4":
                    scanner.nextLine(); // Очищення буфера перед рядком
                    System.out.print("Введіть назву університету: ");
                    String uni = scanner.nextLine();
                    employees.add(new Intern(fName, lName, salary, uni));
                    System.out.println("Стажера створено!");
                    // Уникаємо подвійного очищення буфера
                    return;
                default:
                    System.out.println("Невідомий тип. Об'єкт не створено.");
            }
            scanner.nextLine(); // Очищення буфера після чисел
        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу: некоректний формат даних!");
            scanner.nextLine(); // Очищення буфера
        }
    }

    /**
     * Поліморфне виведення колекції.
     */
    private static void printAllEmployees(List<Employee> employees) {
        System.out.println("\n--- СПИСОК ВСІХ ОБ'ЄКТІВ ---");
        if (employees.isEmpty()) {
            System.out.println("[Колекція порожня]");
        } else {
            for (int i = 0; i < employees.size(); i++) {
                // Викликається перевизначений метод toString() для кожного конкретного об'єкта
                System.out.println((i + 1) + ". " + employees.get(i).toString());
            }
        }
    }
}