package com.lab;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовий клас для перевірки агрегації (клас Company),
 * ієрархії Employee та логіки збереження даних у Lab 11.
 */
class EmployeeTest {

    // ==========================================
    // ТЕСТИ АГРЕГАЦІЇ ТА КЛАСУ COMPANY (LAB 11)
    // ==========================================

    @Test
    @DisplayName("Перевірка Company: агрегація та додавання з урахуванням кількості")
    void shouldAddEmployeeAndIncreaseQuantityIfDuplicate() {
        Company comp = new Company("Test IT");
        Employee e1 = new Intern("Олександр", "Шевченко", 5000, "СумДУ");
        Employee e2 = new Intern("Олександр", "Шевченко", 5000, "СумДУ"); // Повний дублікат

        comp.addNewEmployee(e1, 1);
        comp.addNewEmployee(e2, 5); // Має збільшити кількість існуючого до 6

        assertAll("Перевірка логіки агрегації",
                () -> assertEquals(1, comp.getEmployees().size(), "Розмір списку не має зростати при додаванні дубліката"),
                () -> assertEquals(6, comp.getEmployees().get(0).getQuantity(), "Кількість має сумуватися")
        );
    }

    @Test
    @DisplayName("Перевірка методів пошуку через об'єкт Company")
    void shouldSearchViaCompanyMethods() {
        Company comp = new Company("SearchCompany");
        comp.addNewEmployee(new FullTimeEmployee("Марія", "Коваль", 20000, 5000), 1);
        comp.addNewEmployee(new Manager("Іван", "Яценко", 35000, 10000, 8), 1);

        List<Employee> results = comp.searchByLastName("Яценко");

        assertAll("Пошук через контейнер",
                () -> assertEquals(1, results.size(), "Має знайти одного працівника[cite: 1]"),
                () -> assertEquals("Яценко", results.get(0).getLastName(), "Прізвище має збігатися[cite: 1]")
        );
    }

    // ==========================================
    // ТЕСТИ ІЄРАРХІЇ ТА ФОРМАТУ ФАЙЛІВ
    // ==========================================

    @Test
    @DisplayName("Тест Employee: додано поле quantity в TXT")
    void shouldCreateBaseEmployeeWithQuantityInString() {
        Employee emp = new Employee("Іван", "Яценко", 15000.0);
        // У Lab 11 формат: Employee;Ім'я;Прізвище;Ставка;Кількість[cite: 1]
        assertEquals("Employee;Іван;Яценко;15000.0;1", emp.toFileString());
    }

    @Test
    @DisplayName("Тест FullTimeEmployee: перевірка формату з кількістю")
    void shouldCreateFullTimeEmployeeAndCalculateTotalSalary() {
        FullTimeEmployee ft = new FullTimeEmployee("Марія", "Коваль", 20000.0, 5000.0);

        assertAll("Штатний працівник",
                () -> assertEquals("FullTimeEmployee", ft.getType()),
                () -> assertTrue(ft.toString().contains("(Кількість: 1)"), "toString має містити кількість[cite: 1]"),
                () -> assertEquals("FullTimeEmployee;Марія;Коваль;20000.0;5000.0;1", ft.toFileString())
        );
    }

    @Test
    @DisplayName("Тест ContractEmployee: перевірка формату")
    void shouldCreateContractEmployeeWithDuration() {
        ContractEmployee ce = new ContractEmployee("Дмитро", "Бондар", 18000.0, 12);

        assertAll("Контрактник",
                () -> assertEquals("ContractEmployee", ce.getType()),
                () -> assertEquals("ContractEmployee;Дмитро;Бондар;18000.0;12;1", ce.toFileString())
        );
    }

    @Test
    @DisplayName("Тест Manager: перевірка команди та кількості")
    void shouldCreateManagerWithTeamSize() {
        Manager mgr = new Manager("Іван", "Яценко", 30000.0, 10000.0, 5);

        assertAll("Менеджер",
                () -> assertEquals("Manager", mgr.getType()),
                () -> assertTrue(mgr.toString().contains("Команда: 5 осіб"), "Має відображатися розмір команди[cite: 1]"),
                () -> assertEquals("Manager;Іван;Яценко;30000.0;10000.0;5;1", mgr.toFileString())
        );
    }

    @Test
    @DisplayName("Тест Intern: перевірка ВНЗ та формату")
    void shouldCreateInternWithUniversityInfo() {
        Intern intern = new Intern("Анна", "Лисенко", 8000.0, "СумДУ");

        assertAll("Стажер",
                () -> assertEquals("Intern", intern.getType()),
                () -> assertEquals("Intern;Анна;Лисенко;8000.0;СумДУ;1", intern.toFileString())
        );
    }

    @Test
    @DisplayName("Поліморфізм: перевірка типів у списку Company")
    void shouldCheckPolymorphicMarkersInCompany() {
        Company comp = new Company("PolyTest");
        comp.addNewEmployee(new Employee("E1", "L1", 1000), 1);
        comp.addNewEmployee(new FullTimeEmployee("E2", "L2", 2000, 500), 1);
        comp.addNewEmployee(new Manager("E3", "L3", 4000, 1000, 10), 1);

        List<Employee> list = comp.getEmployees();
        assertAll("Типи в колекції",
                () -> assertEquals("Employee", list.get(0).getType()),
                () -> assertEquals("FullTimeEmployee", list.get(1).getType()),
                () -> assertEquals("Manager", list.get(2).getType())
        );
    }
}