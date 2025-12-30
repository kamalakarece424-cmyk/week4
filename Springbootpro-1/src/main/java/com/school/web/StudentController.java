
package com.school.web;

import com.school.entity.Student;
import com.school.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(name="students")
public class StudentController {
  private final StudentService service;
  public StudentController(StudentService service) { this.service = service; }

  @PostMapping
  public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
    Student saved = service.create(student);
    return ResponseEntity.created(URI.create("/students/" + saved.getId())).body(saved);
  }

  @GetMapping
  public List<Student> findAll() { return service.findAll(); }

  @GetMapping("/{id}")
  public Student findById(@PathVariable Long id) { return service.findById(id); }

  @PutMapping("/{id}")
  public Student update(@PathVariable Long id, @Valid @RequestBody Student student) {
    return service.update(id, student);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
