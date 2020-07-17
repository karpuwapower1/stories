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
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "genres")
@Getter
@Setter
@EqualsAndHashCode(exclude = "books")
@ToString(exclude = "books")
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Column(unique = true)
    private String name;
    @ManyToMany
    @JoinTable(name = "books_genres", 
                joinColumns = @JoinColumn(name = "genres_id"), 
                inverseJoinColumns = @JoinColumn(name = "books_id"),
                foreignKey = @ForeignKey(name="FK_genres_books"),
                inverseForeignKey = @ForeignKey(name="FK_books_genres"),
                uniqueConstraints = @UniqueConstraint(columnNames = {"genres_id", "books_id" }))
    @JsonIgnore
    private List<Book> books;

}
