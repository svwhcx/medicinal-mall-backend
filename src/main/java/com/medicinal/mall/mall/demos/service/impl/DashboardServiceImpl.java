package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.dao.*;
import com.medicinal.mall.mall.demos.entity.Category;
import com.medicinal.mall.mall.demos.entity.Product;
import com.medicinal.mall.mall.demos.entity.SKU;
import com.medicinal.mall.mall.demos.service.DashboardService;
import com.medicinal.mall.mall.demos.vo.dashboard.DashboardProduct;
import com.medicinal.mall.mall.demos.vo.dashboard.OrderStatusVo;
import com.medicinal.mall.mall.demos.vo.dashboard.SmallData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/7 15:18
 */
@Service
public class DashboardServiceImpl implements DashboardService {


    @Autowired
    private DashboardDao dashboardDao;


    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SkuDao skuDao;

    @Override
    public List<DashboardProduct> getHotSellProducts() {
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        // 商品dao
        List<DashboardProduct> hotSellProducts = this.orderDao.getHotSellProducts(sellerId);
        for (DashboardProduct hotSellProduct : hotSellProducts) {
            // 调用product服务查询商品的名称
            LambdaQueryWrapper<Product> productQueryWrapper = new LambdaQueryWrapper<>();
            productQueryWrapper.eq(Product::getId, hotSellProduct.getProductId())
                    .select(Product::getName);
            Product product = this.productDao.selectOne(productQueryWrapper);
            hotSellProduct.setName(product.getName());
        }
        return hotSellProducts;
    }

    @Override
    public List<OrderStatusVo> getOrderStatus() {
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        List<OrderStatusVo> orderStatus = this.orderDao.getOrderStatus(sellerId);
        // 映射字段
        orderStatus.forEach(orderStatusVo -> {
            switch (orderStatusVo.getGroupId()) {
                case 0:
                    orderStatusVo.setName("待付款");
                    break;
                case 1:
                    orderStatusVo.setName("待发货");
                    break;
                case 2:
                    orderStatusVo.setName("待收货");
                    break;
                case 4:
                    orderStatusVo.setName("已完成");
                    break;
                case 5:
                    orderStatusVo.setName("已取消");
                    break;
            }
        });
        return orderStatus;
    }

    @Override
    public List<OrderStatusVo> getCategoryNum() {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        List<OrderStatusVo> categoryNum = this.orderDao.getOrderStatusByCategory(userId);
        categoryNum.forEach(orderStatusVo -> {
            // 根据id查询名称
            Category category = this.categoryDao.selectById(orderStatusVo.getGroupId());
            orderStatusVo.setName(category.getName());
        });
        return categoryNum;
    }

    @Override
    public List<OrderStatusVo> getLastWeekOrderSales() {
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        return this.orderDao.getOrderLastWeek(sellerId);
    }

    @Override
    public List<OrderStatusVo> getLastMonthOrderSales() {
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        return this.orderDao.getOrderLastMonth(sellerId);
    }

    @Override
    public SmallData getTodayUnproccessOrders() {
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        long unProcess = this.dashboardDao.selectAllUnprocessedOrders(sellerId);
        long delivered = this.dashboardDao.selectTodayDeliveredOrders(sellerId);
        return new SmallData("待处理订单", String.valueOf(unProcess), String.valueOf(delivered));
    }

    @Override
    public SmallData getTodayOrdersAndSales() {

        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        return this.dashboardDao.selectTodayOrders(sellerId);
    }

    @Override
    public SmallData getTodaySales() {
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        // 获取今日的订单金额
        Long l = dashboardDao.selectTodaySales(sellerId);
        Long l1 = dashboardDao.selectLastWeekTodaySales(sellerId);
        SmallData smallData = new SmallData();

        if (l == null) {
            l = 0L;
        }

        smallData.setBigNum(String.valueOf(l));
        // 计算周同比
        if (l == 0L && l1 == null) {
            smallData.setSmallNum("+0%");
        } else if (l1 == null){
            // 上周的这一天没有订单，但是今天有订单
            smallData.setSmallNum("+100%");
        }else{
            smallData.setSmallNum((l - l1)/l1*100+"%");
        }
        // 获取上一周同一天的订单金额
        return smallData;
    }

    @Override
    public SmallData getAllStock() {
        SmallData smallData = new SmallData();
        Long allStock = skuDao.getAllStock();
        smallData.setBigNum(allStock.toString());
        smallData.setSmallNum(allStock.toString());
        return smallData;
    }
}
