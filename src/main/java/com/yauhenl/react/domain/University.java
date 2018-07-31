package com.yauhenl.react.domain;

import java.util.Set;

public class University extends Model {
    private Set<Student> students;

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
