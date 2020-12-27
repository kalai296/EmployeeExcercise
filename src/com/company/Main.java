package com.company;

import com.company.models.Employee;
import javafx.beans.binding.MapExpression;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        employeesByDepartment();
        highestSalariedEmployeeByDept();
        employeesByManager(4);
        employessJoinedInLastYear();
    }

    static void employessJoinedInLastYear() {
        List<Employee> employeeList = addEmployeeObjects();
        List<Employee> employeeJoinedinLastYear = employeeList.stream()
                .filter(e -> (LocalDate.now().getYear() - e.getJoiningDate().getYear() <= 1))
                .collect(Collectors.toList());
        employeeJoinedinLastYear.forEach(e -> System.out.println("Employee Id :- " + e.getId() + " , managerId :- " + e.getManagerId()));
    }

    static void employeesByManager(int managerId) {
        List<Employee> employeeList = addEmployeeObjects();
        List<Employee> employeeListByManagerId = employeeList.stream()
                .filter(e ->  e.getManagerId() == managerId && e.getManager() == true)
                .collect(Collectors.toList());

        employeeListByManagerId.forEach(e -> System.out.println("Employee Id :- " + e.getId() + " , managerId :- " + e.getManagerId()));
    }

    static void employeesByDepartment() {
        List<Employee> employeeList = addEmployeeObjects();
        Map<String, List<Employee>> byDept = employeeList.stream().collect(
                groupingBy(Employee::getDepartment));

        byDept.forEach((deptName, deptEmployees) -> System.out.println("Dept:" + deptName + "   " +
                ((List<Employee>) deptEmployees).stream().map(m -> m.getfName())
                        .collect(Collectors.joining(","))));
    }

    static void highestSalariedEmployeeByDept() {
        List<Employee> employeeList = addEmployeeObjects();
        Map<String, Employee> topEmployees =
                employeeList.stream()
                        .collect(groupingBy(
                                e -> e.getDepartment(),
                                collectingAndThen(maxBy(comparingInt(e -> e.getSalary())), Optional::get)
                        ));

        topEmployees.forEach((dept, employee) -> System.out.println(("dept:" + dept + " " + employee.getSalary() + " " + employee.getfName())));
    }


    public static Employee setEmployeeData(int id, String fname, String lname, String department, String designation, String grade, LocalDate joiningDate, Boolean isManager, int salary, int managerId) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setfName(fname);
        employee.setlName(lname);
        employee.setDepartment(department);
        employee.setDesignation(designation);
        employee.setGrade(grade);
        employee.setJoiningDate(joiningDate);
        employee.setManager(isManager);
        employee.setSalary(salary);
        employee.setManagerId(managerId);
        return employee;
    }

    public static List<Employee> addEmployeeObjects() {
        List<Employee> employeeList = new ArrayList<Employee>();
        employeeList.add(setEmployeeData(1, "test1", "testLast1", "testdep1", "testDes1", "testGrade1", LocalDate.parse("2019-01-01"), true, 1000, 2));
        employeeList.add(setEmployeeData(2, "test2", "testLast2", "testdep2", "testDes2", "testGrade2", LocalDate.parse("2018-01-01"), true, 2000, 4));
        employeeList.add(setEmployeeData(3, "test3", "testLast3", "testdep3", "testDes3", "testGrade3", LocalDate.parse("2020-01-01"), true, 3000, 4));
        employeeList.add(setEmployeeData(4, "test4", "testLast4", "testdep4", "testDes4", "testGrade4", LocalDate.parse("2016-01-01"), true, 4000, 3));

        employeeList.add(setEmployeeData(5, "test11", "testLast11", "testdep1", "testDes1", "testGrade1", LocalDate.parse("2019-01-01"), false, 2000, 1));
        employeeList.add(setEmployeeData(6, "test22", "testLast22", "testdep2", "testDes2", "testGrade2", LocalDate.parse("2018-01-01"), false, 3000, 1));
        employeeList.add(setEmployeeData(7, "test33", "testLast33", "testdep3", "testDes3", "testGrade3", LocalDate.parse("2020-01-01"), false, 4000, 1));
        employeeList.add(setEmployeeData(8, "test44", "testLast44", "testdep4", "testDes4", "testGrade4", LocalDate.parse("2016-01-01"), false, 5000, 2));
        return employeeList;
    }
}
