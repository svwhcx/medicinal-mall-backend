package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.dao.CategoryDao;
import com.medicinal.mall.mall.demos.dao.DashboardDao;
import com.medicinal.mall.mall.demos.dao.OrderDao;
import com.medicinal.mall.mall.demos.dao.ProductDao;
import com.medicinal.mall.mall.demos.entity.Category;
import com.medicinal.mall.mall.demos.entity.Product;
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
                case 3:
                    orderStatusVo.setName("已完成");
                    break;
                case 4:
                    orderStatusVo.setName("已取消");
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
        return null;
    }
}
