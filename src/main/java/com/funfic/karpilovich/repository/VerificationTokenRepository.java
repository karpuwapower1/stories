package com.funfic.karpilovich.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.funfic.karpilovich.domain.VerificationToken;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);
}