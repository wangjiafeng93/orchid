package com.thunisoft.orchid.adapter;

import org.springframework.stereotype.Component;

import com.thunisoft.artery.login.model.ArteryLoginConfig;

/**
 * @author wangjiafeng
 * @version 1.0
 * @description
 * @date 2020-03-31 下午 17:16
 */
@Component
public class OrchidLoginConfig extends ArteryLoginConfig {
    @Override
    protected void addFilters() {

    }

    @Override
    protected void initFilterChainDefinitions() {
        this.filter("/add*", "anon");
    }
}
