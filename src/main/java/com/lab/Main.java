package com.lab;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Головний клас програми (Драйвер).
 * Реалізує консольне меню, керує колекцією об'єктів і забезпечує
 * збереження та відновлення даних за допомогою TXT та JSON файлів.
 */
public class Main {
    /** Ім'я файлу для зберігання даних у текстовому форматі */
    private static final String TXT_FILE = "input.txt";
    /** Ім'я файлу для зберігання даних у форматі JSON */
    private static final String JSON_FILE = "input.json";

    /**
     * Точка входу в програму. Запускає інтерактивне меню керування.
     *
     * @param args Аргументи командного рядка
     */
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            boolean running = true;

            while (running) {
                System.out.println("\n=== КОМБІНОВАНЕ МЕНЮ (TXT + JSON) ===");
                System.out.println("1. Завантажити та вивести з TXT");
                System.out.println("2. Завантажити та вивести з JSON");
                System.out.println("3. Додати нового працівника");
                System.out.println("4. Зберегти поточний список в TXT");
                System.out.println("5. Зберегти поточний список в JSON");
                System.out.println("0. Вихід (Зберегти в обидва формати)");
                System.out.print("Ваш вибір: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        employees.clear();
                        loadFromTxt(employees, TXT_FILE);
                        printAll(employees);
                        break;
                    case "2":
                        employees.clear();
                        loadFromJson(employees, JSON_FILE);
                        printAll(employees);
                        break;
                    case "3":
                        createObjectMenu(scanner, employees);
                        break;
                    case "4":
                        saveToTxt(employees, TXT_FILE);
                        break;
                    case "5":
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

    /**
     * Відображає підменю для створення об'єктів різних підкласів.
     *
     * @param scanner   Об'єкт Scanner для зчитування вводу
     * @param employees Колекція для збереження нового об'єкта
     */
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
                    scanner.nextLine(); // Очищення буфера
                    System.out.print("Назва ВНЗ: "); String uni = scanner.nextLine();
                    employees.add(new Intern(fName, lName, salary, uni));
                    return;
                default:
                    System.out.println("Об'єкт не створено (невідомий тип).");
            }
            scanner.nextLine(); // Очищення буфера
            System.out.println("Об'єкт успішно додано!");
        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу!");
            scanner.nextLine();
        }
    }

    /**
     * Виводить інформацію про всі об'єкти в колекції.
     *
     * @param list Колекція працівників для виведення
     */
    private static void printAll(List<Employee> list) {
        System.out.println("\n--- СПИСОК ОБ'ЄКТІВ ---");
        if (list.isEmpty()) System.out.println("[Порожньо]");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
    }

    /**
     * Зчитує дані з текстового файлу (TXT) та відновлює об'єкти.
     *
     * @param list     Колекція для заповнення
     * @param fileName Шлях до TXT файлу
     */
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

    /**
     * Зберігає стан колекції у текстовий файл (TXT).
     *
     * @param list     Колекція для збереження
     * @param fileName Шлях до TXT файлу
     */
    private static void saveToTxt(List<Employee> list, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Employee e : list) {
                bw.write(e.toFileString());
                bw.newLine();
            }
            System.out.println("Збережено у TXT.");
        } catch (IOException e) { System.out.println("Помилка запису TXT."); }
    }

    /**
     * Зчитує дані з файлу JSON та відновлює об'єкти, використовуючи поле 'type' як маркер.
     *
     * @param list     Колекція для заповнення
     * @param fileName Шлях до JSON файлу
     */
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

    /**
     * Зберігає стан колекції у форматі JSON з використанням бібліотеки Gson.
     *
     * @param list     Колекція для збереження
     * @param fileName Шлях до JSON файлу
     */
    private static void saveToJson(List<Employee> list, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(list, writer);
            System.out.println("Збережено у JSON.");
        } catch (IOException e) { System.out.println("Помилка запису JSON."); }
    }
}