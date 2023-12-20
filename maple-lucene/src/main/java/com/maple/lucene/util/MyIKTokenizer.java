//package com.maple.lucene.util;
//
//import org.apache.lucene.analysis.Tokenizer;
//import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
//import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
//import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
//import org.wltea.analyzer.core.IKSegmenter;
//import org.wltea.analyzer.core.Lexeme;
//
//import java.io.IOException;
//
///**
// * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
// * @date 2023/12/13
// */
//public class MyIKTokenizer extends Tokenizer {
//
//    private IKSegmenter _IKImplement;
//    private final CharTermAttribute termAtt = this.addAttribute(CharTermAttribute.class);
//    private final OffsetAttribute offsetAtt = this.addAttribute(OffsetAttribute.class);
//    private final TypeAttribute typeAtt = this.addAttribute(TypeAttribute.class);
//    private int endPosition;
//
//    /**
//     * useSmart：设置是否使用智能分词。默认为false，使用细粒度分词，这里如果更改为TRUE，那么搜索到的结果可能就少的很多
//     */
//    public MyIKTokenizer(boolean useSmart) {
//        this._IKImplement = new IKSegmenter(this.input, useSmart);
//    }
//
//    @Override
//    public boolean incrementToken() throws IOException {
//        this.clearAttributes();
//        Lexeme nextLexeme = this._IKImplement.next();
//        if (nextLexeme != null) {
//            this.termAtt.append(nextLexeme.getLexemeText());
//            this.termAtt.setLength(nextLexeme.getLength());
//            this.offsetAtt.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
//            this.endPosition = nextLexeme.getEndPosition();
//            this.typeAtt.setType(nextLexeme.getLexemeTypeString());
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public void reset() throws IOException {
//        super.reset();
//        this._IKImplement.reset(this.input);
//    }
//
//    @Override
//    public final void end() {
//        int finalOffset = this.correctOffset(this.endPosition);
//        this.offsetAtt.setOffset(finalOffset, finalOffset);
//    }
//}
