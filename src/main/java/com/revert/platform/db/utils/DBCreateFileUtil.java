package com.revert.platform.db.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBCreateFileUtil {

    private static final String suffix_1 = ".java";
    private static final String suffix_2 = ".xml";

    private static final String base_java_mkdirs[] = {"model","mapper","service","controller","mapper.impI"};

    public static Map<String, Map<String, String>> createFile(String javaPath, String xmlPath,
                                                              String packageVal, String tableName,
                                                              String sqlType){
        Map<String, Map<String, String>> resultMap = new HashMap<>();
        tableName = tableName.substring(tableName.indexOf("_t") + "_t".length());
        tableName = toUpperCaseTableName(tableName);
        /** 1、创建目录*/
        String vals[] = packageVal.split("[.]");
        if(vals == null) vals = packageVal.split("-");
        StringBuilder javaPackagePath = createBaseMkdir(vals, javaPath);
        vals = packageVal.replace("com.revert","com.revert."+sqlType).split("[.]");
        StringBuilder xmlPackagePath = createBaseMkdir(vals, xmlPath);
        StringBuilder filePath = null;
        File file = null;
        boolean isXml = false;
        for(String v : base_java_mkdirs){
            if(base_java_mkdirs[4].equals(v)){
                isXml = true;
                filePath = new StringBuilder(xmlPackagePath.toString());
            }else{
                filePath = new StringBuilder(javaPackagePath);
            }
            for(String v2 : v.split("[.]")){
                filePath.append("/").append(v2);
                file = new File(filePath.toString());
                if(!file.exists()){
                    file.mkdir();
                }
            }
            String className = isXml ? tableName+"Mapper" : tableName+toUpperCaseTableName(v);
            filePath.append("/").append(className).append(isXml ? suffix_2 : suffix_1);
            file = new File(filePath.toString());
            try {
                System.out.println(filePath.toString());
                file.createNewFile();
                Map<String, String> obj = new HashMap<>();
                obj.put("tableName",tableName);
                obj.put("filePath",filePath.toString());
                resultMap.put(v, obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    private static StringBuilder createBaseMkdir(String vals[], String path){
        StringBuilder packagePath = new StringBuilder(path);
        File file = null;
        /** 创建基础包文件夹*/
        for(String v : vals){
            packagePath.append("/").append(v);
            file = new File(packagePath.toString());
            if(!file.exists()){
                file.mkdir();
            }
        }
        return packagePath;
    }
    public static String toUpperCaseTableName(String tableName){
        return toUpperCaseTableName(tableName,1);
    }

    public static String toUpperCaseTableName(String tableName,int type){
        String vs[] = tableName.split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for(String v : vs){
            if(StringUtils.isEmpty(v)) continue;
            String first = v.substring(0,1).toUpperCase();
            stringBuilder.append(first)
                    .append(v.substring(1,v.length()));
        }
        if(2 == type){
            String val = stringBuilder.toString();
            return val.substring(0,1).toLowerCase() + val.substring(1,stringBuilder.toString().length());
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
    }

}
