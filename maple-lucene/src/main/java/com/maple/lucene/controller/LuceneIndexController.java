package com.maple.lucene.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.maple.lucene.bean.BlogTitle;
import com.maple.lucene.mapper.BlogTitleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 笑小枫 <https://xiaoxiaofeng.com/>
 * @date 2023/12/14
 */
@RestController
@RequestMapping("/lucene")
@AllArgsConstructor
@Slf4j
public class LuceneIndexController {

    private final BlogTitleMapper blogTitleMapper;

    @GetMapping("/createIndex")
    public String createIndex() {
        List<BlogTitle> list = blogTitleMapper.selectList(Wrappers.lambdaQuery(BlogTitle.class));

        // 创建文档的集合
        Collection<Document> docs = new ArrayList<>();
        for (BlogTitle blogTitle : list) {
            // 创建文档对象
            Document document = new Document();

            // StringField: 这个 Field 用来构建一个字符串Field，不分析，会索引，Field.Store控制存储
            // LongPoint、IntPoint 等类型存储数值类型的数据。会分析，会索引，不存储，如果想存储数据还需要使用 StoredField
            // StoredField: 这个 Field 用来构建不同类型，不分析，不索引，会存储
            // TextField: 如果是一个Reader, 会分析，会索引，，Field.Store控制存储
            document.add(new StringField("id", String.valueOf(blogTitle.getId()), Field.Store.YES));
            // Field.Store.YES, 将原始字段值存储在索引中。这对于短文本很有用，比如文档的标题，它应该与结果一起显示。
            // 值以其原始形式存储，即在存储之前没有使用任何分析器。
            document.add(new TextField("title", blogTitle.getTitle(), Field.Store.YES));
            // Field.Store.NO，可以索引，分词，不将字段值存储在索引中。
            // 个人理解：说白了就是为了省空间，如果回表查询，其实无所谓，如果不回表查询，需要展示就要保存，设为YES，无需展示，设为NO即可。
            document.add(new TextField("description", blogTitle.getDescription(), Field.Store.NO));
            docs.add(document);
        }

        // 引入IK分词器，如果需要解决上面版本冲突报错的问，使用`new MyIKAnalyzer()`即可
        Analyzer analyzer = new IKAnalyzer();
        // 索引写出工具的配置对象
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        // 索引目录类,指定索引在硬盘中的位置，我的设置为D盘的indexDir文件夹
        // 创建索引的写出工具类。参数：索引的目录和配置信息
        try (Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
             IndexWriter indexWriter = new IndexWriter(directory, conf)) {
            // 把文档集合交给IndexWriter
            indexWriter.addDocuments(docs);
            // 提交
            indexWriter.commit();
        } catch (Exception e) {
            log.error("创建索引失败", e);
            return "创建索引失败";
        }
        return "创建索引成功";
    }

    @GetMapping("/updateIndex")
    public String update() {
        // 创建配置对象
        IndexWriterConfig conf = new IndexWriterConfig(new IKAnalyzer());
        // 创建目录对象
        // 创建索引写出工具
        try (Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
             IndexWriter writer = new IndexWriter(directory, conf)) {
            // 获取更新的数据，这里只是演示
            BlogTitle blogTitle = blogTitleMapper.selectById("808");

            // 创建新的文档数据
            Document doc = new Document();
            doc.add(new StringField("id", "808", Field.Store.YES));
            doc.add(new TextField("title", blogTitle.getTitle(), Field.Store.YES));
            doc.add(new TextField("description", blogTitle.getDescription(), Field.Store.YES));
            writer.updateDocument(new Term("id", "808"), doc);
            // 提交
            writer.commit();
        } catch (Exception e) {
            log.error("更新索引失败", e);
            return "更新索引失败";
        }

        return "更新索引成功";
    }

    @GetMapping("/deleteIndex")
    public String deleteIndex() {
        // 创建配置对象
        IndexWriterConfig conf = new IndexWriterConfig(new IKAnalyzer());
        // 创建目录对象
        // 创建索引写出工具
        try (Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("d:\\indexDir"));
             IndexWriter writer = new IndexWriter(directory, conf)) {
            // 根据词条进行删除
            writer.deleteDocuments(new Term("id", "808"));
            // 提交
            writer.commit();
        } catch (Exception e) {
            log.error("删除索引失败", e);
            return "删除索引失败";
        }
        return "删除索引成功";
    }
}
