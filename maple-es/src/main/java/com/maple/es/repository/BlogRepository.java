package com.maple.es.repository;

import com.maple.es.bean.BlogDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BlogRepository extends ElasticsearchRepository<BlogDoc, Long> {

}
