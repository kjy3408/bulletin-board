package com.portfolio.bulletinboard.dto.post;

import com.portfolio.bulletinboard.entity.Comment;
import com.portfolio.bulletinboard.entity.File;
import com.portfolio.bulletinboard.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponseDto {

	private int postId;
	private String postTitle;
	private String content;
	private String uploadDate;
	private int views;
	private int categoryId;
	
	private Comment comment;
	private File file;
	private User user;
}
