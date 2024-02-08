package com.bookstore.com.domain.student;


import com.bookstore.com.domain.borrow.Borrow;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Cacheable
@Table(name = "student")
@NamedEntityGraph(name = "std-borrow" , attributeNodes = @NamedAttributeNode("borrowStd"))
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "std_id", nullable = false)
    private Long std_id;


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "email", nullable = false)
    private String email;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "students", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Borrow> borrowStd = new ArrayList<>();

    public void addBorrow(Borrow borrow){
        borrowStd.add(borrow);
        borrow.setStudents(this);
    }

    public void removeBorrow(Borrow borrow){
        borrowStd.remove(borrow);
        borrow.setStudents(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(std_id, student.std_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(std_id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "std_id=" + std_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}