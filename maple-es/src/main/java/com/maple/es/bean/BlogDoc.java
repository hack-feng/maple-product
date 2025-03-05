package com.maple.es.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/1/30
 */
@Data
@Document(indexName = "blog_title")
public class BlogDoc {

    @Id
    private Long id;

    @Field(type = FieldType.Long)
    private Long categoryId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private String description;

    @Field(type = FieldType.Keyword)
    private String keywords;

    @Field(type = FieldType.Keyword)
    private List<String> keywordList;

    private String status;

    @Field(type = FieldType.Integer)
    private Integer readNum;

    @Field(type = FieldType.Integer)
    private Integer collectNum;

    @Field(type = FieldType.Date)
    private Date createTime;
}
