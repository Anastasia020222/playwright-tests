package com.playwring.user;

import net.datafaker.Faker;

public class GenerateUser {

    public User generateUser() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String age = String.valueOf(faker.number().numberBetween(17, 70));
        String salary = String.valueOf(faker.number().numberBetween(500, 70000));
        String dep = faker.expression("#{options.option 'Insurance', 'Compliance', 'Legal'}");
        return new User(firstName, lastName, age, email, salary, dep);
    }
}
