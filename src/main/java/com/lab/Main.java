package com.lab;

import com.google.gson.*;
import java.io.*;
import java.util.*;

/**
 * Головний клас програми (Драйвер).
 * У Лабораторній №14 додано підменю сортування з використанням анонімних класів Comparator.
 * * @author Яценко Іван
 * @version 14.0
 */
public class Main {
    private static final String TXT_FILE = "input.txt";
    private static final String JSON_FILE = "input.json";
    private static Company company = new Company("IT СумДУ");

    /**
     * Точка входу в програму.
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            boolean running = true;
            company = loadFromTxt(TXT_FILE);

            while (running) {
                System.out.println("\n=== КОМПАНІЯ: " + company.getName() + " (Лабораторна №14) ===");
                System.out.println("1. Пошук об’єктів");
                System.out.println("2. Завантажити та вивести з TXT");
                System.out.println("3. Завантажити та вивести з JSON");
                System.out.println("4. Сортувати та вивести працівників (Підменю)");
                System.out.println("5. Додати нового працівника");
                System.out.println("6. Зберегти поточний стан в TXT");
                System.out.println("7. Зберегти поточний стан в JSON");
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
                    case "4":
                        showSortedEmployees(scanner);
                        break;
                    case "5": createObjectMenu(scanner); break;
                    case "6": saveToTxt(company, TXT_FILE); break;
                    case "7": saveToJson(company, JSON_FILE); break;
                    case "0":
                        saveToTxt(company, TXT_FILE);
                        saveToJson(company, JSON_FILE);
                        System.out.println("Роботу завершено.");
                        running = false;
                        break;
                    default: System.out.println("Некоректний вибір.");
                }
            }
        }
    }

    /**
     * Виводить підменю вибору критерію сортування та виконує сортування
     * за допомогою анонімних внутрішніх класів Comparator.
     * * @param scanner об'єкт для зчитування вибору користувача
     */
    private static void showSortedEmployees(Scanner scanner) {
        List<Employee> list = company.getEmployees();
        if (list.isEmpty()) {
            System.out.println("Список працівників порожній.");
            return;
        }

        System.out.println("\n--- ОБЕРІТЬ КРИТЕРІЙ СОРТУВАННЯ ---");
        System.out.println("1. За прізвищем (Алфавіт)");
        System.out.println("2. За зарплатою (Зростання)");
        System.out.println("3. За кількістю (Спадання)");
        System.out.println("0. Повернутися в головне меню");
        System.out.print("Вибір: ");

        String sortChoice = scanner.nextLine().trim();
        List<Employee> sortedList = new ArrayList<>(list);
        Comparator<Employee> comparator = null;

        switch (sortChoice) {
            case "1":
                // Критерій 1: Анонімний клас для сортування за прізвищем
                comparator = new Comparator<Employee>() {
                    @Override
                    public int compare(Employee e1, Employee e2) {
                        return e1.getLastName().compareToIgnoreCase(e2.getLastName());
                    }
                };
                break;
            case "2":
                // Критерій 2: Анонімний клас для сортування за ставкою (salary)
                comparator = new Comparator<Employee>() {
                    @Override
                    public int compare(Employee e1, Employee e2) {
                        return Double.compare(e1.getSalary(), e2.getSalary());
                    }
                };
                break;
            case "3":
                // Критерій 3: Анонімний клас для сортування за кількістю (quantity)
                comparator = new Comparator<Employee>() {
                    @Override
                    public int compare(Employee e1, Employee e2) {
                        // Сортування за спаданням: e2 порівнюємо з e1
                        return Integer.compare(e2.getQuantity(), e1.getQuantity());
                    }
                };
                break;
            case "0": return;
            default:
                System.out.println("Некоректний критерій.");
                return;
        }

        Collections.sort(sortedList, comparator);
        System.out.println("\n--- РЕЗУЛЬТАТ СОРТУВАННЯ ---");
        printAll(sortedList);
    }

    /**
     * Створює нового працівника та додає до колекції компанії.
     * @param scanner сканер для вводу даних
     */
    private static void createObjectMenu(Scanner scanner) {
        System.out.println("\n1. Штатний  2. Контрактник  3. Менеджер  4. Стажер");
        System.out.print("Вибір: ");
        String typeChoice = scanner.nextLine().trim();

        try {
            System.out.print("Ім'я: "); String fName = scanner.nextLine().trim();
            System.out.print("Прізвище: "); String lName = scanner.nextLine().trim();

            if (fName.isEmpty() || lName.isEmpty()) {
                System.out.println("Помилка: Ім'я та прізвище обов'язкові!");
                return;
            }

            System.out.print("Ставка: "); double salary = scanner.nextDouble();
            System.out.print("Кількість: "); int qty = scanner.nextInt();

            Employee newEmp = null;
            switch (typeChoice) {
                case "1":
                    System.out.print("Бонус: "); double b = scanner.nextDouble();
                    newEmp = new FullTimeEmployee(fName, lName, salary, b);
                    break;
                case "2":
                    System.out.print("Місяців: "); int m = scanner.nextInt();
                    newEmp = new ContractEmployee(fName, lName, salary, m);
                    break;
                case "3":
                    System.out.print("Бонус: "); double bM = scanner.nextDouble();
                    System.out.print("Команда: "); int t = scanner.nextInt();
                    newEmp = new Manager(fName, lName, salary, bM, t);
                    break;
                case "4":
                    scanner.nextLine();
                    System.out.print("ВНЗ: "); String u = scanner.nextLine();
                    newEmp = new Intern(fName, lName, salary, u);
                    break;
                default: System.out.println("Тип не знайдено."); return;
            }
            scanner.nextLine();
            company.addNewEmployee(newEmp, qty);
            System.out.println("Працівника додано!");
        } catch (InputMismatchException e) {
            System.out.println("Помилка типу даних!"); scanner.nextLine();
        }
    }

    /**
     * Підменю для пошуку працівників.
     * @param scanner сканер вводу
     */
    private static void searchMenu(Scanner scanner) {
        if (company.getEmployees().isEmpty()) return;
        System.out.println("\n--- ПОШУК ОБ'ЄКТІВ ---");
        System.out.println("1. За прізвищем 2. За зарплатою 3. За типом");
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
     * Друкує список працівників.
     * @param list список для виводу
     */
    private static void printAll(List<Employee> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("[Список порожній]");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
    }

    /**
     * Завантаження з TXT.
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
                }
                if (emp != null) newC.addNewEmployee(emp, q);
            }
        } catch (Exception e) {}
        return newC;
    }

    /**
     * Збереження в TXT.
     */
    private static void saveToTxt(Company c, String f) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            bw.write("Company;" + c.getName()); bw.newLine();
            for (Employee e : c.getEmployees()) { bw.write(e.toFileString()); bw.newLine(); }
        } catch (IOException e) {}
    }

    /**
     * Завантаження з JSON.
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
                if (emp != null) newC.getEmployees().add(emp);
            }
        } catch (Exception e) {}
        return newC;
    }

    /**
     * Збереження в JSON.
     */
    private static void saveToJson(Company c, String f) {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        try (Writer w = new FileWriter(f)) { g.toJson(c, w); }
        catch (IOException e) {}
    }
}