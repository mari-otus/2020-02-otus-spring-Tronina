package ru.otus.spring.mappers;

import org.mapstruct.Mapper;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.CommentDto;

/**
 * @author Mariya Tronina
 */
@Mapper
public interface CommentMapper {

    CommentDto commentToCommentDto(Comment comment);
    Comment commentDtoToComment(CommentDto commentDto);
}
