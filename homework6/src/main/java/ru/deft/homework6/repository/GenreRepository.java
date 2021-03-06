package ru.deft.homework6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.deft.homework6.domain.Genre;

import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
public interface GenreRepository extends JpaRepository<Genre, UUID> {
}
