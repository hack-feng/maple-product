package com.maple.lucene.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * blog标题
 * </p>
 *
 * @author 笑小枫 <https://xiaoxiaofeng.com/>
 * @since 2023-01-30
 */
@Data
@TableName("blog_title")
public class BlogTitle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String description;
}
