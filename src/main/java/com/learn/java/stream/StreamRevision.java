package com.learn.java.stream;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamRevision {
    public static void main(String[] args) {
        List<Employee> employeeData =
                Arrays.asList(
                        new Employee(1, "Anurag", "IT", "TCS", "Bangalore", 65000),
                        new Employee(2, "Ravi", "HR", "Infosys", "Pune", 48000),
                        new Employee(3, "Aarti", "Finance", "TCS", "Noida", 72000),
                        new Employee(4, "John", "IT", "Wipro", "Bangalore", 58000),
                        new Employee(5, "Sarah", "IT", "Infosys", "Bangalore", 70000),
                        new Employee(6, "Mike", "HR", "Wipro", "Pune", 52000),
                        new Employee(7, "Priya", "Finance", "TCS", "Mumbai", 68000),
                        new Employee(8, "David", "IT", "Infosys", "Noida", 75000),
                        new Employee(9, "Lisa", "HR", "TCS", "Bangalore", 50000),
                        new Employee(10, "Raj", "Finance", "Wipro", "Mumbai", 62000));

        System.out.println("Find Min Salary: " + minSalary(employeeData));
        System.out.println("Find Max Salary: " + maxSalary(employeeData));
//
//        System.out.println("Print Employee by predicate ");
//
//        printEmployeeData(employeeData, e -> e.id() % 2 == 0);
//
       printEmployeeData(employeeData, e -> e.id() % 2 != 0);

        System.out.println(printMap(getAverageSalaryByDepartment(employeeData)));

        Predicate<Employee> itEmployee = e -> e.departmentName() != null && e.departmentName().contains("IT");
        Predicate<Employee> salaryMoreThan20K = e -> e.salary() > 65000.0;

        employeeData.stream()
                .filter(itEmployee.and(salaryMoreThan20K))
                .forEach(System.out::println);



//        employeeData.stream()
//                .collect(Collectors.groupingBy(Employee::company, Collectors.counting()))
//                .forEach((c, count) -> System.out.println(c + " : " + count));

//        String highestPayingCompany = employeeData.stream()
//                .max(Comparator.comparingDouble(Employee::salary))
//                .map(Employee::company)
//                .orElse("No Company");
//        System.out.println(highestPayingCompany);
//
//        employeeData.stream()
//                .collect(Collectors.groupingBy(Employee::company, Collectors.averagingDouble(Employee::salary)))
//                .entrySet().stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .forEach(entry ->
//                        System.out.println("Company: " + entry.getKey() + ", Avg Salary: " + entry.getValue())
//                );
//
//        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        nums.stream().filter(n -> n % 2 == 0).forEach(System.out::println);
    }

    private static String printMap(Map<String, Double> map) {
        return String.format("[%s]", map.keySet().stream()
                .map(s ->  "\"" + s + "\"" + ": " + "\"" + map.get(s) + "\"")
                .collect(Collectors.joining(", ")));
    }

    private static Employee minSalary(List<Employee> employees) {
        return employees.stream()
                .min(Comparator.comparingDouble(Employee::salary))
                .orElse(null);
    }

    private static Employee maxSalary(List<Employee> employees) {
        return employees.stream()
                .max(Comparator.comparingDouble(Employee::salary))
                .orElse(null);
    }

    private static void printEmployeeData(List<Employee> employees, Predicate<? super Employee> predicate) {
        employees.stream()
                .filter(predicate)
                .forEach(System.out::println);
    }

    private static Map<String, Double> getAverageSalaryByDepartment(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::departmentName, Collectors.averagingDouble(Employee::salary)));
    }

}
