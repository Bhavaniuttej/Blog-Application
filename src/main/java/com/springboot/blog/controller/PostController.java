package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(
        name="CRUD REST APIs for Post Resource"
)
public class PostController
{
    private PostService postService;

    public PostController(PostService postService)

    {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @SecurityRequirement(
            name="Bear Authentication"
    )
    @Operation(summary = "Create Post REST API",
    description = "Create Post REST API is used to save post into database")
    @ApiResponse
            (
                    responseCode = "201",
                    description = "HTTP Status 201 Created"
            )
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto)
    {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Posts  REST API",
            description = "Get All Posts REST API used to fetch all the posts from the database")
    @ApiResponse
            (
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            )
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value="pageNo",defaultValue= AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY ,required= false )String sortBY,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortDir)
    {
        return postService.getAllPosts(pageNo,pageSize,sortBY,sortDir);
    }
    @Operation(summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API is used to get single post from the database")
    @ApiResponse
            (
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id")long id)
    {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(summary = "Update Post By Id REST API",
            description = "Update Post By Id REST API is used to update a particular post in the database")
    @ApiResponse
            (
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    @SecurityRequirement(
            name="Bear Authentication"
    )
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name = "id")long id)
    {
        PostDto  postResponse = postService.updatePost(postDto,id);

        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    @SecurityRequirement(
            name="Bear Authentication"
    )
    @Operation(summary = "Delete Post By Id REST API",
            description = "Delete Post By Id REST API is used to delete a particular post in the database")
    @ApiResponse
            (
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            )
    public ResponseEntity<String> DeletePostById(@PathVariable(name = "id") long id)
    {
        postService.deletePostById(id);

        return new ResponseEntity<>("Post deleted Successfully",HttpStatus.OK);
    }

    //Build get posts by category id

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId)
    {
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);

        return ResponseEntity.ok(postDtos);
    }

}
