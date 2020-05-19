package com.thunisoft.orchid.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.thunisoft.orchid.bean.pojo.Orchid;
import com.thunisoft.orchid.repository.OrchidRepository;

/**
 * @author wangjiafeng
 * @version 1.0
 * @description
 * @date 2020-05-19 下午 14:35
 */
@Service
public class OrchidService {

    @Resource
    private OrchidRepository orchidRepository;

    public void getOrchid(String ParameBh, String ParameName) {
        System.out.println("======" + ParameBh + "=====" + ParameName);
        String bh = "963cd71e-b1e0-44d2-9b71-c6ec55a01b0d";
        Orchid orchid = orchidRepository.findOrchid(bh).orElse(null);
        System.out.println(orchid.getName());
    }

}
