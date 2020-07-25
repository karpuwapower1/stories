package com.funfic.karpilovich.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.projection.UserProjection;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<UserProjection> findUserProjectionById(Long id);
    
    Optional<UserProjection> findUserProjectionByUsername(String username);

    Page<UserProjection> findBy(Pageable of);
}