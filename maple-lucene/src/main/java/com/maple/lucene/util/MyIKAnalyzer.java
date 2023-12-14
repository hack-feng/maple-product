//package com.maple.lucene.util;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.Tokenizer;
//
///**
// * @author 笑小枫 <https://xiaoxiaofeng.com/>
// * @date 2023/12/13
// */
//public class MyIKAnalyzer extends Analyzer {
//
//    private boolean useSmart;
//
//    public boolean useSmart() {
//        return this.useSmart;
//    }
//
//    public void setUseSmart(boolean useSmart) {
//        this.useSmart = useSmart;
//    }
//
//    public MyIKAnalyzer() {
//        this(false);
//    }
//
//    @Override
//    protected TokenStreamComponents createComponents(String s) {
//        Tokenizer tokenizer = new MyIKTokenizer(this.useSmart());
//        return new TokenStreamComponents(tokenizer);
//    }
//
//    public MyIKAnalyzer(boolean useSmart) {
//        this.useSmart = useSmart;
//    }
//}
