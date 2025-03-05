package com.maple.es.service;

import com.maple.es.bean.BlogDoc;
import com.maple.es.bean.BlogPageDoc;

public interface BlogService {

    void createBlog(BlogDoc blogBean);

    void updateBlog(BlogDoc blogBean);

    void deleteBlog(Long id);

    void initData();

    BlogPageDoc getListBlogTitle(BlogPageDoc blogTitleVo);

}
