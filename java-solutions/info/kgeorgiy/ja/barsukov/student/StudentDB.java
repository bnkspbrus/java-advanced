package info.kgeorgiy.ja.barsukov.student;

import info.kgeorgiy.java.advanced.student.GroupName;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentQuery;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {
    private static final Comparator<Student> SORT_BY_NAME =
            // :NOTE: Упростить
            Comparator.comparing(Student::getLastName, Comparator.reverseOrder())
                    .thenComparing(Student::getFirstName, Comparator.reverseOrder())
                    .thenComparingInt(Student::getId);

    @Override
    public List<String> getFirstNames(final List<Student> students) {
        return getProperty(Student::getFirstName, students);
    }

    @Override
    public List<String> getLastNames(final List<Student> students) {
        return getProperty(Student::getLastName, students);
    }

    @Override
    public List<GroupName> getGroups(final List<Student> students) {
        return getProperty(Student::getGroup, students);
    }

    @Override
    public List<String> getFullNames(final List<Student> students) {
        return getProperty(student -> student.getFirstName() + " " + student.getLastName(), students);
    }

    private <T> List<T> getProperty(final Function<? super Student, ? extends T> mapper, final List<Student> students) {
        return students.stream().<T>map(mapper).collect(Collectors.toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(final List<Student> students) {
        return students.stream().map(Student::getFirstName).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public String getMaxStudentFirstName(final List<Student> students) {
        // :NOTE: Дубли
        return students.stream().max(Comparator.comparingInt(Student::getId)).map(Student::getFirstName).orElse("");
    }

    @Override
    public List<Student> sortStudentsById(final Collection<Student> students) {
        return sortCollectionByComparator(students, Comparator.comparingInt(Student::getId));
    }

    @Override
    public List<Student> sortStudentsByName(final Collection<Student> students) {
        return sortCollectionByComparator(students, SORT_BY_NAME);
    }

    private List<Student> sortCollectionByComparator(final Collection<Student> students, final Comparator<Student> comparator) {
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }

    private List<Student> findStudentsByPredicate(final Collection<Student> students, final Predicate<Student> predicate) {
        return students.stream().filter(predicate).sorted(SORT_BY_NAME).collect(Collectors.toList());
    }

    // :NOTE: Дубли
    @Override
    public List<Student> findStudentsByFirstName(final Collection<Student> students, final String name) {
        return findStudentsByPredicate(students, student -> student.getFirstName().equals(name));
    }

    @Override
    public List<Student> findStudentsByLastName(final Collection<Student> students, final String name) {
        return findStudentsByPredicate(students, student -> student.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(final Collection<Student> students, final GroupName group) {
        return findStudentsByPredicate(students, groupPredicate(group));
    }

    private Predicate<Student> groupPredicate(final GroupName group) {
        return student -> student.getGroup() == group;
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(final Collection<Student> students, final GroupName group) {
        return students.stream().filter(groupPredicate(group)).collect(
                Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(String::compareTo)));
    }
}
