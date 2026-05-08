package com.lab;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовий клас для перевірки логіки сортування Comparator (Лаб 14).
 */
class EmployeeTest {

    private List<Employee> testList;

    @BeforeEach
    void setUp() {
        testList = new ArrayList<>();
        testList.add(new Intern("Богдан", "Яценко", 5000, "СумДУ")); // Q: 1
        testList.add(new Manager("Андрій", "Авраменко", 25000, 5000, 5)); // Q: 1
        testList.add(new FullTimeEmployee("Віктор", "Коваль", 15000, 2000)); // Q: 1

        // Змінимо кількість для тестування сортування за кількістю
        testList.get(0).setQuantity(10); // Яценко - 10
        testList.get(1).setQuantity(5);  // Авраменко - 5
        testList.get(2).setQuantity(1);  // Коваль - 1
    }

    @Test
    @DisplayName("Сортування за зарплатою (анонімний клас)")
    void shouldSortBySalaryAscending() {
        Collections.sort(testList, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return Double.compare(e1.getSalary(), e2.getSalary());
            }
        });

        assertAll("Перевірка зростання зарплати",
                () -> assertEquals(5000, testList.get(0).getSalary()),
                () -> assertEquals(15000, testList.get(1).getSalary()),
                () -> assertEquals(25000, testList.get(2).getSalary())
        );
    }

    @Test
    @DisplayName("Сортування за кількістю спадання (анонімний клас)")
    void shouldSortByQuantityDescending() {
        Collections.sort(testList, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return Integer.compare(e2.getQuantity(), e1.getQuantity());
            }
        });

        assertEquals(10, testList.get(0).getQuantity(), "Першим має бути об'єкт з кількістю 10");
        assertEquals(1, testList.get(2).getQuantity(), "Останнім має бути об'єкт з кількістю 1");
    }

    @Test
    @DisplayName("Сортування за прізвищем (анонімний клас)")
    void shouldSortByLastNameAnonymous() {
        Collections.sort(testList, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return e1.getLastName().compareToIgnoreCase(e2.getLastName());
            }
        });

        assertEquals("Авраменко", testList.get(0).getLastName());
        assertEquals("Яценко", testList.get(2).getLastName());
    }
}