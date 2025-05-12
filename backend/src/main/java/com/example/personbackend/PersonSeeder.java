package com.example.personbackend;

import com.example.personbackend.model.Person;
import com.example.personbackend.model.Gender;
import com.example.personbackend.repository.PersonRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.Random;

@Configuration
public class PersonSeeder {
    @Bean
    CommandLineRunner seedPersons(PersonRepository personRepository) {
        return args -> {
            if (personRepository.count() >= 200) return;
            Faker faker = new Faker(new Locale("pt"));
            Random random = new Random();
            Gender[] genders = Gender.values();
            for (int i = 0; i < 200; i++) {
                Gender gender = genders[random.nextInt(genders.length)];
                Person person = new Person();
                person.setFirstName(faker.name().firstName());
                person.setLastName(faker.name().lastName());
                person.setBirthDate(faker.date().birthday(18, 90).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                person.setGender(gender);
                person.setEmail(faker.internet().emailAddress());
                person.setPhone(faker.phoneNumber().cellPhone());
                person.setTaxId(faker.number().digits(9));
                person.setAddress(faker.address().fullAddress());
                personRepository.save(person);
            }
        };
    }
}
