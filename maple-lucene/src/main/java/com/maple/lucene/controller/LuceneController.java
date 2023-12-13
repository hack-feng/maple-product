package com.maple.lucene.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.maple.lucene.bean.BlogTitle;
import com.maple.lucene.mapper.BlogTitleMapper;
import com.maple.lucene.util.MyIKAnalyzer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
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
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zhangfuzeng
 * @date 2023/12/13
 */
@RestController
@RequestMapping("/lucene")
@AllArgsConstructor
@Slf4j
public class LuceneController {

    private final BlogTitleMapper blogTitleMapper;

    @GetMapping("/createIndex")
    public String createIndex() throws IOException {
        List<BlogTitle> list = blogTitleMapper.selectList(Wrappers.lambdaQuery(BlogTitle.class));

        // 创建文档的集合
        Collection<Document> docs = new ArrayList<>();
        for (BlogTitle blogTitle : list) {
            // 创建文档对象
            Document document1 = new Document();
            // StringField会创建索引，但是不会被分词，TextField，即创建索引又会被分词。
            document1.add(new StringField("id", String.valueOf(blogTitle.getId()), Field.Store.YES));

            document1.add(new TextField("title", blogTitle.getTitle(), Field.Store.YES));
            document1.add(new TextField("description", blogTitle.getDescription(), Field.Store.YES));
            docs.add(document1);
        }

        // 索引目录类,指定索引在硬盘中的位置，我的设置为D盘的indexDir文件夹
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 引入IK分词器
        Analyzer analyzer = new MyIKAnalyzer();
        // 索引写出工具的配置对象，这个地方就是最上面报错的问题解决方案
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        // 创建索引的写出工具类。参数：索引的目录和配置信息
        IndexWriter indexWriter = new IndexWriter(directory, conf);
        // 把文档集合交给IndexWriter
        indexWriter.addDocuments(docs);
        // 提交
        indexWriter.commit();
        // 关闭
        indexWriter.close();
        return "success";
    }

    @GetMapping("/updateIndex")
    public String update(String age) throws IOException {
        // 创建目录对象
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 创建配置对象
        IndexWriterConfig conf = new IndexWriterConfig(new MyIKAnalyzer());
        // 创建索引写出工具
        IndexWriter writer = new IndexWriter(directory, conf);
        // 创建新的文档数据
        Document doc = new Document();
        doc.add(new StringField("id", "34", Field.Store.YES));
        BlogTitle blogTitle = blogTitleMapper.selectById("34");
        blogTitle.setTitle("宫本武藏超级兵");
        blogTitleMapper.updateById(blogTitle);
        doc.add(new TextField("title", blogTitle.getTitle(), Field.Store.YES));
        doc.add(new TextField("description", blogTitle.getDescription(), Field.Store.YES));
        writer.updateDocument(new Term("id", "34"), doc);
        // 提交
        writer.commit();
        // 关闭
        writer.close();
        return "success";
    }

