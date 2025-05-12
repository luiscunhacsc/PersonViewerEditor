package com.example.personbackend.service.impl;

import com.example.personbackend.dto.PersonDTO;
import com.example.personbackend.mapper.PersonMapper;
import com.example.personbackend.model.Gender;
import com.example.personbackend.model.Person;
import com.example.personbackend.repository.PersonRepository;
import com.example.personbackend.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public Page<PersonDTO> getAll(Pageable pageable, String gender, String search) {
        Page<Person> page = null;
        if (search != null && !search.isEmpty()) {
            if (gender != null && !gender.isEmpty() && !gender.equalsIgnoreCase("Todos")) {
                try {
                    Gender genderEnum = Gender.valueOf(gender);
                    page = personRepository.searchByFields(genderEnum, search, pageable);
                } catch (IllegalArgumentException e) {
                    page = personRepository.searchByFields(null, search, pageable);
                }
            } else {
                page = personRepository.searchByFields(null, search, pageable);
            }
            List<PersonDTO> dtoList = page.getContent().stream().map(personMapper::toDto).collect(Collectors.toList());
            return new PageImpl<>(dtoList, pageable, page.getTotalElements());
        }
        if (gender != null && !gender.isEmpty() && !gender.equalsIgnoreCase("Todos")) {
            try {
                Gender genderEnum = Gender.valueOf(gender);
                page = personRepository.findByGender(genderEnum, pageable);
            } catch (IllegalArgumentException e) {
                page = personRepository.findAll(pageable);
            }
        } else {
            page = personRepository.findAll(pageable);
        }
        List<PersonDTO> dtoList = page.getContent().stream().map(personMapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public PersonDTO getById(Long id) {
        return personRepository.findById(id).map(personMapper::toDto).orElse(null);
    }

    @Override
    public PersonDTO create(PersonDTO personDTO) {
        Person person = personMapper.toEntity(personDTO);
        return personMapper.toDto(personRepository.save(person));
    }

    @Override
    public PersonDTO update(Long id, PersonDTO personDTO) {
        Person person = personRepository.findById(id).orElseThrow();
        // Atualiza os campos manualmente
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setBirthDate(personDTO.getBirthDate());
        person.setGender(personDTO.getGender());
        person.setEmail(personDTO.getEmail());
        person.setPhone(personDTO.getPhone());
        person.setTaxId(personDTO.getTaxId());
        person.setAddress(personDTO.getAddress());
        return personMapper.toDto(personRepository.save(person));
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
