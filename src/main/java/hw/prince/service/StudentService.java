package hw.prince.service;

import org.springframework.stereotype.Service;
import hw.prince.model.Faculty;
import hw.prince.model.Student;
import hw.prince.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int max, int min) {
        return studentRepository.findByAgeBetween(max, min);
    }

    public Faculty findFacultyOfStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get().getFaculty();
        } else {
            return null;
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public double getAmountOfStudents() {
        return studentRepository.amountOfStudents();
    }

    public double getAverageAge() {
        return studentRepository.averageAge();
    }

    public List<Student> getLastStudents() {
        return studentRepository.getLastStudents();
    }
}