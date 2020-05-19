package com.thunisoft.orchid.bean.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;

/**
 * @author wangjiafeng
 * @version 1.0
 * @description
 * @date 2020-05-19 下午 14:42
 */
@Data
@Entity
@Table(name="T_ORCHID", schema = "PUBLIC")
public class Orchid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="C_BH")
    private String bh;

    @Column(name="C_NAME")
    private String name;

    @Column(name="D_DATE")
    private Date date;

    @Column(name="C_CONTENT")
    private String content;

    @Column(name="C_TYPE")
    private String type;
}
