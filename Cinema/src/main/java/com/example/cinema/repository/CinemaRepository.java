package com.example.cinema.repository;

import com.example.cinema.entity.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Page<Cinema> findAllByIsEnabledTrue(Pageable pageable);
}
