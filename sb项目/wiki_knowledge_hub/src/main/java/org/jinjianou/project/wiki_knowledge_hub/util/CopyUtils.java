package org.jinjianou.project.wiki_knowledge_hub.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyUtils {
    //复制单值
    public static <T> T copy(Object source,Class<T> clazz){
        if(source==null){
            return null;
        }
        T obj;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(source,obj);
        return obj;
    }

    //复制list
    public static <T> List<T> copyList(List source, Class<T> clazz){
        List<T> rs = new ArrayList<>();
        if(!CollectionUtils.isEmpty(source)){
            for (Object element : source) {
                rs.add(copy(element,clazz));
            }
        }
        return rs;
    }
}
