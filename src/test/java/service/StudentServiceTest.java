package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import hw.prince.model.Student;
import hw.prince.repository.StudentRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    StudentService out;

    @Mock
    StudentRepository repository;

    Student STUDENT_1 = new Student(1L, "Harry", 12, null);
    Student STUDENT_2 = new Student(2L, "Ron", 11, null);
    List<Student> studentList;

    @BeforeEach
    void setUp() {
        STUDENT_1.setId(1L);
        STUDENT_2.setId(2L);
        studentList = new ArrayList<>(List.of(STUDENT_1, STUDENT_2));
    }

    @Test
    void createStudentCorrectly() {
        Mockito.when(repository.save(STUDENT_1)).thenReturn(STUDENT_1);
        out.createStudent(STUDENT_1);
        Mockito.verify(repository, Mockito.times(1)).save(STUDENT_1);
        assertEquals(STUDENT_1, out.createStudent(STUDENT_1));
    }

    @Test
    void findStudentCorrectly() {
        long ID = 2L;
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(STUDENT_2));
        assertEquals(STUDENT_2, out.findStudent(STUDENT_2.getId()));
        Mockito.verify(repository, Mockito.times(1)).findById(2L);
    }


    @Test
    void deleteStudent() {
        long ID = 2L;
        Mockito.doNothing().when(repository).deleteById(ID);
        out.deleteStudent(ID);
        Mockito.verify(repository, Mockito.times(1)).deleteById(ID);
    }

    @Test
    void findByAge() {
        List<Student> EXP = List.of(STUDENT_1);
        int age = 12;
        Mockito.when(repository.findByAge(age)).thenReturn(EXP);
        assertIterableEquals(EXP, out.findByAge(age));
        Mockito.verify(repository, Mockito.times(1)).findByAge(age);
    }
}