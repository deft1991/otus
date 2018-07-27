package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@org.springframework.shell.standard.ShellComponent
@RequiredArgsConstructor
public class ShellComponent {
  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;

  @ShellMethod("Print all books")
  public void printAllBooks() {
	bookService.printBooks();
  }

  @ShellMethod("Print books count")
  public void booksCount() {
	bookService.booksCount();
  }

  @ShellMethod("Print all authors")
  public void printAllAuthors() {
	authorService.printAuthors();
  }

  @ShellMethod("Print all genres")
  public void printAllGenres() {
	genreService.printGenres();
  }



  @ShellMethod("Print all books")
  public void addBook(@ShellOption String bookName,
					  @ShellOption String authorId,
					  @ShellOption String genreId) {
	bookService.addBook(bookName, authorId, genreId);
  }
}
