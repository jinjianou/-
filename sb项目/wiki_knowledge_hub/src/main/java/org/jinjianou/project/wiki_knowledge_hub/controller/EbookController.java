package org.jinjianou.project.wiki_knowledge_hub.controller;

import org.jinjianou.project.wiki_knowledge_hub.domain.Ebook;
import org.jinjianou.project.wiki_knowledge_hub.req.EBookResp;
import org.jinjianou.project.wiki_knowledge_hub.req.EbookEeq;
import org.jinjianou.project.wiki_knowledge_hub.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Autowired
    private EbookService ebookService;

    @GetMapping("list")
    public List<Ebook> listEbooks(){
        return ebookService.getAllEbooks();
    }

    @GetMapping("listByName")
    public List<EBookResp> getEbooksByReqParam(EbookEeq ebookEeq){
        return ebookService.getEbooksByReqParam(ebookEeq);
    }

}
