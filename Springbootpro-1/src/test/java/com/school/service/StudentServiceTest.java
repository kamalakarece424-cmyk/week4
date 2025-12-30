package com.school.service;
import com.school.entity.Student;
import com.school.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repo;

    @InjectMocks
    private StudentService service;

    // ---------- CREATE ----------
    @Test
    @DisplayName("create(): should save and return the persisted student")
    void create_shouldSaveStudent() {
        Student input = new Student(null, "Amit", "amit@example.com",
                LocalDate.of(2005, 3, 15), 82);

        Student persisted = new Student(1L, "Amit", "amit@example.com",
                input.getDob(), 82);

        when(repo.save(input)).thenReturn(persisted);

        Student saved = service.create(input);

        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo("Amit");
        verify(repo, times(1)).save(input);
        verifyNoMoreInteractions(repo);
    }

    // ---------- FIND ALL ----------
    @Test
    @DisplayName("findAll(): should return all students")
    void findAll_shouldReturnList() {
        List<Student> mockList = List.of(
                new Student(1L, "Amit", "amit@example.com", LocalDate.of(2005,3,15), 82),
                new Student(2L, "Priya", "priya@example.com", LocalDate.of(2006,7,22), 68)
        );

        when(repo.findAll()).thenReturn(mockList);

        List<Student> students = service.findAll();

        assertThat(students).hasSize(2);
        assertThat(students).extracting(Student::getName).containsExactly("Amit", "Priya");
        verify(repo, times(1)).findAll();
        verifyNoMoreInteractions(repo);
    }

    // ---------- FIND BY ID (SUCCESS) ----------
    @Test
    @DisplayName("findById(): when exists should return student")
    void findById_whenExists_returnsStudent() {
        Student existing = new Student(10L, "Kiran", "kiran@example.com",
                LocalDate.of(2005,11,9), 91);

        when(repo.findById(10L)).thenReturn(Optional.of(existing));

        Student out = service.findById(10L);

        assertThat(out.getEmail()).isEqualTo("kiran@example.com");
        assertThat(out.getMarks()).isEqualTo(91);
        verify(repo, times(1)).findById(10L);
        verifyNoMoreInteractions(repo);
    }

    // ---------- FIND BY ID (NOT FOUND) ----------
    @Test
    @DisplayName("findById(): when missing should throw ResourceNotFoundException")
    void findById_whenMissing_throwsNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(repo, times(1)).findById(99L);
        verifyNoMoreInteractions(repo);
    }

    // ---------- UPDATE ----------
    @Test
    @DisplayName("update(): should update fields and save")
    void update_shouldPersistChanges() {
        Student existing = new Student(1L, "Neha", "neha@example.com",
                LocalDate.of(2006,1,30), 74);

        Student payload = new Student(null, "Neha Singh", "neha@example.com",
                existing.getDob(), 85);


when(repo.findById(1L)).thenReturn(Optional.of(existing)); // 1L, not 1
Student out = service.findById(1L);
verify(repo).findById(1L);


        Student updated = service.update(1L, payload);

        assertThat(updated.getName()).isEqualTo("Neha Singh");
        assertThat(updated.getMarks()).isEqualTo(85);
        assertThat(updated.getEmail()).isEqualTo("neha@example.com");

        // Verify interactions and that repo.save was called with the modified entity
        verify(repo, times(1)).findById(1L);
        verify(repo, times(1)).save(argThat(s ->
                s.getId().equals(1L) &&
                "Neha Singh".equals(s.getName()) &&
                Integer.valueOf(85).equals(s.getMarks())
        ));
        verifyNoMoreInteractions(repo);
    }

    // ---------- UPDATE (NOT FOUND) ----------
    @Test
    @DisplayName("update(): when target not found should throw ResourceNotFoundException")
    void update_whenMissing_throwsNotFound() {
        Student payload = new Student(null, "X", "x@example.com", LocalDate.now(), 50);

        when(repo.findById(123L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(123L, payload))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("123");

        verify(repo, times(1)).findById(123L);
        // save must NOT be called
        verify(repo, never()).save(any());
        verifyNoMoreInteractions(repo);
    }

    // ---------- DELETE ----------
    @Test
    @DisplayName("delete(): should remove existing entity")
    void delete_shouldRemoveEntity() {
        Student existing = new Student(2L, "Rahul", "rahul@example.com",
                LocalDate.of(2005,5,18), 59);

        when(repo.findById(2L)).thenReturn(Optional.of(existing));

        service.delete(2L);

        verify(repo, times(1)).findById(2L);
        verify(repo, times(1)).delete(existing);
        verifyNoMoreInteractions(repo);
    }

    // ---------- DELETE (NOT FOUND) ----------
    @Test
    @DisplayName("delete(): when missing should throw ResourceNotFoundException")
    void delete_whenMissing_throwsNotFound() {
        when(repo.findById(777L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(777L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("777");

        verify(repo, times(1)).findById(777L);
        verify(repo, never()).delete(any());
        verifyNoMoreInteractions(repo);
    }
}


