package com.springlego.autoconfigure.frame.util;

import java.io.File;

/**
 * @Description 全局的静态变量,用于全局变量的存放
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/5/24 10:40
 **/
public class GlobalStatic {

    private GlobalStatic(){
        throw new IllegalAccessError("工具类不能实例化");
    }


    public static  String rootDir=null;
    public static  String webInfoDir=null;
    public static  String staticHtmlDir=null;
    public static  String tempRootpath = System.getProperty("user.dir") + "/temp/";

    //主业务缓存
    public static final String cacheKey="servicecache";


    static{
        String path=Thread.currentThread().getContextClassLoader().getResource("").toString();
        path = path.replace("\\", "/");


        if(path.startsWith("file:/")){
            path=path.substring(6, path.length());
        }

        int _info=path.indexOf("/WEB-INF/classes");
        if(_info>0){
            path=path.substring(0, _info);
        }
        if(!path.startsWith("/")&&path.indexOf(":")==-1){
            path= File.separatorChar+path;
        }
        rootDir=path;
        webInfoDir=rootDir+"/WEB-INF";
        tempRootpath = rootDir + "/temp/";
        staticHtmlDir=rootDir + "/statichtml/";



    }
}
