package ru.otus.library.jpa.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.jpa.dao.BooksDao;
import ru.otus.library.jpa.dao.CommentsDao;
import ru.otus.library.jpa.exception.NotFoundException;
import ru.otus.library.jpa.models.Author;
import ru.otus.library.jpa.models.Book;
import ru.otus.library.jpa.models.Comment;
import ru.otus.library.jpa.models.Genre;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = CommentsServiceImpl.class)
public class CommentsServiceImplTest {
  private final String COMMENT_TEXT = "Test comment";
  private final String TITLE = "Test book";
  private final Author AUTHOR = new Author(1L, "Test Author");
  private final Genre GENRE = new Genre(1L, "Test Genre");
  private final Book BOOK = new Book(
          1L, TITLE, 2000, AUTHOR,
          GENRE, List.of());
  private final Comment COMMENT = new Comment(0L, COMMENT_TEXT, BOOK);

  @MockBean
  private CommentsDao commentsDao;
  @MockBean
  private BooksDao booksDao;

  @Autowired
  private CommentsServiceImpl commentsService;

  @DisplayName("Checks that comment is added")
  @Test
  void shouldAddComment() throws NotFoundException {
    doReturn(BOOK).when(booksDao).getById(anyLong());
    doReturn(COMMENT).when(commentsDao).save(any());
    commentsService.addComment(BOOK.getId(), COMMENT_TEXT);
    verify(commentsDao, times(1)).save(COMMENT);
  }

  @DisplayName("Checks that comments are printed in ioService")
  @Test
  void shouldShowComments() {
    List<Comment> comments = List.of(COMMENT);
    Book book = new Book(1L, TITLE, 2000, AUTHOR, GENRE, comments);
    doReturn(book).when(booksDao).getById(anyLong());
    List<Comment> result = commentsService.getBookComments(book.getId());
    assertThat(result).containsExactlyInAnyOrderElementsOf(comments);
  }

  @DisplayName("Checks that comment is deleted")
  @Test
  void shouldDeleteComment() throws NotFoundException {
    doReturn(COMMENT).when(commentsDao).getById(anyLong());
    commentsService.deleteComment(COMMENT.getId());
    verify(commentsDao, times(1)).delete(COMMENT);
  }

  @DisplayName("Checks that comment is updated")
  @Test
  void shouldUpdateComment() throws NotFoundException {
    String commentText = "Updated test";
    Comment expectedComment = new Comment(COMMENT.getId(), commentText, BOOK);
    doReturn(COMMENT).when(commentsDao).getById(anyLong());
    commentsService.updateComment(COMMENT.getId(), commentText);
    verify(commentsDao, times(1)).update(expectedComment);
  }
}