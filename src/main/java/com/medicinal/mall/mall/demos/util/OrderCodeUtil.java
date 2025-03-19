package com.medicinal.mall.mall.demos.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 订单编号生成工具
 * @Author cxk
 * @Date 2025/3/19 0:11
 */
public class OrderCodeUtil {

    // 静态配置项
    /**
     * 默认的机器id
     */
    private static int MACHINE_ID = 15;
    /**
     * 当前的时间戳
     */
    private static String CURRENT_MINUTE = "";
    /**
     * 自增序列
     */
    private static AtomicInteger SEQUENCE = new AtomicInteger(0);

    // 时间格式（精确到分钟）
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    /**
     * 配置机器ID（0~99），需在首次调用generate前设置
     */
    public static void setMachineId(int machineId) {
        if (machineId < 0 || machineId > 99) {
            throw new IllegalArgumentException("机器ID必须在0~99之间");
        }
        MACHINE_ID = machineId;
    }

    /**
     * 生成订单号（线程安全）
     */
    public static String generate() {
        synchronized (OrderCodeUtil.class) {
            String nowMinute = getCurrentMinute();

            // 每分钟重置序列
            if (!nowMinute.equals(CURRENT_MINUTE)) {
                CURRENT_MINUTE = nowMinute;
                SEQUENCE.set(0);
            }

            // 生成序列号
            int seq = SEQUENCE.getAndIncrement();
            if (seq > 9999) {
                throw new IllegalStateException("序列号溢出，请等待下一分钟");
            }

            // 拼接订单号
            return String.format("%s%02d%04d", CURRENT_MINUTE, MACHINE_ID, seq);
        }
    }

    // 获取当前时间戳
    private static String getCurrentMinute() {
        return LocalDateTime.now().format(TIME_FORMATTER);
    }

}
