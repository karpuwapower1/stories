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

@Entity
@Table(name = "verification_token")
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

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public Timestamp getTerminationDate() {
        return terminationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTerminationDate(Timestamp terminationDate) {
        this.terminationDate = terminationDate;
    }

    private Timestamp calculateTerminationDate() {
        Calendar termination = Calendar.getInstance();
        termination.add(Calendar.MINUTE, TERMINATION_TIME_IN_MINUTES);
        return new Timestamp(termination.getTimeInMillis());
    }
}