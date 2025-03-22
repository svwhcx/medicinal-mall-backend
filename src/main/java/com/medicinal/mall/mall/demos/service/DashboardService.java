package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.vo.dashboard.DashboardProduct;
import com.medicinal.mall.mall.demos.vo.dashboard.OrderStatusVo;
import com.medicinal.mall.mall.demos.vo.dashboard.SmallData;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/7 15:15
 */
public interface DashboardService {


    /**
     * 获取当前商家的热销的商品
     * @return 热销商品的列表
     */
    List<DashboardProduct> getHotSellProducts();

    /**
     * 商家获取不同的订单状态的对应的订单的数量。
     * @return
     */
    List<OrderStatusVo> getOrderStatus();

    /**
     * 商家获取自己的不同种类的商品的数量。
     * @return
     */
    List<OrderStatusVo> getCategoryNum();

    /**
     * 商家获取最近一周的订单销售的量（这里只计算订单的完成时间，是否已退款就不管了）
     * @return
     */
    List<OrderStatusVo> getLastWeekOrderSales();

    /**
     * 商家获取最近一个月的订单销售量（只统计订单的完成时间，是否已退款就不管了）
     * @return
     */
    List<OrderStatusVo> getLastMonthOrderSales();

    /**
     * 商家获取当天的未处理的订单的数量
     * @return
     */
    SmallData getTodayUnproccessOrders();

    /**
     * 商家获取今日的订单数量和平均的订单金额
     * @return
     */
    SmallData getTodayOrdersAndSales();

    /**
     * 商家获取今日的销售总额
     * @return
     */
    SmallData getTodaySales();

    /**
     * 获取所有的库存信息
     * @return
     */
    SmallData getAllStock();

}
