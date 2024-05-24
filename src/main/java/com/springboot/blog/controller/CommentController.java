package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(
        name="CRUD REST APIs for Comment Resource"
)
public class CommentController
{
    private CommentService commentService;

    public CommentController(CommentService commentService)
    {
        this.commentService = commentService;
    }

    @Operation(summary = "Create Comment REST API",
            description = "Create Comment REST API is used to save comment into database")
    @ApiResponse
            (
                    responseCode = "201",
                    description = "HTTP Status 201 Created"
            )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId")long postId,
                                                    @Valid @RequestBody CommentDto commentDto)
    {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Comments By Post Id REST API",
            description = "Get All Comments By Post Id REST API used to fetch all the Comments with particular post Id from the database")
    @ApiResponse
            (
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            )
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId)
    {
        return commentService.getCommentsByPostId(postId);
    }
    @Operation(summary = "Get Comment By  Id REST API",
            description = "Get  Comment By  Id REST API used to fetch particular Comment  by Id  from the database")
    @ApiResponse
            (
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            )
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId)
    {
        CommentDto commentDto = commentService.getCommentById(postId, commentId);

        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
    @Operation(summary = "Update Comment By  Id REST API",
            description = "Update  Comment By  Id REST API used to update particular Comment  by Id  from the database")
    @ApiResponse
            (
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            )
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value="id") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto)
    {
        CommentDto updatedComment = commentService.updateComment(postId,commentId,commentDto);

        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }

    @Operation(summary = "Delete Comment By  Id REST API",
            description = "Get  Delete By  Id REST API used to delete particular Comment  by Id  from the database")
    @ApiResponse
            (
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            )
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public  ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                 @PathVariable(value="id") Long commentId)
    {
        commentService.deleteComment(postId,commentId);

        return new ResponseEntity<>("Comment Deleted Successfully",HttpStatus.OK);
    }
}
