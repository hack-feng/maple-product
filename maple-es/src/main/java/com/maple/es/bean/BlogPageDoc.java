package com.maple.es.bean;

import lombok.Data;

import java.util.List;

@Data
public class BlogPageDoc {

    private Page page;

    private BlogDoc query;

    private List<BlogDoc> result;
}
