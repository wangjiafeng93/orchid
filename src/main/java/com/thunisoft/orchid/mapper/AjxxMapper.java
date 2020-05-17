package com.thunisoft.orchid.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author wangjiafeng
 * @version 1.0
 * @description
 * @date 2020-05-16 下午 16:13
 */
@Component
public interface AjxxMapper  {
    public List<Map<String,Object>> selectAjxx(@Param("offset") Integer offset,@Param("size") Integer size);
    public Integer selectAjxxCount();

    public List<Map<String,Object>> selectXyrxx(@Param("offset") Integer offset,@Param("size") Integer size);
    public Integer selectXyrxxCount();
}
