package com.example.personbackend.mapper;

import com.example.personbackend.dto.PersonDTO;
import com.example.personbackend.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    PersonDTO toDto(Person person);
    Person toEntity(PersonDTO personDTO);
}
