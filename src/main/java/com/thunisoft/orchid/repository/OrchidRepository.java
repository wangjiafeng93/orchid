package com.thunisoft.orchid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blinkfox.fenix.jpa.QueryFenix;
import com.thunisoft.orchid.bean.pojo.Orchid;

/**
 * @author wangjiafeng
 * @version 1.0
 * @description 此类为Orchid持久层类
 * @date 2020-05-19 下午 14:36
 */
@Repository
public interface OrchidRepository extends JpaRepository<Orchid, String> {

    /**
     * 自定义写SQL查询
     * @param bh 主键
     * @return 实体
     */
    @Query("select t from Orchid t where t.bh = :bh ")
    Optional<Orchid> findOrchid(@Param("bh") String bh);

    /**
     * 查询orchid实体where content=？ and type= ？条件查询
     * @param content 内容
     * @param type 类型
     * @return 实体
     */
    Orchid findByContentAndType(String content,String type);

    /**
     * 查询orchid实体where content=？ or type= ？条件查询
     * @param content 内容
     * @param type 类型
     * @return 实体
     */
    Orchid findByContentOrType(String content,String type);

    /**
     * 查询orchid实体where content like ?条件查询
     * @param content 内容
     * @return 实体
     */
    Orchid findByContentLike(String content);

    /**
     * 查询最新的实体
     * @return 名称
     */
    @QueryFenix
    String findMaxDateOrchid();
}
