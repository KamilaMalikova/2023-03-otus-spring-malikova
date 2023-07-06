package ru.otus.library.mvc.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.library.mvc.models.Comment;

/**
 * Comment dto for controller.
 */
@AllArgsConstructor
@Data
public class CommentDto {
  private String id;
  private String commentText;

  public static CommentDto toDto(Comment comment) {
    return new CommentDto(comment.getId(), comment.getCommentText());
  }
}
