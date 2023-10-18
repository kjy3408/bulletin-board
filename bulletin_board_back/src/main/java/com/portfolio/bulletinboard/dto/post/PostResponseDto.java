package com.portfolio.bulletinboard.dto.post;

import java.util.List;

import com.portfolio.bulletinboard.entity.Category;
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
	
	private Category category;
	private Comment comment;
	private List<File> file;
	private User user;
}
