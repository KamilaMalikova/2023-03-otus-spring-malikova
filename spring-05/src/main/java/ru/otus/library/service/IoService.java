package ru.otus.library.service;

import java.util.List;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;

/**
 * Reads and writes into IO.
 */
public interface IoService {
  void writeLine(String line);

  void writeBook(Book book);

  void writeBooks(List<Book> books);

  void writeAuthor(Author author);

  void writeGenre(Genre genre);
}
