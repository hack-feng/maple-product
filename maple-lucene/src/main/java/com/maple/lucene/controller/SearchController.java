package com.maple.lucene.controller;

import com.maple.lucene.bean.BlogTitle;
import com.maple.lucene.mapper.BlogTitleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/12/13
 */
@RestController
@RequestMapping("/lucene")
@AllArgsConstructor
@Slf4j
public class SearchController {

    private final BlogTitleMapper blogTitleMapper;

    /**
     * 简单搜索
     */
    @RequestMapping("/searchText")
    public List<BlogTitle> searchText(String text) throws IOException, ParseException {
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        QueryParser parser = new QueryParser("title", new IKAnalyzer());
        // 创建查询对象
        // Query 这是一个抽象类，他有多个实现，比如 TermQuery, BooleanQuery, PrefixQuery. 这个类的目的是把用户输入的查询字符串封装成 Lucene 能够识别的 Query。
        Query query = parser.parse(text);
        // 获取前十条记录
        TopDocs topDocs = searcher.search(query, 10);
        // 获取总条数
        log.info("本次搜索共找到" + topDocs.totalHits + "条数据");
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<BlogTitle> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docId = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docId);
            BlogTitle content = blogTitleMapper.selectById(doc.get("id"));
            list.add(content);
        }
        return list;
    }


    /**
     * 一个关键词，在多个字段里面搜索
     */
    @RequestMapping("/searchTextMore")
    public List<BlogTitle> searchTextMore(String text) throws IOException, ParseException {
        String[] str = {"title", "description"};
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new IKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        // 获取前十条记录
        TopDocs topDocs = searcher.search(query, 10);
        // 获取总条数
        log.info("本次搜索共找到" + topDocs.totalHits + "条数据");
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<BlogTitle> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docId = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docId);
            BlogTitle content = blogTitleMapper.selectById(doc.get("id"));
            list.add(content);
        }
        return list;
    }

    /**
     * 搜索结果高亮显示
     */
    @RequestMapping("/searchTextHighlighter")
    public List<BlogTitle> searchTextHighlighter(String text) throws IOException, ParseException, InvalidTokenOffsetsException {
        String[] str = {"title", "description"};
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new IKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        // 获取前十条记录
        TopDocs topDocs = searcher.search(query, 10);
        // 获取总条数
        log.info("本次搜索共找到" + topDocs.totalHits + "条数据");

        //高亮显示
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        //高亮后的段落范围在100字内
        Fragmenter fragmenter = new SimpleFragmenter(100);
        highlighter.setTextFragmenter(fragmenter);

        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<BlogTitle> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docId = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docId);
            BlogTitle content = blogTitleMapper.selectById(doc.get("id"));
            //处理高亮字段显示
            String title = highlighter.getBestFragment(new IKAnalyzer(), "title", doc.get("title"));
            if (title == null) {
                title = content.getTitle();
            }
            // 因为创建索引的时候description设置的Field.Store.NO，所以这里doc没有description数据，取不出来值，设为YES则可以，可以断点看一下
//            String description = highlighter.getBestFragment(new IKAnalyzer(), "description", doc.get("description"));
//            if (description == null) {
//                description = content.getDescription();
//            }
//            content.setDescription(description);
            content.setDescription(content.getDescription());
            content.setTitle(title);
            list.add(content);
        }
        return list;
    }


    /**
     * 分页搜索
     */
    @RequestMapping("/searchTextPage")
    public List<BlogTitle> searchTextPage(String text) throws IOException, ParseException, InvalidTokenOffsetsException {
        String[] str = {"title", "description"};
        int page = 1;
        int pageSize = 5;
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new IKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        // 分页获取数据
        TopDocs topDocs = searchByPage(page, pageSize, searcher, query);
        // 获取总条数
        log.info("本次搜索共找到" + topDocs.totalHits + "条数据");

        //高亮显示
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        //高亮后的段落范围在100字内
        Fragmenter fragmenter = new SimpleFragmenter(100);
        highlighter.setTextFragmenter(fragmenter);

        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<BlogTitle> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docId = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docId);
            BlogTitle content = blogTitleMapper.selectById(doc.get("id"));
            //处理高亮字段显示
            String title = highlighter.getBestFragment(new IKAnalyzer(), "title", doc.get("title"));
            if (title == null) {
                title = content.getTitle();
            }
            String description = highlighter.getBestFragment(new IKAnalyzer(), "description", content.getDescription());
            content.setDescription(description);
            content.setTitle(title);
            list.add(content);
        }
        return list;
    }

    private TopDocs searchByPage(int page, int perPage, IndexSearcher searcher, Query query) throws IOException {
        TopDocs result;
        if (query == null) {
            log.info(" Query is null return null ");
            return null;
        }
        ScoreDoc before = null;
        if (page != 1) {
            TopDocs docsBefore = searcher.search(query, (page - 1) * perPage);
            ScoreDoc[] scoreDocs = docsBefore.scoreDocs;
            if (scoreDocs.length > 0) {
                before = scoreDocs[scoreDocs.length - 1];
            }
        }
        result = searcher.searchAfter(before, query, perPage);
        return result;
    }

    /**
     * 多关键词搜索
     */
    @GetMapping("/searchTextMoreParam")
    public List<BlogTitle> searchTextMoreParam(String text) throws IOException, ParseException, InvalidTokenOffsetsException {
        String[] str = {"title", "description"};
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);

        //多条件查询构造
        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        // 条件一
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new IKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        builder.add(query, BooleanClause.Occur.MUST);
        // 条件二
        // TermQuery不使用分析器所以建议匹配不分词的Field域(StringField, )查询，比如价格、分类ID号等。这里只能演示个ID了。。。
        Query termQuery = new TermQuery(new Term("id", "839"));
        builder.add(termQuery, BooleanClause.Occur.MUST);
        // 获取前十条记录
        TopDocs topDocs = searcher.search(builder.build(), 100);
        // 获取总条数
        log.info("本次搜索共找到" + topDocs.totalHits + "条数据");
        //高亮显示
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        //高亮后的段落范围在100字内
        Fragmenter fragmenter = new SimpleFragmenter(100);
        highlighter.setTextFragmenter(fragmenter);

        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<BlogTitle> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docId = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docId);
            BlogTitle content = blogTitleMapper.selectById(doc.get("id"));
            //处理高亮字段显示
            String title = highlighter.getBestFragment(new IKAnalyzer(), "title", doc.get("title"));
            if (title == null) {
                title = content.getTitle();
            }
            String description = highlighter.getBestFragment(new IKAnalyzer(), "description", content.getDescription());
            content.setDescription(description);
            content.setTitle(title);
            list.add(content);
        }
        return list;
    }
}
