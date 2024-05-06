package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import hw.prince.model.Faculty;
import hw.prince.repository.FacultyRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @InjectMocks
    FacultyService out;

    @Mock
    FacultyRepository repository;

    Faculty FACULTY_1 = new Faculty(1L, "Griffindor", "red", null);
    Faculty FACULTY_2 = new Faculty(2L, "Ravenclaw", "blue", null);
    List<Faculty> facultyList;

    @BeforeEach
    void setUp() {
        FACULTY_1.setId(1L);
        FACULTY_2.setId(2L);
        facultyList = new ArrayList<>(List.of(FACULTY_1, FACULTY_2));
    }

    @Test
    void createFacultyCorrectly() {
        Mockito.when(repository.save(FACULTY_1)).thenReturn(FACULTY_1);
        out.createFaculty(FACULTY_1);
        Mockito.verify(repository, Mockito.times(1)).save(FACULTY_1);
        assertEquals(FACULTY_1, out.createFaculty(FACULTY_1));
    }

    @Test
    void findFacultyByIdCorrectly() {
        long id = 2L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(FACULTY_2));
        assertEquals(FACULTY_2, out.findFaculty(FACULTY_2.getId()));
        Mockito.verify(repository, Mockito.times(1)).findById(id);
    }


    @Test
    void deleteFacultyByIdCorrectly() {
        long id = 1L;
        Mockito.doNothing().when(repository).deleteById(id);
        out.deleteFaculty(id);
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void findByColor() {
        List<Faculty> EXP = List.of(FACULTY_1);
        String color = "red";
        String name = "Griffindor";
        Mockito.when(repository.getByNameIgnoreCaseOrColorIgnoreCase(color, name)).thenReturn(EXP);
        assertIterableEquals(EXP, out.findByNameOrColor(color, name));
        Mockito.verify(repository, Mockito.times(1)).getByNameIgnoreCaseOrColorIgnoreCase(color, name);
    }
}