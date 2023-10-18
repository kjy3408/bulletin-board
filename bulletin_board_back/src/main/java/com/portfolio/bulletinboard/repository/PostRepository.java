package com.portfolio.bulletinboard.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.bulletinboard.entity.Category;
import com.portfolio.bulletinboard.entity.Post;

@Mapper
public interface PostRepository {

	public List<Category> getCategories();
	public int registerPost(Post post);
	public int registerFile(List<String> fileName, int postId);
	public List<Post> getPosts(Map<String, Object> map);
	public int postTotalCount();
	public Post readPostData(int postId);
}
