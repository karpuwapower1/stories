package com.funfic.karpilovich.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "chapters")
@Getter
@Setter
@EqualsAndHashCode(exclude = "book")
@ToString(exclude = "book")
@NoArgsConstructor
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @Column(columnDefinition = "text")
    @NotEmpty
    private String text;
    @Column(columnDefinition = "SMALLINT")
    private short number;
    @ManyToOne
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_books_chapters"))
    @JsonIgnore
    private Book book;

}