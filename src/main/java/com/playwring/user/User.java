package com.playwring.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String firstName;
    private String lastName;
    private String age;
    private String email;
    private String salary;
    private String department;

    User(String firstName, String lastName, String age, String email, String salary, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }

    @Override
    public String toString() {
        return "firstName= " + firstName +
                ", lastName='" + lastName +
                ", age= " + age +
                ", email= " + email +
                ", salary='" + salary +
                ", department='" + department;
    }
}
