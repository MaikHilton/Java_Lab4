package com.lab;

import com.google.gson.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Головний клас програми (Драйвер).
 * У Лабораторній №12 додано інтеграцію з JDBC для збереження об'єктів у БД .
 * Програма зчитує шлях до db.properties через аргументи командного рядка .
 *
 * @author Яценко Іван
 * @version 12.0
 */
public class Main {
    /** Шлях до файлу з даними у текстовому форматі  */
    private static final String TXT_FILE = "input.txt";
    /** Шлях до файлу з даними у форматі JSON  */
    private static final String JSON_FILE = "input.json";

    /** Контейнер для агрегації працівників (з Лаб 11)  */
    private static Company company = new Company("IT СумДУ");

    /** Менеджер для роботи з базою даних (з Лаб 12)  */
    private static DatabaseManager dbManager;

    /**
     * Точка входу в програму. Забезпечує роботу головного меню та ініціалізацію БД.
     *
     * @param args аргументи командного рядка. Перший аргумент — шлях до db.properties .
     */
    public static void main(String[] args) {
        // Перевірка та ініціалізація БД через аргументи командного рядка 
        if (args.length > 0) {
            dbManager = new DatabaseManager(args[0]);
        } else {
            System.out.println("!!! Попередження: Шлях до конфігурації БД не вказано в args.");
            System.out.println("Використовуйте: java Main db.properties");
        }

        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            boolean running = true;

            // Початкове завантаження даних (з файлу) 
            company = loadFromTxt(TXT_FILE);

            while (running) {
                System.out.println("\n=== КОМПАНІЯ: " + company.getName() + " (Лабораторна №12 - JDBC) ===");
                System.out.println("1. Пошук об’єктів");
                System.out.println("2. Завантажити та вивести з TXT");
                System.out.println("3. Завантажити та вивести з JSON");
                System.out.println("4. Додати нового працівника (Збереження в БД)");
                System.out.println("5. Зберегти поточний стан в TXT");
                System.out.println("6. Зберегти поточний стан в JSON");
                System.out.println("0. Вихід");
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
                        System.out.println("Завершення програми.");
                        running = false;
                        break;
                    default: System.out.println("Некоректний вибір.");
                }
            }
        }
    }

    /**
     * Меню створення нового об'єкта.
     * Зберігає новий об'єкт одночасно в локальну колекцію та в базу даних .
     *
     * @param scanner об'єкт Scanner для зчитування даних користувача
     */
    private static void createObjectMenu(Scanner scanner) {
        System.out.println("\n1. Штатний  2. Контрактник  3. Менеджер  4. Стажер");
        System.out.print("Оберіть тип: ");
        String typeChoice = scanner.nextLine().trim();

        try {
            System.out.print("Ім'я: ");
            String fName = scanner.nextLine().trim();
            System.out.print("Прізвище: ");
            String lName = scanner.nextLine().trim();

            if (fName.isEmpty() || lName.isEmpty()) {
                System.out.println("Помилка: Поля не можуть бути порожніми.");
                return;
            }

            System.out.print("Ставка: ");
            double salary = scanner.nextDouble();
            System.out.print("Кількість: ");
            int qty = scanner.nextInt();

            Employee newEmp = null;

            switch (typeChoice) {
                case "1":
                    System.out.print("Бонус: "); double b = scanner.nextDouble();
                    newEmp = new FullTimeEmployee(fName, lName, salary, b);
                    break;
                case "2":
                    System.out.print("Місяців контракту: "); int m = scanner.nextInt();
                    newEmp = new ContractEmployee(fName, lName, salary, m);
                    break;
                case "3":
                    System.out.print("Бонус: "); double bM = scanner.nextDouble();
                    System.out.print("Розмір команди: "); int t = scanner.nextInt();
                    newEmp = new Manager(fName, lName, salary, bM, t);
                    break;
                case "4":
                    scanner.nextLine();
                    System.out.print("Назва ВНЗ: "); String u = scanner.nextLine();
                    newEmp = new Intern(fName, lName, salary, u);
                    break;
                default:
                    System.out.println("Тип не знайдено.");
                    return;
            }
            scanner.nextLine();

            // 1. Додаємо в локальну колекцію (Лаб 11) 
            company.addNewEmployee(newEmp, qty);
            System.out.println("Об'єкт додано в локальну колекцію.");

            // 2. Додаємо в базу даних (Лаб 12) 
            if (dbManager != null) {
                dbManager.saveEmployee(newEmp);
            }

        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу числа!");
            scanner.nextLine();
        }
    }

    /**
     * Реалізує підменю пошуку за різними критеріями.
     *
     * @param scanner об'єкт Scanner для зчитування параметрів пошуку
     */
    private static void searchMenu(Scanner scanner) {
        if (company.getEmployees().isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }
        System.out.println("\n--- ПОШУК (Лаб 10-11) ---");
        System.out.println("1. Прізвище 2. Зарплата 3. Тип");
        String c = scanner.nextLine().trim();
        List<Employee> res = null;
        if (c.equals("1")) {
            System.out.print("Прізвище: "); res = company.searchByLastName(scanner.nextLine().trim());
        } else if (c.equals("2")) {
            System.out.print("Мін: "); double min = scanner.nextDouble();
            System.out.print("Макс: "); double max = scanner.nextDouble();
            scanner.nextLine();
            res = company.searchBySalaryRange(min, max);
        } else if (c.equals("3")) {
            System.out.print("Тип: "); res = company.searchByType(scanner.nextLine().trim());
        }
        printAll(res);
    }

    /**
     * Виводить список працівників у консоль.
     *
     * @param list список працівників для виводу
     */
    private static void printAll(List<Employee> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("[Порожньо]");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
    }

    /**
     * Завантажує дані компанії та працівників з TXT файлу .
     *
     * @param fileName шлях до TXT файлу
     * @return об'єкт Company з завантаженими даними
     */
    private static Company loadFromTxt(String fileName) {
        Company newC = new Company("IT СумДУ");
        File file = new File(fileName);
        if (!file.exists()) return newC;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length < 2) continue;
                if (p[0].equals("Company")) { newC.setName(p[1]); continue; }

                Employee emp = null;
                int q = 1;
                if (p[0].equals("Manager")) {
                    emp = new Manager(p[1], p[2], Double.parseDouble(p[3]), Double.parseDouble(p[4]), Integer.parseInt(p[5]));
                    q = Integer.parseInt(p[6]);
                } else if (p[0].equals("Intern")) {
                    emp = new Intern(p[1], p[2], Double.parseDouble(p[3]), p[4]);
                    q = Integer.parseInt(p[5]);
                } else if (p[0].equals("FullTimeEmployee")) {
                    emp = new FullTimeEmployee(p[1], p[2], Double.parseDouble(p[3]), Double.parseDouble(p[4]));
                    q = Integer.parseInt(p[5]);
                } else if (p[0].equals("ContractEmployee")) {
                    emp = new ContractEmployee(p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4]));
                    q = Integer.parseInt(p[5]);
                } else if (p[0].equals("Employee")) {
                    emp = new Employee(p[1], p[2], Double.parseDouble(p[3]));
                    q = Integer.parseInt(p[4]);
                }
                if (emp != null) newC.addNewEmployee(emp, q);
            }
        } catch (Exception e) { System.out.println("TXT Error"); }
        return newC;
    }

    /**
     * Зберігає дані компанії у TXT файл .
     *
     * @param c об'єкт компанії для збереження
     * @param f шлях до файлу
     */
    private static void saveToTxt(Company c, String f) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            bw.write("Company;" + c.getName()); bw.newLine();
            for (Employee e : c.getEmployees()) { bw.write(e.toFileString()); bw.newLine(); }
            System.out.println("Збережено в TXT.");
        } catch (IOException e) { System.out.println("Save TXT Error"); }
    }

    /**
     * Завантажує дані компанії з JSON файлу за допомогою бібліотеки Gson .
     *
     * @param f шлях до JSON файлу
     * @return об'єкт Company з завантаженими даними
     */
    private static Company loadFromJson(String f) {
        Company newC = new Company("IT СумДУ");
        try (Reader r = new FileReader(f)) {
            JsonObject j = JsonParser.parseReader(r).getAsJsonObject();
            if (j.has("name")) newC.setName(j.get("name").getAsString());
            JsonArray a = j.get("employees").getAsJsonArray();
            Gson g = new Gson();
            for (JsonElement e : a) {
                JsonObject o = e.getAsJsonObject();
                String t = o.get("type").getAsString();
                Employee emp = null;
                if (t.equals("Manager")) emp = g.fromJson(o, Manager.class);
                else if (t.equals("Intern")) emp = g.fromJson(o, Intern.class);
                else if (t.equals("FullTimeEmployee")) emp = g.fromJson(o, FullTimeEmployee.class);
                else if (t.equals("ContractEmployee")) emp = g.fromJson(o, ContractEmployee.class);
                else emp = g.fromJson(o, Employee.class);
                if (emp != null) newC.getEmployees().add(emp);
            }
        } catch (Exception e) { System.out.println("JSON Error"); }
        return newC;
    }

    /**
     * Зберігає дані компанії у JSON файл .
     *
     * @param c об'єкт компанії для збереження
     * @param f шлях до файлу
     */
    private static void saveToJson(Company c, String f) {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        try (Writer w = new FileWriter(f)) { g.toJson(c, w); System.out.println("Збережено в JSON."); }
        catch (IOException e) { System.out.println("Save JSON Error"); }
    }
}