package com.portfolio.bulletinboard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.bulletinboard.dto.post.PostResponseDto;
import com.portfolio.bulletinboard.entity.Category;
import com.portfolio.bulletinboard.entity.Post;
import com.portfolio.bulletinboard.repository.PostRepository;
import com.portfolio.bulletinboard.security.PrincipalUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	
	@Value("${file.path}")
	private String filePath;
	
	private final PostRepository postRepository;
	
	public List<Category> getCategories() {
		
		return postRepository.getCategories();
	}
	
	public int registerPost(String postTitle, String content, int categoryId, List<MultipartFile> files) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

	    // 게시물(Post) 등록
	    Post postEntity = Post.builder()
	            .postTitle(postTitle)
	            .content(content)
	            .categoryId(categoryId)
	            .userId(principalUser.getUserId())
	            .build();

	    postRepository.registerPost(postEntity);

	    List<String> fileNames = new ArrayList<>();

	    for (MultipartFile file : files) {
	        String originFileName = file.getOriginalFilename();
	        String extension = originFileName.substring(originFileName.lastIndexOf("."));
	        String tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;
	        String absolutePath = Paths.get("").toAbsolutePath().toString().replace("\\", "/");
	        String resourcePath = absolutePath + filePath + "post/" + tempFileName;
	        Path uploadPath = Paths.get(resourcePath);

	        try {
	            Files.write(uploadPath, file.getBytes());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        fileNames.add(tempFileName);
	    }

	    // 파일 이름(File Name)을 DB에 등록
	    postRepository.registerFile(fileNames, postEntity.getPostId());
	    return 0;
	}
	
	public Map<String, Object> getPosts(int page, String searchValue, int categoryId) {
		Map<String, Object> map = new HashMap<>();
		map.put("index", (page - 1) * 15);
		map.put("searchValue", searchValue);
		map.put("categoryId", categoryId);
		
		List<PostResponseDto> list = new ArrayList<>();
		postRepository.getPosts(map).forEach(post -> {
			list.add(post.toDto());
		});;
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("totalCount", postRepository.postTotalCount());
		responseMap.put("posts", list);
				
		return responseMap;
	}
}
