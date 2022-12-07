package org.jinjianou.project.wiki_knowledge_hub.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jinjianou.project.wiki_knowledge_hub.domain.Ebook;
import org.jinjianou.project.wiki_knowledge_hub.domain.EbookExample;

public interface EbookMapper {
    long countByExample(EbookExample example);

    int deleteByExample(EbookExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ebook row);

    int insertSelective(Ebook row);

    List<Ebook> selectByExample(EbookExample example);

    Ebook selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Ebook row, @Param("example") EbookExample example);

    int updateByExample(@Param("row") Ebook row, @Param("example") EbookExample example);

    int updateByPrimaryKeySelective(Ebook row);

    int updateByPrimaryKey(Ebook row);
}