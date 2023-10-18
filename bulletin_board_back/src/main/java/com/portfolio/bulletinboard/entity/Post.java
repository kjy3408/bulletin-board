package com.portfolio.bulletinboard.entity;

import java.util.List;

import com.portfolio.bulletinboard.dto.post.PostResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

	private int postId;
	private String postTitle;
	private String content;
	private int views;
	private String uploadDate;
	private int categoryId;
	private int userId;
	private User user;
	private Comment comment;
	private File file;
  
    public String getTitle() {
        return postTitle;
    }
    
    public PostResponseDto toDto() {
    	return PostResponseDto.builder()
    				.postId(postId)
    				.postTitle(postTitle)
    				.content(content)
    				.views(views)
    				.uploadDate(uploadDate)
    				.categoryId(categoryId)
    				.user(user)
    				.comment(comment)
    				.file(file)
    				.build();
    }
}
