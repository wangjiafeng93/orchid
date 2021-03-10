package com.thunisoft.orchid;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.thunisoft.artery.util.uuid.UUIDHelper;

/**
 * @author wangjiafeng
 * @version 1.0
 * @description
 * @date 2020-12-08 下午 16:37
 */
public class JiemiTest {

    static final String passwordSuffix = "jcw";
    static final String iv = "1234560405060708";

    public static void main(String[] args) throws IOException {
        File zipfile = new File("C:\\Users\\huayu\\Documents\\cssydj\\ceshi\\123456.zip");
        String password = "6789@jkl";
        FileInputStream input = new FileInputStream(zipfile);

        MultipartFile multipartFile =new MockMultipartFile("file", zipfile.getName(), "text/plain", IOUtils.toByteArray(input));
        FileOutputStream fos = null;

        byte[] fileBytes = multipartFile.getBytes();
        //对导入的压缩文件解密
        byte[] jmBytes = new byte[0];
        try {
        jmBytes = aesDecryptToCBC(fileBytes, get16MD5Key(password), iv.getBytes());
        } catch (Exception e) {
            //密码错误异常
            System.out.println("密码错误异常");
        }
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(jmBytes));
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ZipEntry zipEntry = zis.getNextEntry();
            if (zipEntry == null) {
                //压缩文件为空
                throw new FileNotFoundException();
            }
            final byte[] bytes = new byte[1024];
            int len;
            while ((len = zis.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }

            byte[] outBytes = baos.toByteArray();

            //构建输出文件路径
//            String fileName = getRandomFileName(zipEntry.getName(), drjl.getBh());
        }
    }

    public static byte[] aesDecryptToCBC(byte[] content, String decryptKey,byte[] iv) throws Exception {
        //byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(decryptKey.toCharArray());
        byte[] md5Key = decryptKey.getBytes();//已经使用md5生成16位key
        checkkey(md5Key);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //算法参数
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(md5Key, "AES"),paramSpec);
        return cipher.doFinal(content);
    }

    public static void checkkey(byte[] key) throws Exception {

        if(key.length != 16 && key.length != 32) {
            throw new Exception("密钥长度错误，key byte[]必须是16或者32位");
        }
    }
    private static String get16MD5Key(String password) {
        final String suffix = passwordSuffix;

        String md5 = MD5(password);

        String key = md5.substring(0, md5.length() - 3) + suffix;

        return key;
    }
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //result = buf.toString(); // 32位
            result = buf.toString().substring(8, 24); // 16位
            //System.out.println("MD5(" + sourceStr + ",32) = " + result);
            //System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("shibai");
        }

        return result;
    }
    private String getRandomFileName(String name, String drjlid) {
        String fileSeparator = "\\.";

        if (StringUtils.isBlank(drjlid)) {
            drjlid = UUIDHelper.getUuid();
        }

        String[] split = name.split(fileSeparator);

        if (split.length == 2) {
            return split[0] + "_" + drjlid + "." + split[1];
        }

        return StringUtils.EMPTY;
    }
}
