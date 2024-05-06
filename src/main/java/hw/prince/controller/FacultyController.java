package hw.prince.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hw.prince.model.Faculty;
import hw.prince.model.Student;
import hw.prince.service.FacultyService;

import java.util.Collection;


@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping("/createFaculty")
    public Faculty createFaculty(Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping("/editFaculty")
    public ResponseEntity<Faculty> editFaculty(Faculty faculty) {
        Faculty faculty1 = facultyService.editFaculty(faculty);
        if (faculty1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty1);
    }

    @DeleteMapping("deleteFaculty/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/findFacultyByColorOrName")
    public ResponseEntity<Collection<Faculty>> findByNameOrColor(@RequestParam(required = false) String color, @RequestParam(required = false) String name) {
        Collection<Faculty> facultyToFind = facultyService.findByNameOrColor(color, name);
        if (facultyToFind == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.findByNameOrColor(color, name));

    }

    @GetMapping("/getAllStudentOfFaculty")
    public ResponseEntity<Collection<Student>> getAllStudentOfFaculty(@RequestParam long id) {
        Collection<Student> studentOfFaculty = facultyService.getAllStudentOfFaculty(id);
        if (studentOfFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentOfFaculty);

    }


}