package org.jinjianou.project.wiki_knowledge_hub.service;

import org.apache.commons.lang3.StringUtils;
import org.jinjianou.project.wiki_knowledge_hub.dao.EbookMapper;
import org.jinjianou.project.wiki_knowledge_hub.domain.Ebook;
import org.jinjianou.project.wiki_knowledge_hub.domain.EbookExample;
import org.jinjianou.project.wiki_knowledge_hub.req.EBookResp;
import org.jinjianou.project.wiki_knowledge_hub.req.EbookEeq;
import org.jinjianou.project.wiki_knowledge_hub.util.CopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {
    @Autowired
    private EbookMapper ebookMapper;

    public List<Ebook> getAllEbooks(){
        return ebookMapper.selectByExample(null);
    }

    public List<EBookResp> getEbooksByReqParam(EbookEeq ebookEeq){
        EbookExample example = new EbookExample();
        EbookExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isBlank(ebookEeq.getTitle())) {
            criteria.andTitleLike("%"+ebookEeq.getTitle()+"%");
        }
        if (!StringUtils.isBlank(ebookEeq.getDescription())) {
            example.or().andDescriptionLike("%"+ebookEeq.getDescription()+"%");
        }
        List<Ebook> ebookList = ebookMapper.selectByExample(example);
        return CopyUtils.copyList(ebookList,EBookResp.class);
    }

}
