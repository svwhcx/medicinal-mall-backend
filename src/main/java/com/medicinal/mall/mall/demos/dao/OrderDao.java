package com.medicinal.mall.mall.demos.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicinal.mall.mall.demos.entity.Order;
import com.medicinal.mall.mall.demos.vo.dashboard.DashboardProduct;
import com.medicinal.mall.mall.demos.vo.dashboard.OrderStatusVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:07
 */
@Mapper
public interface OrderDao extends BaseMapper<Order> {


    @Select(
            "select o.goods_id as productId,SUM(o.buy_num) as saleNum,Sum(s.stock) as stock,p.price" +
                    " from `order` as o join product as p on p.id = o.goods_id " +
                    "join sku as s on s.product_id = p.id" +
                    " and o.seller_id = #{sellerId} " +
                    "group by o.goods_id " +
                    "order by saleNum desc limit 10")

    List<DashboardProduct> getHotSellProducts(Integer sellerId);


    /**
     * 商家获取不同状态的订单的数量。
     * @param sellerId 商家的id
     * @return
     */
    @Select(
            "select count(1) as num, `status` as groupId from `order` where seller_id = #{sellerId} group by groupId")
    List<OrderStatusVo> getOrderStatus(Integer sellerId);


    /**
     * 商家获取不同药材类型的订单的数量
     */
    @Select("select count(o.id) as num,p.category_id as groupId from `order` as o  inner join product as p on o.goods_id = p.id and o.seller_id = #{sellerId} group by groupId")
    List<OrderStatusVo> getOrderStatusByCategory(Integer sellerId);


    /**
     * 商家根据日期来获取一个周的每天的订单的数量，根据商家的sellerid来进行判断
     */

    @Select("SELECT\n" +
            "    ds.keyTime,\n" +
            "    IFNULL(COUNT(o.id), 0) AS num,\n" +
            "    IFNULL(SUM(o.price), 0) AS totalAmount\n" +
            "FROM (\n" +
            "    SELECT DATE_SUB(CURDATE(), INTERVAL seq.seq DAY) AS keyTime\n" +
            "    FROM (\n" +
            "        SELECT 0 AS seq UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4\n" +
            "        UNION ALL SELECT 5 UNION ALL SELECT 6\n" +
            "    ) AS seq\n" +
            ") AS ds\n" +
            "LEFT JOIN `order` o \n" +
            "    ON ds.keyTime = DATE(o.complete_time)\n" +
            "    AND o.seller_id = #{sellerId}\n" +
            "    AND o.`status` = 4\n" +
            "GROUP BY\n" +
            "    ds.keyTime\n" +
            "ORDER BY\n" +
            "    ds.keyTime;")
    List<OrderStatusVo> getOrderLastWeek(Integer sellerId);

    /**
     * 商家根据日期来获取一个月的每天订单的数量，根据商家的sellerid来进行判断
     */


    @Select("SELECT\n" +
            "    ds.keyTime,\n" +
            "    IFNULL(COUNT(o.id), 0) AS num,\n" +
            "    IFNULL(SUM(o.price), 0) AS total_Amount\n" +
            "FROM (\n" +
            "    SELECT DATE_SUB(CURDATE(), INTERVAL seq.seq DAY) AS keyTime\n" +
            "    FROM (\n" +
            "        SELECT 0 AS seq UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4\n" +
            "        UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9\n" +
            "        UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14\n" +
            "        UNION ALL SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19\n" +
            "        UNION ALL SELECT 20 UNION ALL SELECT 21 UNION ALL SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24\n" +
            "        UNION ALL SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL SELECT 29\n" +
            "    ) AS seq\n" +
            ") AS ds\n" +
            "LEFT JOIN `order` o \n" +
            "    ON ds.keyTime = DATE(o.complete_time)\n" +
            "    AND o.seller_id = #{sellerId}\n" +
            "    AND o.`status` = 4\n" +
            "WHERE\n" +
            "    ds.keyTime >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)\n" +
            "GROUP BY\n" +
            "    ds.keyTime\n" +
            "ORDER BY\n" +
            "    ds.keyTime;")
    List<OrderStatusVo> getOrderLastMonth(Integer sellerId);
}
