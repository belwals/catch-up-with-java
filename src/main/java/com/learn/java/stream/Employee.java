package com.learn.java.stream;

public record Employee(
        long id,
        String fullName,
        String departmentName,
        String company,
        String location,
        double salary
        ) {

    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", fullName='" + fullName + '\'' +
            ", departmentName='" + departmentName + '\'' + 
            ", company='" + company + '\'' +
            ", location='" + location + '\'' +
            ", salary=" + salary +
            '}';
    }   
}
