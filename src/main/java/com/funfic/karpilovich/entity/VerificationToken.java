package com.funfic.karpilovich.entity;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "verification_token")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class VerificationToken {

    private static final int TERMINATION_TIME_IN_MINUTES = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private String token;
    @Column(name = "termination_date")
    private Timestamp terminationDate;

    public VerificationToken() {
        terminationDate = calculateTerminationDate();
    }

    public VerificationToken(User user, String token) {
        this();
        this.user = user;
        this.token = token;
    }

    private Timestamp calculateTerminationDate() {
        Calendar termination = Calendar.getInstance();
        termination.add(Calendar.MINUTE, TERMINATION_TIME_IN_MINUTES);
        return new Timestamp(termination.getTimeInMillis());
    }
}