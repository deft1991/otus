package ru.deft.homework6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.deft.homework6.domain.BookToAuthor;

import java.util.UUID;

/**
 * Created by Sergey Golitsyn (deft) on 04.08.2018
 */
public interface BookToAuthorRepositoty extends JpaRepository<BookToAuthor, UUID> {
}
