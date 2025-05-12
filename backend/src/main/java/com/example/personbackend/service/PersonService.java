package com.example.personbackend.service;

import com.example.personbackend.dto.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {
    Page<PersonDTO> getAll(Pageable pageable, String gender, String search);
    PersonDTO getById(Long id);
    PersonDTO create(PersonDTO personDTO);
    PersonDTO update(Long id, PersonDTO personDTO);
    void delete(Long id);
}
