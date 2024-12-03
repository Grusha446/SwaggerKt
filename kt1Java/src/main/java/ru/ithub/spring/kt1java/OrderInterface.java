package ru.ithub.spring.kt1java;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInterface extends JpaRepository<Orders, Long> {
}
