package com.lab;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовий клас для перевірки ієрархії Employee та логіки збереження даних (TXT/JSON).
 */
class EmployeeTest {

    @Test
    @DisplayName("Тест базового класу Employee та його маркерів")
    void shouldCreateBaseEmployeeWithCorrectMetadata() {
        Employee emp = new Employee("Олександр", "Шевченко", 15000.0);

        assertAll("Перевірка базового працівника",
                () -> assertEquals("Employee", emp.getType(), "Поле type має бути Employee для JSON"),
                () -> assertEquals("Employee;Олександр;Шевченко;15000.0", emp.toFileString(), "Формат для TXT має бути коректним")
        );
    }

    @Test
    @DisplayName("Тест FullTimeEmployee: розрахунок зарплати та маркер")
    void shouldCreateFullTimeEmployeeAndCalculateTotalSalary() {
        FullTimeEmployee ft = new FullTimeEmployee("Марія", "Коваль", 20000.0, 5000.0);

        assertAll("Перевірка штатного працівника",
                () -> assertEquals("FullTimeEmployee", ft.getType()),
                () -> assertTrue(ft.toString().contains("з бонусом: 25000,00"), "toString має виводити суму ставки та бонусу"),
                () -> assertEquals("FullTimeEmployee;Марія;Коваль;20000.0;5000.0", ft.toFileString())
        );
    }

    @Test
    @DisplayName("Тест ContractEmployee: термін контракту")
    void shouldCreateContractEmployeeWithDuration() {
        ContractEmployee ce = new ContractEmployee("Дмитро", "Бондар", 18000.0, 12);

        assertAll("Перевірка контрактника",
                () -> assertEquals("ContractEmployee", ce.getType()),
                () -> assertTrue(ce.toString().contains("Термін: 12 міс."), "toString має містити тривалість"),
                () -> assertEquals("ContractEmployee;Дмитро;Бондар;18000.0;12", ce.toFileString())
        );
    }

    @Test
    @DisplayName("Тест Manager: успадкування та команда")
    void shouldCreateManagerWithTeamSize() {
        Manager mgr = new Manager("Іван", "Яценко", 30000.0, 10000.0, 5);

        assertAll("Перевірка менеджера",
                () -> assertEquals("Manager", mgr.getType()),
                () -> assertEquals(10000.0, mgr.getBonus(), 0.01),
                () -> assertTrue(mgr.toString().contains("Команда: 5 осіб"), "Має відображатися розмір команди"),
                () -> assertEquals("Manager;Іван;Яценко;30000.0;10000.0;5", mgr.toFileString())
        );
    }

    @Test
    @DisplayName("Тест Intern: назва університету")
    void shouldCreateInternWithUniversityInfo() {
        Intern intern = new Intern("Анна", "Лисенко", 8000.0, "СумДУ");

        assertAll("Перевірка стажера",
                () -> assertEquals("Intern", intern.getType()),
                () -> assertTrue(intern.toString().contains("ВНЗ: СумДУ"), "Має бути вказано назву ВНЗ"),
                () -> assertEquals("Intern;Анна;Лисенко;8000.0;СумДУ", intern.toFileString())
        );
    }

    @Test
    @DisplayName("Поліморфізм: перевірка унікальних маркерів у списку")
    void shouldCheckPolymorphicMarkersInCollection() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("E1", "L1", 1000));
        list.add(new FullTimeEmployee("E2", "L2", 2000, 500));
        list.add(new ContractEmployee("E3", "L3", 3000, 6));
        list.add(new Manager("E4", "L4", 4000, 1000, 10));
        list.add(new Intern("E5", "L5", 500, "Університет"));

        String[] expectedTypes = {"Employee", "FullTimeEmployee", "ContractEmployee", "Manager", "Intern"};

        for (int i = 0; i < list.size(); i++) {
            assertEquals(expectedTypes[i], list.get(i).getType(), "Тип об'єкта під індексом " + i + " некоректний");
            assertTrue(list.get(i).toFileString().startsWith(expectedTypes[i]), "Рядок TXT має починатися з правильного типу");
        }
    }
}