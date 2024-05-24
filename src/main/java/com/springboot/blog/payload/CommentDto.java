package com.springboot.blog.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto
{
    private long id;
    @NotEmpty(message = "Name Should not be Null or empty")
    private String name;

    @NotEmpty(message = "Email Should not be nuyll or empty")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10 ,message = "Content Body must be minimum 10 characters")
    private String body;
}
