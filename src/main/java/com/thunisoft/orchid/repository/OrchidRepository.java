package com.thunisoft.orchid.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thunisoft.orchid.bean.pojo.Orchid;

/**
 * @author wangjiafeng
 * @version 1.0
 * @description
 * @date 2020-05-19 下午 14:36
 */
@Repository
public interface OrchidRepository extends JpaRepository<Orchid, String> {

    @Query("select t from Orchid t where t.bh = :bh ")
    Optional<Orchid> findOrchid(@Param("bh") String bh);

}
