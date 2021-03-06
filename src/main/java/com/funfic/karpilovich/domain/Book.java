package com.funfic.karpilovich.domain;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "books")
@Getter
@Setter
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @DateTimeFormat
    @Column
    @Temporal(TemporalType.DATE)
    private Calendar created;
    @DateTimeFormat
    @Temporal(TemporalType.DATE)
    private Calendar updated;
    @ManyToMany
    @JoinTable(name = "books_genres", 
                joinColumns = @JoinColumn(name = "books_id"), 
                inverseJoinColumns = @JoinColumn(name = "genres_id"  ),
                foreignKey = @ForeignKey(name="FK_books_genres"),
                inverseForeignKey = @ForeignKey(name="FK_genres_books"),
                uniqueConstraints = @UniqueConstraint(columnNames = {"genres_id", "books_id" }))
    private Set<Genre> genres;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("number")
    private Set<Chapter> chapters;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_users_books"))
    @JsonIgnore
    private User user;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "books_tags", 
                joinColumns = @JoinColumn(name = "books_id"), 
                inverseJoinColumns = @JoinColumn(name = "tags_id"), 
                foreignKey = @ForeignKey(name = "FK_books_tags"),
                inverseForeignKey = @ForeignKey(name = "FK_tags_books"), 
                uniqueConstraints = @UniqueConstraint(columnNames = {"books_id", "tags_id"}))
    private Set<Tag> tags;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Comment> comments;
}