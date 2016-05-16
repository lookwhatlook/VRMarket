package com.xmmaker.vrmarket;

/**
 * Created by Administrator on 2016/4/9.
 */
public class Api {
    //网易科技新闻
    public static final String NETEAST_HOST = "http://c.m.163.com/nc/article/";

    public static final String ENDDETAIL_URL = "/full.html";

    public static final String ENDNEWS_URL = "-20.html";

    //网易科技新闻接口，一次请求20条
    public static final String NEWS_URL="http://c.m.163.com/nc/article/headline/T1348649580692/0-20.html";
    public static final String NEWS_URL_PAGE="http://c.m.163.com/nc/article/headline/T1348649580692/";


    // 新闻详情
    //新闻详情：例子：http://c.m.163.com/nc/article/BFNFMVO800034JAU/full.html, 其中一大串的是postId
    public static final String NEWS_DETAIL = NETEAST_HOST + "nc/article/";

    // 科技
    public static final String TECH_ID = "T1348649580692";

    // 头条
    public static final String HEADLINE_TYPE = "headline";
    public static final String HEADLINE_ID = "T1348647909107";
    public static final String OTHER_TYPE = "list";

//    public static final String XM_ALL_APPS = "http://www.xmmaker.com/App/demo.php";
    public static final String XM_ALL_APPS = "http://www.xmmaker.com/index.php?s=/VRsource/index.html";
}
