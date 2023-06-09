package ru.otus.library.orm.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.orm.dao.AuthorsDao;
import ru.otus.library.orm.models.Author;

/**
 * Implementation of service responsible for operations with Authors.
 */
@Service
@AllArgsConstructor
public class AuthorsServiceImpl implements AuthorsService {
  private final AuthorsDao authorsDao;

  @Override
  public List<Author> findAuthors() {
    return authorsDao.getAll();
  }

  @Override
  public Author getAuthorById(long authorId) {
    return authorsDao.getById(authorId);
  }
}
