package com.school.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student")
public class Student {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;                // Wrapper type

 
 private String name;

 
 @Column(unique = true)
 private String email;

 private LocalDate dob;

 
 private Integer marks;          // Wrapper type

 // --- Required by JPA ---
 public Student() { }

 // --- All-args using wrappers (Long, Integer) ---
 public Student(Long id, String name, String email, LocalDate dob, Integer marks) {
     this.id = id;
     this.name = name;
     this.email = email;
     this.dob = dob;
     this.marks = marks;
 }

 // --- Convenience constructor using primitives (long, int) ---
 public Student(long id, String name, String email, LocalDate dob, int marks) {
     this(Long.valueOf(id), name, email, dob, Integer.valueOf(marks));
 }

 // Getters & Setters ...

public Long getId() { return id; }
public void setId(Long id) { this.id = id; }

public String getName() { return name; }
public void setName(String name) { this.name = name; }

public String getEmail() { return email; }           // <-- REQUIRED
public void setEmail(String email) { this.email = email; }

public LocalDate getDob() { return dob; }
public void setDob(LocalDate dob) { this.dob = dob; }

public Integer getMarks() { return marks; }
public void setMarks(Integer marks) { this.marks = marks; }

}


