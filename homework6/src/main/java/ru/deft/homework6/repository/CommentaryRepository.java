package ru.deft.homework6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.deft.homework6.domain.Commentary;

import java.util.List;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
public interface CommentaryRepository extends JpaRepository<Commentary, UUID> {
  List<Commentary> findAllByBookId(UUID bookId);
}
