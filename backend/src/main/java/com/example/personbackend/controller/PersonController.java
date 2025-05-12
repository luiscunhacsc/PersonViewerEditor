package com.example.personbackend.controller;

import com.example.personbackend.dto.PersonDTO;
import com.example.personbackend.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@Tag(name = "Person API", description = "API for managing persons")
public class PersonController {
    private final PersonService personService;

    @GetMapping
    @Operation(summary = "Get all persons (paginated)")
    public Page<PersonDTO> getAll(@ParameterObject Pageable pageable, @RequestParam(name = "gender", required = false) String gender, @RequestParam(name = "search", required = false) String search) {
        return personService.getAll(pageable, gender, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get person by ID")
    public ResponseEntity<PersonDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new person")
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO) {
        return new ResponseEntity<>(personService.create(personDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing person")
    public ResponseEntity<PersonDTO> update(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok(personService.update(id, personDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person by ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
