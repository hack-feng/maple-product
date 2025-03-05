package com.maple.es.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maple.es.bean.BlogDoc;

import java.util.List;

/**
 * <p>
 * Blog简介 Mapper 接口
 * </p>
 *
 * @author 笑小枫
 * @since 2023-01-30
 */
public interface BlogMapper extends BaseMapper<BlogDoc> {

    List<BlogDoc> selectTitleList();
}
