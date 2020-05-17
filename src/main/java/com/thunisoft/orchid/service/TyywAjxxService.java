package com.thunisoft.orchid.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.thunisoft.orchid.mapper.AjxxMapper;

/**
 * @author wangjiafeng
 * @version 1.0
 * @description
 * @date 2020-05-16 下午 17:19
 */
@Service
public class TyywAjxxService {

    @Autowired
    AjxxMapper ajxxMapper;

    @Value("${exportPath}")
    String exportPath;

    @Value("${onceSize}")
    Integer onceSize;

    static String HH = "\r\n";

    public void exportTyywAjxx() {
        System.out.println("==========案件抽取开始=============");

        Integer ajs = ajxxMapper.selectAjxxCount();
        if (null != ajs) {
            int count = 0;
            for (int i = 0; i < (ajs.intValue() / onceSize)+1; i++) {
                List<Map<String, Object>> result = ajxxMapper.selectAjxx(i * onceSize, (i + 1) * onceSize);
                readAjxxFile(result,i * onceSize+1,(i + 1) * onceSize);
                count ++;
                System.out.println("-------进行中。已完成批次:"+count);
            }
            System.out.println("=========执行"+count+"批次。导出总数："+ajs+"个案件信息抽取结束。输出路径：" + exportPath);
        } else {
            System.out.println("==========无符合案件====");
        }
    }


    public void exportTyywXyrxx() {
        System.out.println("==========嫌疑人开始=============");
        Integer xyrs = ajxxMapper.selectXyrxxCount();
        if (null != xyrs) {
            int count = 0;
            for (int i = 0; i < (xyrs.intValue() / onceSize)+1; i++) {
                List<Map<String, Object>> result = ajxxMapper.selectXyrxx(i * onceSize, (i + 1) * onceSize);
                readXyrxxFile(result,i * onceSize+1,(i + 1) * onceSize);
                count ++;
                System.out.println("-------进行中。已完成批次:"+count);
            }
            System.out.println("========执行"+count+"批次。导出总数："+xyrs+"个嫌疑人信息抽取结束。输出路径：" + exportPath);
        }else {
            System.out.println("==========无符合案件====");
        }
    }
        /**
         * 写出txt数据
         */
        private void readAjxxFile (List < Map < String, Object >> tyywDataList,int offset,int size){
            String exportPathFull = exportPath + "\\"+offset+"-"+size;
            File filePath = new File(exportPathFull);
            if (!filePath.exists()) {
                filePath.mkdir();
            }
        for (Map<String, Object> map : tyywDataList) {
            if (map.get("SFPB") == null) {
                map.put("PB", null);
                map.put("BPB", null);
            } else if ("批捕".equals(map.get("SFPB"))) {
                map.put("PB", "是");
                map.put("BPB", "否");
            } else {
                map.put("PB", "否");
                map.put("BPB", "是");
            }
            String ajText = map.get("TYSAH") + "-" + map.get("BMSAH");
            String filePathFull = exportPathFull + "\\"+ajText ;
            filePath = new File(filePathFull);
            if (!filePath.exists()) {
                filePath.mkdir();
            }
            try (FileOutputStream out = new FileOutputStream(filePathFull + "\\"+ajText+".txt"); PrintStream ps = new PrintStream(out);) {
                String text = map.get("TYSAH") + HH +
                        map.get("BMSAH") + HH +
                        map.get("YSAY_AYMC") + HH +
                        map.get("JAAY") + HH +
                        map.get("AJLB_MC") + HH +
                        map.get("BJRQ") + HH +
                        map.get("QY") + HH +
                        map.get("XYR") + HH +
                        map.get("PB") + HH +
                        map.get("BPB") + HH +
                        map.get("QS") + HH +
                        map.get("BQS") + HH +
                        map.get("SFKS");
                ps.print(text.replaceAll("null", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }

        void readXyrxxFile (List < Map < String, Object >> tyywDataList,int offset,int size){
            String exportPathFull = exportPath + "\\"+offset+"-"+size;
            File filePath = new File(exportPathFull);
            if (!filePath.exists()) {
                filePath.mkdir();
            }
        for (Map<String, Object> map : tyywDataList) {
            if (map.get("SFPB") == null) {
                map.put("PB", null);
                map.put("BPB", null);
            } else if ("批捕".equals(map.get("SFPB"))) {
                map.put("PB", "是");
                map.put("BPB", "否");
            } else {
                map.put("PB", "否");
                map.put("BPB", "是");
            }

            String ajText = map.get("TYSAH") + "-" + map.get("BMSAH");
            String filePathFull = exportPathFull + "\\" + ajText;
            filePath = new File(filePathFull);
            if (!filePath.exists()) {
                filePath.mkdir();
            }
            try (FileOutputStream out = new FileOutputStream(
                    filePathFull + "\\" + map.get("XM") + ".txt"); PrintStream ps = new PrintStream(out);) {
                String text = map.get("TYSAH") + HH +
                        map.get("BMSAH") + HH +
                        map.get("XM") + HH +
                        map.get("CSRQ") + HH +
                        map.get("YS_AYMC") + HH +
                        map.get("SJAY_AYMC") + HH +
                        map.get("AJLB_MC") + HH +
                        map.get("SJRQ") + HH +
                        map.get("QY") + HH +
                        map.get("PB") + HH +
                        map.get("BPB") + HH +
                        map.get("QS") + HH +
                        map.get("BQSLX") + HH +
                        map.get("SFKS");
                ps.print(text.replaceAll("null", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }
    }