    @RequestMapping("/searchText")
    public List<BlogTitle> searchText(String text) throws IOException, ParseException {
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        QueryParser parser = new QueryParser("description", new MyIKAnalyzer());
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

    @GetMapping("/deleteIndex")
    public String deleteIndex() throws IOException {
        // 创建目录对象
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 创建配置对象
        IndexWriterConfig conf = new IndexWriterConfig(new IKAnalyzer());
        // 创建索引写出工具
        IndexWriter writer = new IndexWriter(directory, conf);
        // 根据词条进行删除
        writer.deleteDocuments(new Term("id", "34"));
        // 提交
        writer.commit();
        // 关闭
        writer.close();
        return "success";
    }


    @RequestMapping("/searchTextMore")
    public List<BlogTitle> searchText1(String text) throws IOException, ParseException {
        String[] str = {"title", "description"};
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new MyIKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        // 获取前十条记录
        TopDocs topDocs = searcher.search(query, 100);
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

    @RequestMapping("/searchText2")
    public List<BlogTitle> searchText2(String text) throws IOException, ParseException, InvalidTokenOffsetsException {
        String[] str = {"title", "description"};
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new MyIKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        // 获取前十条记录
        TopDocs topDocs = searcher.search(query, 100);
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
            String title = highlighter.getBestFragment(new MyIKAnalyzer(), "title", doc.get("title"));
            if (title == null) {
                title = content.getTitle();
            }
            String descs = highlighter.getBestFragment(new MyIKAnalyzer(), "description", doc.get("description"));
            if (descs == null) {
                descs = content.getDescription();
            }
            content.setDescription(descs);
            content.setTitle(title);
            list.add(content);
        }
        return list;
    }


    @RequestMapping("/searchText3")
    public List<BlogTitle> searchText3(String text) throws IOException, ParseException, InvalidTokenOffsetsException {
        String[] str = {"title", "description"};
        int page = 1;
        int pageSize = 10;
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new MyIKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        // 获取前十条记录
        //TopDocs topDocs = searcher.search(query, 100);

        TopDocs topDocs = searchByPage(page, pageSize, searcher, query);
        // 获取总条数
        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");

        //高亮显示
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        Fragmenter fragmenter = new SimpleFragmenter(100);   //高亮后的段落范围在100字内
        highlighter.setTextFragmenter(fragmenter);

        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<BlogTitle> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docID);
            BlogTitle content = blogTitleMapper.selectById(doc.get("id"));
            //处理高亮字段显示
            String title = highlighter.getBestFragment(new MyIKAnalyzer(), "title", doc.get("title"));
            if (title == null) {
                title = content.getTitle();
            }
            String descs = highlighter.getBestFragment(new MyIKAnalyzer(), "description", doc.get("description"));
            if (descs == null) {
                descs = content.getDescription();
            }
            content.setDescription(descs);
            content.setTitle(title);
            list.add(content);
        }
        return list;
    }

    private TopDocs searchByPage(int page, int perPage, IndexSearcher searcher, Query query) throws IOException {
        TopDocs result = null;
        if (query == null) {
            System.out.println(" Query is null return null ");
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

    @GetMapping("/searchText4")
    public List<BlogTitle> searchText4(String text) throws IOException, ParseException, InvalidTokenOffsetsException {
        String[] str = {"description"};
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);

        //多条件查询构造
        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        // 条件一
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new MyIKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        builder.add(query, BooleanClause.Occur.SHOULD);
        //条件二
        Query termQuery = new TermQuery(new Term("title", "0.SpringBoot目录"));
        builder.add(termQuery, BooleanClause.Occur.SHOULD);

        // 获取前十条记录
        TopDocs topDocs = searcher.search(builder.build(), 10);
        // 获取总条数
        System.err.println("本次搜索共找到" + topDocs.totalHits + "条数据");
        //高亮显示
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        Fragmenter fragmenter = new SimpleFragmenter(100);   //高亮后的段落范围在100字内
        highlighter.setTextFragmenter(fragmenter);

        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<BlogTitle> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docID);
            BlogTitle content = blogTitleMapper.selectById(doc.get("id"));
            //处理高亮字段显示
            String title = highlighter.getBestFragment(new MyIKAnalyzer(), "title", doc.get("title"));
            if (title == null) {
                title = content.getTitle();
            }
            String descs = highlighter.getBestFragment(new MyIKAnalyzer(), "description", doc.get("description"));
            if (descs == null) {
                descs = content.getDescription();
            }
            content.setDescription(descs);
            content.setTitle(title);
            list.add(content);
        }
        return list;
    }

    public static List<String> cut(String msg) throws IOException {
        StringReader sr=new StringReader(msg);
        IKSegmenter ik=new IKSegmenter(sr, true);
        Lexeme lex=null;
        List<String> list=new ArrayList<>();
        while((lex=ik.next())!=null){
            list.add(lex.getLexemeText());
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        String text="笑小枫是一个的网站？还是一个人名！";
        List<String> list=cut(text);
        System.out.println(list);
    }

}
