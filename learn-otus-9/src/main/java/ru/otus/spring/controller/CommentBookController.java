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
 * @author Mariya Tronina mariya.tronina@rtlabs.ru
 */
@RequiredArgsConstructor
@RestController
public class CommentBookController {

    private final CommentBookService commentBookService;

    private final CommentMapper commentMapper;

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
