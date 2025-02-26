package com.medicinal.mall.mall.demos.info;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;

import java.util.Map;


public class ThreadLocalParserInfo {

    private static final ThreadLocal<ExpressionParser> PARSE_INFO = new ThreadLocal<>();
    private static final ThreadLocal<EvaluationContext> CONTEXT_INFO = new ThreadLocal<>();
    private static final ThreadLocal<Map<String,Object>> VARIABLE_INFO = new ThreadLocal<>();

    public static ExpressionParser getParseInfo() {
        return PARSE_INFO.get();
    }

    public static EvaluationContext getContextInfo() {
        return CONTEXT_INFO.get();
    }

    public static Map<String,Object> getVariableInfo() {
        return VARIABLE_INFO.get();
    }

    public static void set(Map<String,Object> variableInfo) {
        VARIABLE_INFO.set(variableInfo);
    }

    public static void set(ExpressionParser parserInfo) {
        PARSE_INFO.set(parserInfo);
    }

    public static void set(EvaluationContext contextInfo) {
        CONTEXT_INFO.set(contextInfo);
    }

    public static boolean isCache(){
        return VARIABLE_INFO.get() != null;
    }

    public static void removeAll() {
        PARSE_INFO.remove();
        CONTEXT_INFO.remove();
        VARIABLE_INFO.remove();
    }

}
