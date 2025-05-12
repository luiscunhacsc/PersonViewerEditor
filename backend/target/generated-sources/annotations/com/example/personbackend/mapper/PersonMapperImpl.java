package com.example.personbackend.mapper;

import com.example.personbackend.dto.PersonDTO;
import com.example.personbackend.model.Person;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-12T11:46:53+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDTO toDto(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setAddress( person.getAddress() );
        personDTO.setBirthDate( person.getBirthDate() );
        personDTO.setEmail( person.getEmail() );
        personDTO.setFirstName( person.getFirstName() );
        personDTO.setGender( person.getGender() );
        personDTO.setId( person.getId() );
        personDTO.setLastName( person.getLastName() );
        personDTO.setPhone( person.getPhone() );
        personDTO.setTaxId( person.getTaxId() );

        return personDTO;
    }

    @Override
    public Person toEntity(PersonDTO personDTO) {
        if ( personDTO == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.address( personDTO.getAddress() );
        person.birthDate( personDTO.getBirthDate() );
        person.email( personDTO.getEmail() );
        person.firstName( personDTO.getFirstName() );
        person.gender( personDTO.getGender() );
        person.id( personDTO.getId() );
        person.lastName( personDTO.getLastName() );
        person.phone( personDTO.getPhone() );
        person.taxId( personDTO.getTaxId() );

        return person.build();
    }
}
