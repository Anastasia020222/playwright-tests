package com.playwring.user;

import lombok.*;
import net.datafaker.Faker;

@Getter
@NoArgsConstructor
@Builder
@Setter
@AllArgsConstructor
public class User {

    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String age;
    private String email;
    private String salary;
    private String department;

    public User(String firstName, String lastName, String password, String age, String email, String salary, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }

    public void fillInFields() {
        Faker faker = new Faker();
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        password = faker.regexify("[A-Z]{2}[a-z]{4}\\d{2}[#@$%!&*]{1}");
        email = faker.internet().emailAddress();
        age = String.valueOf(faker.number().numberBetween(17, 70));
        salary = String.valueOf(faker.number().numberBetween(500, 70000));
        department = faker.expression("#{options.option 'Insurance', 'Compliance', 'Legal'}");
    }

    @Override
    public String toString() {
        return "userId= " + userId +
               ", firstName= " + firstName +
               ", lastName= " + lastName +
               ", password= " + password +
               ", age= " + age +
               ", email= " + email +
               ", salary= " + salary +
               ", department= " + department;
    }
}
