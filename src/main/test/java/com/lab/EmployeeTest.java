package com.lab;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Комплексний тестовий клас для перевірки ієрархії об'єктів,
 * логіки агрегації (Lab 11) та взаємодії з базою даних через JDBC (Lab 12).
 */
class EmployeeTest {

    private static final String TEST_CONFIG = "test_db.properties";
    private DatabaseManager dbManager;

    /**
     * Налаштування середовища перед кожним тестом Lab 12.
     * Створює тимчасовий файл конфігурації для ініціалізації DatabaseManager.
     */
    @BeforeEach
    void setUp() throws IOException {
        Properties props = new Properties();
        props.setProperty("db.url", "jdbc:postgresql://localhost:5432/lab_db");
        props.setProperty("db.user", "postgres");
        props.setProperty("db.password", "1234");

        try (FileWriter writer = new FileWriter(TEST_CONFIG)) {
            props.store(writer, "Test DB Settings");
        }
        dbManager = new DatabaseManager(TEST_CONFIG);
    }

    /**
     * Очищення після виконання тестів.
     * Видаляє тимчасовий конфігураційний файл.
     */
    @AfterEach
    void tearDown() {
        File file = new File(TEST_CONFIG);
        if (file.exists()) {
            file.delete();
        }
    }

    // ==========================================
    // ТЕСТИ JDBC ТА ПАРАМЕТРІВ (LAB 12)
    // ==========================================

    /**
     * Перевіряє, чи коректно DatabaseManager зчитує налаштування з файлу.
     */
    @Test
    @DisplayName("Lab 12: Перевірка завантаження конфігурації БД")
    void shouldLoadDbConfigCorrectly() {
        assertDoesNotThrow(() -> new DatabaseManager(TEST_CONFIG),
                "Конструктор має успішно зчитувати існуючий файл властивостей  ");
    }

    /**
     * Перевіряє логіку збереження об'єкта через PreparedStatement.
     * Тест підтверджує, що метод не викликає винятків при передачі об'єкта  .
     */
    @Test
    @DisplayName("Lab 12: Тест виклику збереження в БД для Manager")
    void shouldHandleDatabaseInsertCall() {
        Manager mgr = new Manager("Іван", "Яценко", 35000, 12000, 8);
        assertDoesNotThrow(() -> dbManager.saveEmployee(mgr),
                "Метод saveEmployee повинен обробляти SQL-запит без помилок переривання  ");
    }

    // ==========================================
    // ТЕСТИ АГРЕГАЦІЇ ТА КЛАСУ COMPANY (LAB 11)
    // ==========================================

    /**
     * Перевіряє механізм зміни кількості існуючих даних замість створення дублікатів  .
     */
    @Test
    @DisplayName("Lab 11: Агрегація та облік кількості при дублюванні")
    void shouldAddEmployeeAndIncreaseQuantityIfDuplicate() {
        Company comp = new Company("Test IT");
        Employee e1 = new Intern("Олександр", "Шевченко", 5000, "СумДУ");
        Employee e2 = new Intern("Олександр", "Шевченко", 5000, "СумДУ");

        comp.addNewEmployee(e1, 1);
        comp.addNewEmployee(e2, 5);

        assertAll("Перевірка логіки агрегації",
                () -> assertEquals(1, comp.getEmployees().size(), "Розмір списку не має зростати"),
                () -> assertEquals(6, comp.getEmployees().get(0).getQuantity(), "Кількість має стати 6  ")
        );
    }

    /**
     * Перевіряє роботу методів пошуку, реалізованих у класі-контейнері Company  .
     */
    @Test
    @DisplayName("Lab 11: Пошук за прізвищем через об'єкт Company")
    void shouldSearchViaCompanyMethods() {
        Company comp = new Company("SearchCompany");
        comp.addNewEmployee(new FullTimeEmployee("Марія", "Коваль", 20000, 5000), 1);
        comp.addNewEmployee(new Manager("Іван", "Яценко", 35000, 10000, 8), 1);

        List<Employee> results = comp.searchByLastName("Яценко");

        assertAll("Пошук через контейнер",
                () -> assertEquals(1, results.size(), "Має бути знайдено 1 запис  "),
                () -> assertEquals("Яценко", results.get(0).getLastName(), "Прізвище має збігатися  ")
        );
    }

    // ==========================================
    // ТЕСТИ ІЄРАРХІЇ ТА ФОРМАТУ (LAB 11)
    // ==========================================

    /**
     * Перевіряє наявність поля quantity у текстовому представленні для файлу  .
     */
    @Test
    @DisplayName("Lab 11: Перевірка формату TXT з полем quantity")
    void shouldCreateBaseEmployeeWithQuantityInString() {
        Employee emp = new Employee("Іван", "Яценко", 15000.0);
        assertEquals("Employee;Іван;Яценко;15000.0;1", emp.toFileString(),
                "Рядок має відповідати структурі Лаб 11 з кількістю в кінці  ");
    }

    /**
     * Перевіряє коректність дискримінатора типу для кожного класу.
     * Це критично для поля 'type' у таблиці бази даних  .
     */
    @Test
    @DisplayName("Поліморфізм: Перевірка типів-дискримінаторів")
    void shouldCheckPolymorphicMarkers() {
        Employee ft = new FullTimeEmployee("Марія", "Коваль", 20000.0, 5000.0);
        Employee mgr = new Manager("Іван", "Яценко", 30000.0, 10000.0, 5);
        Employee intern = new Intern("Анна", "Лисенко", 8000.0, "СумДУ");

        assertAll("Типи об'єктів",
                () -> assertEquals("FullTimeEmployee", ft.getType(), "Некоректний тип для штатного працівника  "),
                () -> assertEquals("Manager", mgr.getType(), "Некоректний тип для менеджера  "),
                () -> assertEquals("Intern", intern.getType(), "Некоректний тип для стажера  ")
        );
    }
}