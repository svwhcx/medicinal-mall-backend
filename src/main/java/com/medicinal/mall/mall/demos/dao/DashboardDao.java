package com.medicinal.mall.mall.demos.dao;

import com.medicinal.mall.mall.demos.vo.dashboard.SmallData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @description 仪表盘对应的数据
 * @Author cxk
 * @Date 2025/3/7 11:33
 */
@Mapper
public interface DashboardDao {


    /**
     * 商家查询今日的订单数,和平均的订单金额
     * @param sellerId
     */

    @Select("select count(1) as bigNum , avg(price) as smallNum  from `order` where seller_id = #{sellerId} and create_time >= DATE_SUB(CURDATE(), INTERVAL 1 DAY)")
    SmallData selectTodayOrders(Integer sellerId);


    /**
     * 查询当前商家所有的未处理的订单数量
     */
    @Select("select count(1)   from `order` where seller_id = #{sellerid};")
    long selectAllUnprocessedOrders(Integer sellerId);


    /**
     * 查询当前商家今日的已发货的订单的数量，根据update_time来确定
     */
    @Select("select count(1)   from `order` where seller_id = #{sellerId} and update_time >= DATE_SUB(CURDATE(), INTERVAL 1 DAY) and `status` = 2")
    long selectTodayDeliveredOrders(Integer sellerId);


    /**
     * 查询商家今日的销售总额，包含多个商品的，就是用户已经付款的
     */
    @Select("select sum(price)  from `order` where seller_id = #{sellerId} and DATE(pay_time) = CURDATE()")
    Long selectTodaySales(Integer sellerId);

    @Select("select sum(price) from `order` where seller_id=#{sellerId} and DATE(pay_time) = DATE_SUB(CURDATE(),INTERVAL 7 day) ")
    Long selectLastWeekTodaySales(Integer sellerId);
}
