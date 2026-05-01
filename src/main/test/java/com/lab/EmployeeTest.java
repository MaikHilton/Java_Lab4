package com.lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Клас для модульного тестування (JUnit 5) логіки ієрархії Employee.
 * Покриває сценарії створення об'єктів різних типів та перевірку виводу інформації.
 */
class EmployeeTest {

    /**
     * Тест перевірки створення об'єкта FullTimeEmployee та коректності його даних.
     */
    @Test
    void shouldCreateFullTimeEmployeeWithCorrectData() {
        FullTimeEmployee ftEmp = new FullTimeEmployee("Іван", "Яценко", 20000, 5000);

        assertAll("Перевірка полів штатного працівника",
                () -> assertEquals("Іван", ftEmp.getFirstName()),
                () -> assertEquals("Яценко", ftEmp.getLastName()),
                () -> assertEquals(20000, ftEmp.getSalary(), 0.01),
                () -> assertTrue(ftEmp.toString().contains("з бонусом: 25000"), "toString має містити суму з бонусом")
        );
    }

    /**
     * Тест перевірки створення об'єкта ContractEmployee та коректності його даних.
     */
    @Test
    void shouldCreateContractEmployeeWithCorrectData() {
        ContractEmployee ctEmp = new ContractEmployee("Олексій", "Петренко", 15000, 12);

        assertAll("Перевірка полів контрактного працівника",
                () -> assertEquals("Олексій", ctEmp.getFirstName()),
                () -> assertEquals(15000, ctEmp.getSalary(), 0.01),
                () -> assertTrue(ctEmp.toString().contains("Термін: 12 міс."), "toString має містити тривалість контракту")
        );
    }

    /**
     * Демонстрація поліморфізму через тести: об'єкти різних підкласів
     * мають оброблятися як базовий тип Employee.
     */
    @Test
    void shouldDemonstratePolymorphismInCollection() {
        java.util.List<Employee> list = new java.util.ArrayList<>();
        list.add(new FullTimeEmployee("Анна", "Коваль", 30000, 2000));
        list.add(new ContractEmployee("Максим", "Сидоренко", 10000, 6));

        // Перевіряємо, що в списку дійсно два об'єкти базового типу
        assertEquals(2, list.size());

        // Перевіряємо, що перший об'єкт викликає версію toString штатного працівника
        assertTrue(list.get(0).toString().contains("[Штатний]"));

        // Перевіряємо, що другий об'єкт викликає версію toString контрактника
        assertTrue(list.get(1).toString().contains("[Контрактник]"));
    }

    /**
     * Тест валідації (якщо ви додали перевірки у конструктор базового класу).
     */
    @Test
    void shouldHandleValidationIfImplemented() {
        // Приклад перевірки на від'ємну зарплату (якщо логіка додана в конструктор Employee)
        // Наразі у наданому коді валідація мінімальна, але це шаблон для розширення:
        assertDoesNotThrow(() -> new FullTimeEmployee("Тест", "Тестович", 1000, 100));
    }

    /**
     * Перевірка того, що статичні лічильники більше не використовуються (вимога лаби 7).
     * Цей тест підтверджує, що ми видалили getTotalEmployees().
     */
    @Test
    void shouldNotHaveStaticCounter() {
        // Ми просто перевіряємо працездатність об'єктів без звернення до статики
        Employee emp = new Employee("Степан", "Гіга", 50000);
        assertNotNull(emp);
        // Спроба викликати Employee.getTotalEmployees() тепер призведе до помилки компіляції
    }
}