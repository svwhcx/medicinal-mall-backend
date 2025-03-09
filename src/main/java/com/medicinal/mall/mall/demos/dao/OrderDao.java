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
            "select o.goods_id as productId,SUM(o.buy_num) as saleNum,p.price,p.stock as stock " +
                    "from `order` as o join product as p on p.id = o.goods_id " +
                    "and o.seller_id = #{sellerId} " +
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
    @Select("select count(1) as num,sum(`price`),date(`complete_time`) as keyTime" +
            " from `order` " +
            "where seller_id = #{sellerId} and `status` = 4 and complete_time >= DATE_SUB(NOW(),INTERVAL 1 WEEK) " +
            "group by complete_time order by keyTime")
    List<OrderStatusVo> getOrderLastWeek(Integer sellerId);
}
