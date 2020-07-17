package com.funfic.karpilovich.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tags")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(unique = true)
    private String name;
    @ManyToMany
    @JoinTable(name = "books_tags", 
                joinColumns = @JoinColumn(name = "tags_id"), 
                inverseJoinColumns = @JoinColumn(name = "books_id"), 
                foreignKey = @ForeignKey(name = "FK_tags_books"),
                inverseForeignKey = @ForeignKey(name = "FK_books_tags"), 
                uniqueConstraints = @UniqueConstraint(columnNames = {"books_id", "tags_id"}))
    @JsonIgnore
    private List<Book> books;
}