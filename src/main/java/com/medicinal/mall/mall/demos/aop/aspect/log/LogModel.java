package com.medicinal.mall.mall.demos.aop.aspect.log;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 日志模型
 */
@Data
@NoArgsConstructor
public class LogModel {

    /**
     * 线程ID
     */
    private String threadId;

    /**
     * 线程名称
     */
    private String threadName;

    /**
     * 真实的ip地址
     */
    private String ip;

    /**
     * url路径
     */
    private String url;

    /**
     * 请求的http方法（GET、POST、PUT、DELETE）
     */
    private String httpMethod;

    /**
     * 执行的类的方法
     */
    private String classMethod;

    /**
     * 请求的参数
     */
    private Object requestParam;

    /**
     * 返回的响应参数
     */
    private Object result;

    /**
     * 接口总共的耗时
     */
    private Long timeCost;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 异常信息
     */
    private Throwable exception;

    /**
     * 异常的内容（发生异常的类和异常的信息）
     */
    private String exceptionContent;

    /**
     * 系统名称
     */
    private String appName;

    /**
     * 使用的浏览器
     */
    private String browser;

    /**
     * user-agent值
     */
    private String userAgent;

}
