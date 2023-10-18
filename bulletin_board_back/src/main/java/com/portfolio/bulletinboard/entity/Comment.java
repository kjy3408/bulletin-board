package com.portfolio.bulletinboard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

	private int commentId;
	private String commentContent;
	private String commentCreateDate;
	private int commentAuthorId;
	private User user;
}
