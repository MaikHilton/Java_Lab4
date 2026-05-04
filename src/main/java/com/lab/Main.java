package com.lab;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Головний клас програми (Драйвер).
 * Керує колекцією об'єктів, підтримує читання/запис (TXT, JSON)
 * та реалізує пошук за різними критеріями.
 */
public class Main {
    private static final String TXT_FILE = "input.txt";
    private static final String JSON_FILE = "input.json";

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            boolean running = true;

            while (running) {
                System.out.println("\n=== ГОЛОВНЕ МЕНЮ (Лабораторна №10) ===");
                System.out.println("1. Пошук об’єктів");
                System.out.println("2. Завантажити та вивести з TXT");
                System.out.println("3. Завантажити та вивести з JSON");
                System.out.println("4. Додати нового працівника");
                System.out.println("5. Зберегти поточний список в TXT");
                System.out.println("6. Зберегти поточний список в JSON");
                System.out.println("0. Вихід (Зберегти в обидва формати)");
                System.out.print("Ваш вибір: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        searchMenu(scanner, employees);
                        break;
                    case "2":
                        employees.clear();
                        loadFromTxt(employees, TXT_FILE);
                        printAll(employees);
                        break;
                    case "3":
                        employees.clear();
                        loadFromJson(employees, JSON_FILE);
                        printAll(employees);
                        break;
                    case "4":
                        createObjectMenu(scanner, employees);
                        break;
                    case "5":
                        saveToTxt(employees, TXT_FILE);
                        break;
                    case "6":
                        saveToJson(employees, JSON_FILE);
                        break;
                    case "0":
                        saveToTxt(employees, TXT_FILE);
                        saveToJson(employees, JSON_FILE);
                        System.out.println("Роботу завершено. Дані збережено.");
                        running = false;
                        break;
                    default:
                        System.out.println("Некоректний вибір.");
                }
            }
        }
    }


    // БЛОК ПОШУКУ (ЛАБОРАТОРНА №10)

    /**
     * Підменю для вибору критерію пошуку об'єктів.
     * @param scanner   Сканер для вводу
     * @param employees Поточна колекція працівників
     */
    private static void searchMenu(Scanner scanner, List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("Колекція порожня. Завантажте дані або додайте працівників перед пошуком.");
            return;
        }

        System.out.println("\n--- МЕНЮ ПОШУКУ ---");
        System.out.println("1. За прізвищем");
        System.out.println("2. За діапазоном базової ставки (зарплати)");
        System.out.println("3. За типом посади (наприклад: Manager, Intern)");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Оберіть критерій: ");

        String choice = scanner.nextLine().trim();
        List<Employee> results = new ArrayList<>();

        switch (choice) {
            case "1":
                System.out.print("Введіть прізвище для пошуку: ");
                String targetLastName = scanner.nextLine().trim();
                results = searchByLastName(employees, targetLastName);
                break;
            case "2":
                try {
                    System.out.print("Введіть мінімальну ставку: ");
                    double min = scanner.nextDouble();
                    System.out.print("Введіть максимальну ставку: ");
                    double max = scanner.nextDouble();
                    scanner.nextLine(); // Очищення буфера
                    results = searchBySalaryRange(employees, min, max);
                } catch (InputMismatchException e) {
                    System.out.println("Помилка вводу числа!");
                    scanner.nextLine();
                    return;
                }
                break;
            case "3":
                System.out.print("Введіть тип (Employee, FullTimeEmployee, ContractEmployee, Manager, Intern): ");
                String targetType = scanner.nextLine().trim();
                results = searchByType(employees, targetType);
                break;
            case "0":
                return;
            default:
                System.out.println("Некоректний вибір критерію.");
                return;
        }

        printSearchResults(results);
    }

    /**
     * Шукає працівників за точним збігом прізвища (без урахування регістру).
     * @param list     Колекція для пошуку
     * @param lastName Прізвище
     * @return Список знайдених об'єктів
     */
    private static List<Employee> searchByLastName(List<Employee> list, String lastName) {
        List<Employee> found = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLastName().equalsIgnoreCase(lastName)) {
                found.add(list.get(i));
            }
        }
        return found;
    }

    /**
     * Шукає працівників, чия базова ставка потрапляє в заданий діапазон.
     * @param list Колекція для пошуку
     * @param min  Мінімальна межа
     * @param max  Максимальна межа
     * @return Список знайдених об'єктів
     */
    private static List<Employee> searchBySalaryRange(List<Employee> list, double min, double max) {
        List<Employee> found = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSalary() >= min && list.get(i).getSalary() <= max) {
                found.add(list.get(i));
            }
        }
        return found;
    }

    /**
     * Шукає працівників за їхнім типом (маркером класу).
     * @param list Поточна колекція
     * @param type Тип працівника (наприклад, "Manager")
     * @return Список знайдених об'єктів
     */
    private static List<Employee> searchByType(List<Employee> list, String type) {
        List<Employee> found = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType().equalsIgnoreCase(type)) {
                found.add(list.get(i));
            }
        }
        return found;
    }

    /**
     * Виводить результати пошуку на екран.
     * @param results Список знайдених об'єктів
     */
    private static void printSearchResults(List<Employee> results) {
        System.out.println("\n--- РЕЗУЛЬТАТИ ПОШУКУ ---");
        if (results.isEmpty()) {
            System.out.println("Жодного об'єкта не знайдено за заданими критеріями.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i).toString());
            }
            System.out.println("Знайдено записів: " + results.size());
        }
    }

    // БЛОК СТВОРЕННЯ ТА ЗБЕРЕЖЕННЯ (ЛАБ 9)

    private static void createObjectMenu(Scanner scanner, List<Employee> employees) {
        System.out.println("\n1. Штатний  2. Контрактник  3. Менеджер  4. Стажер");
        System.out.print("Вибір типу: ");
        String typeChoice = scanner.nextLine().trim();

        try {
            System.out.print("Ім'я: "); String fName = scanner.nextLine();
            System.out.print("Прізвище: "); String lName = scanner.nextLine();
            System.out.print("Базова ставка: "); double salary = scanner.nextDouble();

            switch (typeChoice) {
                case "1":
                    System.out.print("Бонус: "); double bonusFT = scanner.nextDouble();
                    employees.add(new FullTimeEmployee(fName, lName, salary, bonusFT));
                    break;
                case "2":
                    System.out.print("Місяців: "); int months = scanner.nextInt();
                    employees.add(new ContractEmployee(fName, lName, salary, months));
                    break;
                case "3":
                    System.out.print("Бонус: "); double bonusMgr = scanner.nextDouble();
                    System.out.print("Команда (осіб): "); int team = scanner.nextInt();
                    employees.add(new Manager(fName, lName, salary, bonusMgr, team));
                    break;
                case "4":
                    scanner.nextLine();
                    System.out.print("Назва ВНЗ: "); String uni = scanner.nextLine();
                    employees.add(new Intern(fName, lName, salary, uni));
                    return;
                default:
                    System.out.println("Об'єкт не створено (невідомий тип).");
            }
            scanner.nextLine();
            System.out.println("Об'єкт успішно додано!");
        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу!");
            scanner.nextLine();
        }
    }

    private static void printAll(List<Employee> list) {
        System.out.println("\n--- СПИСОК ОБ'ЄКТІВ ---");
        if (list.isEmpty()) System.out.println("[Порожньо]");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
    }

    private static void loadFromTxt(List<Employee> list, String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length < 4) continue;
                String type = p[0];
                if (type.equals("Manager")) list.add(new Manager(p[1], p[2], Double.parseDouble(p[3]), Double.parseDouble(p[4]), Integer.parseInt(p[5])));
                else if (type.equals("Intern")) list.add(new Intern(p[1], p[2], Double.parseDouble(p[3]), p[4]));
                else if (type.equals("FullTimeEmployee")) list.add(new FullTimeEmployee(p[1], p[2], Double.parseDouble(p[3]), Double.parseDouble(p[4])));
                else if (type.equals("ContractEmployee")) list.add(new ContractEmployee(p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4])));
                else if (type.equals("Employee")) list.add(new Employee(p[1], p[2], Double.parseDouble(p[3])));
            }
            System.out.println("Завантажено з TXT.");
        } catch (Exception e) { System.out.println("Помилка читання TXT."); }
    }

    private static void saveToTxt(List<Employee> list, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Employee e : list) {
                bw.write(e.toFileString());
                bw.newLine();
            }
            System.out.println("Збережено у TXT.");
        } catch (IOException e) { System.out.println("Помилка запису TXT."); }
    }

    private static void loadFromJson(List<Employee> list, String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return;
        try (Reader reader = new FileReader(file)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            Gson gson = new Gson();
            for (JsonElement el : array) {
                JsonObject obj = el.getAsJsonObject();
                String type = obj.get("type").getAsString();
                if (type.equals("Manager")) list.add(gson.fromJson(obj, Manager.class));
                else if (type.equals("Intern")) list.add(gson.fromJson(obj, Intern.class));
                else if (type.equals("FullTimeEmployee")) list.add(gson.fromJson(obj, FullTimeEmployee.class));
                else if (type.equals("ContractEmployee")) list.add(gson.fromJson(obj, ContractEmployee.class));
                else list.add(gson.fromJson(obj, Employee.class));
            }
            System.out.println("Завантажено з JSON.");
        } catch (Exception e) { System.out.println("Помилка читання JSON."); }
    }

    private static void saveToJson(List<Employee> list, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(list, writer);
            System.out.println("Збережено у JSON.");
        } catch (IOException e) { System.out.println("Помилка запису JSON."); }
    }
}