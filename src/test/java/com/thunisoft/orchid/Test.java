package com.thunisoft.orchid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

/**
 * @author wangjiafeng
 * @version 1.0
 * @description
 * @date 2020-12-31 下午 12:28
 */
public class Test {

    public static void main(String[] args) {
           String ss = "申请审核";
           if(ss.contains("立案")){
               System.out.println("123");
           }else{
               System.out.println("456");
           }
    }

    public static void test(){
        ArrayList<String> strings = Lists.newArrayList("fdsfd", "fds", "fggfdsad");
        for(String str : strings){
            str.replace("f","123");
        }
        strings.forEach(String::length);
        Collection<String> f = strings.stream().map(o -> o.replace("f", "123")).collect(Collectors.toList());
        System.out.println(f);
        String strMi = "jfkdsfjids8989 fdsf\r\nds786/7fds 908990 /ffsdf" ;
        strMi = strMi.replace(" ", "%20");
        strMi = strMi.replace("\\+", "%2B");
        strMi = strMi.replace("/", "%2F");
        strMi = strMi.replace("=", "%3D");
        System.out.println(
                strMi.replace("\r\n","")
        );
        strMi = "jfkdsfjids8989 fdsf\r\nds786/7fds 908990 /ffsdf" ;
        strMi = strMi.replaceAll(" ", "%20");
        strMi = strMi.replaceAll("\\+", "%2B");
        strMi = strMi.replaceAll("/", "%2F");
        strMi = strMi.replaceAll("=", "%3D");
        System.out.println(
                strMi.replaceAll("\r\n","")
        ); ;
    }
}
