package com.lab;

import com.google.gson.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Головний клас управління програмою (Драйвер).
 * Реалізує інтерфейс користувача через консольне меню та забезпечує
 * взаємодію між користувачем і об'єктом класу-контейнера {@link Company}.
 *
 * Програма підтримує завантаження/збереження даних у форматах TXT та JSON,
 * а також пошук працівників за трьома різними критеріями.
 */
public class Main {
    /** Шлях до файлу з даними у текстовому форматі */
    private static final String TXT_FILE = "input.txt";
    /** Шлях до файлу з даними у форматі JSON */
    private static final String JSON_FILE = "input.json";
    /** Об'єкт компанії, що агрегує список працівників */
    private static Company company = new Company("IT СумДУ");

    /**
     * Точка входу в програму. Забезпечує роботу головного циклу меню.
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            boolean running = true;

            while (running) {
                System.out.println("\n=== КОМПАНІЯ: " + company.getName() + " (Лабораторна №11) ===");
                System.out.println("1. Пошук об’єктів");
                System.out.println("2. Завантажити та вивести з TXT");
                System.out.println("3. Завантажити та вивести з JSON");
                System.out.println("4. Додати нового працівника");
                System.out.println("5. Зберегти поточний стан в TXT");
                System.out.println("6. Зберегти поточний стан в JSON");
                System.out.println("0. Вихід (Збереження в обидва формати)");
                System.out.print("Ваш вибір: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1": searchMenu(scanner); break;
                    case "2":
                        company = loadFromTxt(TXT_FILE);
                        printAll(company.getEmployees());
                        break;
                    case "3":
                        company = loadFromJson(JSON_FILE);
                        printAll(company.getEmployees());
                        break;
                    case "4": createObjectMenu(scanner); break;
                    case "5": saveToTxt(company, TXT_FILE); break;
                    case "6": saveToJson(company, JSON_FILE); break;
                    case "0":
                        saveToTxt(company, TXT_FILE);
                        saveToJson(company, JSON_FILE);
                        System.out.println("Роботу завершено. Дані збережено.");
                        running = false;
                        break;
                    default: System.out.println("Некоректний вибір.");
                }
            }
        }
    }

    /**
     * Реалізує підменю пошуку працівників за трьома критеріями.
     * @param scanner об'єкт Scanner для зчитування параметрів пошуку
     */
    private static void searchMenu(Scanner scanner) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Колекція порожня. Завантажте дані.");
            return;
        }

        System.out.println("\n--- МЕНЮ ПОШУКУ ---");
        System.out.println("1. За прізвищем");
        System.out.println("2. За діапазоном ставки");
        System.out.println("3. За типом (Employee, FullTimeEmployee, ContractEmployee, Manager, Intern)");
        System.out.print("Оберіть критерій: ");

        String choice = scanner.nextLine().trim();
        List<Employee> results = null;

        switch (choice) {
            case "1":
                System.out.print("Прізвище: ");
                results = company.searchByLastName(scanner.nextLine().trim());
                break;
            case "2":
                try {
                    System.out.print("Мін. ставка: "); double min = scanner.nextDouble();
                    System.out.print("Макс. ставка: "); double max = scanner.nextDouble();
                    scanner.nextLine();
                    results = company.searchBySalaryRange(min, max);
                } catch (InputMismatchException e) {
                    System.out.println("Помилка вводу числа."); scanner.nextLine(); return;
                }
                break;
            case "3":
                System.out.print("Тип: ");
                results = company.searchByType(scanner.nextLine().trim());
                break;
            default: System.out.println("Некоректний вибір."); return;
        }

        printSearchResults(results);
    }

    /**
     * Відображає результати виконання пошуку в консолі.
     * @param results список знайдених об'єктів
     */
    private static void printSearchResults(List<Employee> results) {
        System.out.println("\n--- РЕЗУЛЬТАТИ ПОШУКУ ---");
        if (results == null || results.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i).toString());
            }
        }
    }

    /**
     * Меню створення нового об'єкта та додавання його до компанії з урахуванням кількості.
     * @param scanner об'єкт Scanner для вводу атрибутів працівника
     */
    private static void createObjectMenu(Scanner scanner) {
        System.out.println("\n1. Штатний  2. Контрактник  3. Менеджер  4. Стажер");
        System.out.print("Вибір: ");
        String typeChoice = scanner.nextLine().trim();

        try {
            System.out.print("Ім'я: ");
            String fName = scanner.nextLine().trim();
            System.out.print("Прізвище: ");
            String lName = scanner.nextLine().trim();

            // Валідація на пусті поля
            if (fName.isEmpty() || lName.isEmpty()) {
                System.out.println("Помилка: Ім'я та Прізвище не можуть бути порожніми!");
                return;
            }

            System.out.print("Ставка: ");
            double salary = scanner.nextDouble();

            // Валідація на від'ємні значення (хороша практика)
            if (salary < 0) {
                System.out.println("Помилка: Ставка не може бути від'ємною!");
                scanner.nextLine();
                return;
            }

            System.out.print("Кількість: ");
            int qty = scanner.nextInt();
            if (qty <= 0) {
                System.out.println("Помилка: Кількість має бути більше 0!");
                scanner.nextLine();
                return;
            }

            switch (typeChoice) {
                case "1":
                    System.out.print("Бонус: "); double b = scanner.nextDouble();
                    company.addNewEmployee(new FullTimeEmployee(fName, lName, salary, b), qty);
                    break;
                case "2":
                    System.out.print("Місяців: "); int m = scanner.nextInt();
                    company.addNewEmployee(new ContractEmployee(fName, lName, salary, m), qty);
                    break;
                case "3":
                    System.out.print("Бонус: "); double bM = scanner.nextDouble();
                    System.out.print("Команда: "); int t = scanner.nextInt();
                    company.addNewEmployee(new Manager(fName, lName, salary, bM, t), qty);
                    break;
                case "4":
                    scanner.nextLine();
                    System.out.print("ВНЗ: "); String u = scanner.nextLine();
                    company.addNewEmployee(new Intern(fName, lName, salary, u), qty);
                    return;
                default: System.out.println("Тип не знайдено.");
            }
            scanner.nextLine();
            System.out.println("Працівника(ів) успішно додано!");
        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу!"); scanner.nextLine();
        }
    }

    /**
     * Виводить список усіх працівників компанії.
     * @param list список працівників
     */
    private static void printAll(List<Employee> list) {
        System.out.println("\n--- ПОВНИЙ СПИСОК ПРАЦІВНИКІВ ---");
        if (list.isEmpty()) System.out.println("[Колекція порожня]");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
    }

    /**
     * Завантажує дані про компанію та працівників із текстового файлу[cite: 1].
     * @param fileName шлях до TXT файлу
     * @return ініціалізований об'єкт Company
     */
    private static Company loadFromTxt(String fileName) {
        Company newComp = new Company("Нова Компанія");
        File file = new File(fileName);
        if (!file.exists()) return newComp;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length < 2) continue;

                if (p[0].equals("Company")) {
                    newComp.setName(p[1]);
                } else {
                    Employee emp = null;
                    int qty = 1;
                    if (p[0].equals("Manager")) {
                        emp = new Manager(p[1], p[2], Double.parseDouble(p[3]), Double.parseDouble(p[4]), Integer.parseInt(p[5]));
                        qty = Integer.parseInt(p[6]);
                    } else if (p[0].equals("Intern")) {
                        emp = new Intern(p[1], p[2], Double.parseDouble(p[3]), p[4]);
                        qty = Integer.parseInt(p[5]);
                    } else if (p[0].equals("FullTimeEmployee")) {
                        emp = new FullTimeEmployee(p[1], p[2], Double.parseDouble(p[3]), Double.parseDouble(p[4]));
                        qty = Integer.parseInt(p[5]);
                    } else if (p[0].equals("ContractEmployee")) {
                        emp = new ContractEmployee(p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4]));
                        qty = Integer.parseInt(p[5]);
                    } else if (p[0].equals("Employee")) {
                        emp = new Employee(p[1], p[2], Double.parseDouble(p[3]));
                        qty = Integer.parseInt(p[4]);
                    }
                    if (emp != null) newComp.addNewEmployee(emp, qty);
                }
            }
            System.out.println("Дані успішно завантажено з TXT.");
        } catch (Exception e) { System.out.println("Помилка зчитування TXT."); }
        return newComp;
    }

    /**
     * Зберігає стан компанії та її працівників у текстовий файл[cite: 1].
     * @param comp об'єкт компанії для збереження
     * @param fileName шлях до TXT файлу
     */
    private static void saveToTxt(Company comp, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("Company;" + comp.getName());
            bw.newLine();
            for (Employee e : comp.getEmployees()) {
                bw.write(e.toFileString());
                bw.newLine();
            }
            System.out.println("Дані збережено у TXT.");
        } catch (IOException e) { System.out.println("Помилка запису TXT."); }
    }

    /**
     * Завантажує дані з JSON файлу за допомогою бібліотеки Gson[cite: 1].
     * @param fileName шлях до JSON файлу
     * @return ініціалізований об'єкт Company
     */
    private static Company loadFromJson(String fileName) {
        Company newComp = new Company("Нова Компанія");
        File file = new File(fileName);
        if (!file.exists()) return newComp;

        try (Reader reader = new FileReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (json.has("name")) newComp.setName(json.get("name").getAsString());

            JsonArray arr = json.get("employees").getAsJsonArray();
            Gson gson = new Gson();
            for (JsonElement el : arr) {
                JsonObject obj = el.getAsJsonObject();
                String type = obj.get("type").getAsString();
                Employee emp = null;

                if (type.equals("Manager")) emp = gson.fromJson(obj, Manager.class);
                else if (type.equals("Intern")) emp = gson.fromJson(obj, Intern.class);
                else if (type.equals("FullTimeEmployee")) emp = gson.fromJson(obj, FullTimeEmployee.class);
                else if (type.equals("ContractEmployee")) emp = gson.fromJson(obj, ContractEmployee.class);
                else emp = gson.fromJson(obj, Employee.class);

                if (emp != null) newComp.getEmployees().add(emp);
            }
            System.out.println("Дані завантажено з JSON.");
        } catch (Exception e) { System.out.println("Помилка зчитування JSON."); }
        return newComp;
    }

    /**
     * Серіалізує об'єкт компанії у формат JSON та записує у файл[cite: 1].
     * @param comp об'єкт компанії
     * @param fileName шлях до файлу
     */
    private static void saveToJson(Company comp, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(comp, writer);
            System.out.println("Дані збережено у JSON.");
        } catch (IOException e) { System.out.println("Помилка запису JSON."); }
    }
}