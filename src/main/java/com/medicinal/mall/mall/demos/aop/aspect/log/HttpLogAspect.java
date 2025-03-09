package com.medicinal.mall.mall.demos.aop.aspect.log;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @description 网络日志收集切面
 */
@Aspect
@Component
@Slf4j
public class HttpLogAspect {

    @Value("${spring.application.name}")
    private String appName;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.GetMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void httpLog() {
    }

    @Around("httpLog()")
    public Object aroundHttpLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取当前请求对象
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 记录请求信息
        // 程序执行中出现异常
        Object result = null;
        LogModel webLog = new LogModel();
        webLog.setUrl(request.getRequestURL().toString());
        webLog.setIp(request.getRemoteUser());
        webLog.setHttpMethod(request.getMethod());
        webLog.setThreadId(Long.toString(Thread.currentThread().getId()));
        webLog.setThreadName(Thread.currentThread().getName());
        // 通过反射，获取入参和出参
        webLog.setRequestParam(getParameterNameAndValue(proceedingJoinPoint));
        webLog.setAppName(appName);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            result = proceedingJoinPoint.proceed();
            stopWatch.stop();
            webLog.setTimeCost(stopWatch.getLastTaskTimeMillis());
            log.info("{}", JSONUtil.parse(webLog));
        } catch (Throwable throwable) {
            stopWatch.stop();
            throw throwable;
        }
        return result;
    }

    private static Map<String, Object> getParameterNameAndValue(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();
        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            return Collections.emptyMap();
        }
        // 不用强制指定大小，一般参数个数不会太多
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }

}
