package com.school.service;

import com.school.entity.Student;
import com.school.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testAddStudent() {
        Student student = new Student(1L, "Kamal", "kamal@gmail.com", 23);

        when(studentRepository.save(student)).thenReturn(student);

        Student savedStudent = studentService.addStudent(student);

        assertNotNull(savedStudent);
        assertEquals("Kamal", savedStudent.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testGetAllStudents() {
        Student s1 = new Student(1L, "Kamal", "k@gmail.com", 23);
        Student s2 = new Student(2L, "Ravi", "r@gmail.com", 24);

        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));

        List<Student> students = studentService.getAllStudents();

        assertEquals(2, students.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById_Success() {
        Student student = new Student(1L, "Kamal", "k@gmail.com", 23);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1L);

        assertEquals("Kamal", result.getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> studentService.getStudentById(1L)
        );

        assertTrue(exception.getMessage().contains("Student not found"));
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
