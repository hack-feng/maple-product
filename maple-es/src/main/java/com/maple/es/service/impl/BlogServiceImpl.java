package com.maple.es.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.maple.es.bean.BlogDoc;
import com.maple.es.bean.BlogPageDoc;
import com.maple.es.bean.Page;
import com.maple.es.mapper.BlogMapper;
import com.maple.es.repository.BlogRepository;
import com.maple.es.service.BlogService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private BlogMapper blogTitleMapper;

    /**
     * 创建单条数据
     * <p>
     * 该方法负责将给定的BlogDoc对象保存到ES中
     *
     * @param blogBean 代表要创建的博客文章的文档对象
     */
    @Override
    public void createBlog(BlogDoc blogBean) {
        blogRepository.save(blogBean);
    }

    /**
     * 更新博客文档
     * <p>
     * 使用Elasticsearch的脚本更新功能来更新博客文档的标题和内容
     *
     * @param blogBean 包含要更新的博客信息的BlogDoc对象，包括博客的标题、内容和ID
     */
    @Override
    public void updateBlog(BlogDoc blogBean) {

        // 构建更新脚本，用于更新Elasticsearch文档的标题和内容
        // ctx._source 固定写法
        String script = "ctx._source.title = '" + blogBean.getTitle() + "';" +
                "ctx._source.content = '" + blogBean.getTitle() + "'";

        // 构建更新查询对象，指定要更新的文档ID和更新脚本
        UpdateQuery build = UpdateQuery.builder(String.valueOf(blogBean.getId())).withScript(script).build();

        // 指定要更新的索引名称
        IndexCoordinates indexCoordinates = IndexCoordinates.of("blog_title");

        // 执行更新操作，并获取更新响应
        elasticsearchRestTemplate.update(build, indexCoordinates);
    }

    /**
     * 初始化数据方法
     * <p>
     * 该方法用于从数据库中加载博客标题的相关信息，并处理关键词字符串，以便于后续的搜索或展示
     * 它首先从数据库中选择所有博客标题的信息，然后遍历这些信息，将每个博客标题的关键词字符串
     * 转换为关键词列表，最后将处理后的博客标题信息保存到博客仓库中
     */
    public void initData() {
        // 从数据库中选择所有博客标题的信息
        List<BlogDoc> list = blogTitleMapper.selectTitleList();

        // 遍历博客标题列表，处理每个博客标题的关键词
        for (BlogDoc blogTitleVo : list) {
            // 如果博客标题的关键词不为空，则将其转换为关键词列表
            if (blogTitleVo.getKeywords() != null) {
                blogTitleVo.setKeywordList(Arrays.asList(blogTitleVo.getKeywords().split(",")));
            }
        }

        // 将处理后的博客标题列表保存到博客仓库中
        blogRepository.saveAll(list);
    }

    /**
     * 删除指定ID的博客
     *
     * @param id 博客的唯一标识符
     */
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public BlogPageDoc getListBlogTitle(BlogPageDoc blogTitleVo) {
        return getList(blogTitleVo);
    }


    /**
     * 根据查询条件和分页信息获取博客列表
     *
     * @param blogPageDoc 包含查询条件和分页信息的博客页面文档
     * @return 返回填充了查询结果的博客页面文档
     */
    private BlogPageDoc getList(BlogPageDoc blogPageDoc) {

        // 获取查询条件
        BlogDoc blogDoc = blogPageDoc.getQuery();
        // 获取分页信息
        Page page = blogPageDoc.getPage();

        // 创建高亮构建器
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        // 设置需要高亮的字段
        highlightBuilder.field("title");
        highlightBuilder.field("description");
        // 设置高亮显示的前缀和后缀
        highlightBuilder.preTags("<span style=\"font-color :red\">");
        highlightBuilder.postTags("</span>");

        // 创建布尔查询构建器
        BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery();
        // 如果标题关键字不为空，则必须匹配keywordList中的关键字
        if (StringUtils.isNotBlank(blogDoc.getTitle())) {
            // list中有一个匹配成功，就代表成功，虽然指定了keywordList的类型，但是在es中还是text（不知道是不是哪个地方bug了，暂未验证），所以指定keywordList.keyword
            queryBuilders.must(QueryBuilders.termsQuery("keywordList.keyword", blogDoc.getKeywordList()));
        }
        // 如果类别ID不为空，则必须匹配类别ID
        if (blogDoc.getCategoryId() != null) {
            queryBuilders.must(QueryBuilders.termQuery("categoryId", blogDoc.getCategoryId()));
        }
        // 如果blogDoc的阅读数量不为空，则在查询中添加阅读数量的范围条件
        if (blogDoc.getReadNum() != null) {
            // 构建一个范围查询，筛选出阅读数量大于当前博客阅读数量的文档
            queryBuilders.must(QueryBuilders.rangeQuery("readNum").gt(blogDoc.getReadNum()));
        }
        // 如果标题不为空，则应匹配标题、描述或内容中的标题
        if (StringUtils.isNotBlank(blogDoc.getTitle())) {
            queryBuilders.should(QueryBuilders.matchQuery("title", blogDoc.getTitle()));
            queryBuilders.should(QueryBuilders.matchQuery("description", blogDoc.getTitle()));
            queryBuilders.should(QueryBuilders.matchQuery("content", blogDoc.getTitle()));
            // 设置至少应匹配的条件数
            queryBuilders.minimumShouldMatch(1);
        }
        // 执行搜索并获取结果
        SearchHits<BlogDoc> searchHits = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                        .withQuery(queryBuilders) // 设置查询条件
                        .withSorts(SortBuilders.fieldSort("readNum").order(SortOrder.DESC)) // 设置排序条件
                        .withHighlightBuilder(highlightBuilder)  // 设置高亮构建器
                        .withPageable(PageRequest.of(page.getPage() - 1, page.getSize()))  // 设置分页信息
                        .build(),
                BlogDoc.class);
        // 创建结果列表
        List<BlogDoc> result = new ArrayList<>();
        // 设置总记录数
        page.setTotal(searchHits.getTotalHits());
        // 遍历搜索结果并处理高亮显示
        searchHits.getSearchHits().forEach(hits -> {
            BlogDoc blogTitle = hits.getContent();
            Map<String, List<String>> highlightFields = hits.getHighlightFields();

            // 如果标题的高亮字段不为空，则替换标题
            if (!CollectionUtils.isEmpty(highlightFields.get("title"))) {
                blogTitle.setTitle(hits.getHighlightFields().get("title").get(0));
            }
            // 如果描述的高亮字段不为空，则替换描述
            if (!CollectionUtils.isEmpty(highlightFields.get("description"))) {
                blogTitle.setDescription(hits.getHighlightFields().get("description").get(0));
            }
            // 将处理后的结果添加到列表中
            result.add(blogTitle);
        });
        // 设置查询结果
        blogPageDoc.setResult(result);
        // 返回填充了查询结果的博客页面文档
        return blogPageDoc;
    }

}
