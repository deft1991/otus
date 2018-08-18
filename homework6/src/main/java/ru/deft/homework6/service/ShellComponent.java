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
  private final CommentService commentService;

  @ShellMethod("Print all books")
  public void printBooks() {
	bookService.printBooks();
  }

  @ShellMethod("Print all authors")
  public void printAuthors() {
	authorService.printAuthors();
  }

  @ShellMethod("Print count authors")
  public void countAuthors() {
	authorService.count();
  }

  @ShellMethod("Print all genres")
  public void printGenres() {
	genreService.printGenres();
  }

  @ShellMethod("Print book by ID")
  public void printBookById(@ShellOption String id) {
	bookService.printOne(id);
  }

  @ShellMethod("Print author by ID")
  public void printAuthorById(@ShellOption String id) {
	authorService.printAuthorById(id);
  }

  @ShellMethod("Print genre by ID")
  public void printGenreById(@ShellOption String id) {
	genreService.printGenreById(id);
  }

  @ShellMethod("Print books count")
  public void booksCount() {
	bookService.booksCount();
  }

  @ShellMethod("Add book")
  public void addBook(@ShellOption String bookName) {
	bookService.addBook(bookName);
  }

  @ShellMethod("Add book")
  public void addBookWithAuthor(@ShellOption String bookName,
								@ShellOption String authorId) {
	bookService.addBook(bookName, authorId);
  }

  @ShellMethod("Add book")
  public void addFullBook(@ShellOption String bookName,
						  @ShellOption String authorId,
						  @ShellOption String genreId) {
	bookService.addBook(bookName, authorId, genreId);
  }

  @ShellMethod("Add author")
  public void addAuthor(@ShellOption String name) {
	authorService.addAuthor(name);
  }

  @ShellMethod("Add genre")
  public void addGenre(@ShellOption String name) {
	genreService.addGenre(name);
  }

  @ShellMethod("Add book comment")
  public void addComment(@ShellOption String book, @ShellOption String comment) {
	commentService.addComment(book, comment);
  }

  @ShellMethod("Read book comment")
  public void readComment(@ShellOption String book) {
	commentService.readComment(book);
  }
}
