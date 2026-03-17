package com.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Драйвер-клас.
 * Кількість працівників вводиться з клавіатури;
 * для кожного — ім'я та прізвище, решта полів задаються програмно
 * імітація частково-клавіатурного заповнення згідно умови.
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Читання кільксоті об'єктів
        int n = 0;
        while (n <= 0) {
            System.out.print("Введіть кількість працівників: ");
            try {
                n = Integer.parseInt(scanner.nextLine().trim());
                if (n <= 0) System.out.println(" Число має бути більше нуля.");
            } catch (NumberFormatException e) {
                System.out.println(" Будь ласка, введіть ціле число.");
            }
        }

        // Заповнення ArrayList
        List<Employee> employees = new ArrayList<>();

        // Фіксовані дані (посади, зарплати, стаж) — частина даних з програми
        String[]  positions   = {"Розробник", "Тестувальник", "Аналітик",
                                  "DevOps-інженер", "Менеджер проєкту"};
        double[]  salaries    = {45000, 32000, 38000, 50000, 55000};
        int[]     experiences = {3, 1, 5, 7, 10};

        System.out.println("\nВведіть дані для кожного працівника:");

        for (int i = 0; i < n; i++) {
            System.out.printf("%nПрацівник #%d%n", i + 1);

            // Ім'я
            String firstName = "";
            while (firstName.isBlank()) {
                System.out.print("  Ім'я: ");
                firstName = scanner.nextLine().trim();
                if (firstName.isBlank()) System.out.println(" Поле не може бути порожнім.");
            }

            // Прізвище
            String lastName = "";
            while (lastName.isBlank()) {
                System.out.print("  Прізвище: ");
                lastName = scanner.nextLine().trim();
                if (lastName.isBlank()) System.out.println(" Поле не може бути порожнім.");
            }

            // Решта полів програмно
            int idx = i % positions.length;
            Employee emp = new Employee(
                firstName,
                lastName,
                positions[idx],
                salaries[idx],
                experiences[idx]
            );

            employees.add(emp);
        }

        // Вивід всіх об'єктів2 через toString()
        System.out.println("  СПИСОК ПРАЦІВНИКІВ");

        for (int i = 0; i < employees.size(); i++) {
            System.out.printf("  %2d. %s%n", i + 1, employees.get(i));
        }

        System.out.printf("  Всього: %d працівник(ів)%n", employees.size());

        // Демонстрація equals()
        if (employees.size() >= 2) {
            Employee first  = employees.get(0);
            Employee second = employees.get(1);
            System.out.println("\n  Порівняння першого та другого об'єктів (equals):");
            System.out.printf("  %s == %s → %b%n",
                first.getFirstName(), second.getFirstName(),
                first.equals(second));

            // Клон першого — має бути рівним
            Employee clone = new Employee(
                first.getFirstName(), first.getLastName(),
                first.getPosition(), first.getSalary(), first.getExperienceYears()
            );
            System.out.printf("  %s == clone(%s) → %b%n",
                first.getFirstName(), first.getFirstName(),
                first.equals(clone));
        }

        System.out.println("\nГотово.");
        scanner.close();
    }
}
