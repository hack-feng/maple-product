package com.maple.es.controller;

import com.maple.es.bean.BlogDoc;
import com.maple.es.bean.BlogPageDoc;
import com.maple.es.service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/blog")
@AllArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/save")
    public void save(@RequestBody BlogDoc blogBean) {
        blogBean.setCreateTime(new Date());
        blogService.createBlog(blogBean);
    }

    @PostMapping("/update")
    public void update(@RequestBody BlogDoc blogBean) {
        blogService.updateBlog(blogBean);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        blogService.deleteBlog(id);
    }

    @GetMapping("/initData")
    public void initData() {
        blogService.initData();
    }

    @PostMapping("/getListBlogTitle")
    public BlogPageDoc getListBlogTitle(@RequestBody BlogPageDoc blogTitleVo) {
        return blogService.getListBlogTitle(blogTitleVo);
    }
}
