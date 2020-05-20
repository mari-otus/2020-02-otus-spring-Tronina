package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.mappers.CommentMapper;
import ru.otus.spring.service.comment.CommentBookService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с комментариями.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@RestController
public class CommentBookController {

    /**
     * Сервис для работы с комментариями.
     */
    private final CommentBookService commentBookService;

    private final CommentMapper commentMapper;

    /**
     * Возвращает список всех комментариев у книги.
     *
     * @param id идентификатор книги
     * @return список комментариев
     */
    @GetMapping("books/{id}/comments")
    public ResponseEntity<List<CommentDto>> getBookComments(@PathVariable Long id) {
        List<Comment> comments = commentBookService.getAllComment(id);
        return CollectionUtils.isEmpty(comments)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok()
                .body(comments.stream()
                        .map(comment -> commentMapper.commentToCommentDto(comment))
                        .collect(Collectors.toList()));
    }

}
