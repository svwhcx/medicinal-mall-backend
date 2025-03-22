package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.service.DashboardService;
import com.medicinal.mall.mall.demos.vo.dashboard.DashboardProduct;
import com.medicinal.mall.mall.demos.vo.dashboard.OrderStatusVo;
import com.medicinal.mall.mall.demos.vo.dashboard.SmallData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/7 16:18
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * 商家查询今天未处理的订单的数量。
     * 以及今日的发货数量
     * @return
     */
    @GetMapping("/unprocessed")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<SmallData> getTodayUnprocessOrders(){
        return success(this.dashboardService.getTodayUnproccessOrders());
    }


    /**
     * 获取用户的订单数量和平均的订单金额
     * @return
     */
    @GetMapping("/todayOrder")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<SmallData> getTodayOrder(){
        return success(this.dashboardService.getTodayOrdersAndSales());
    }

    /**
     * 商家获取当前的库存数量和紧急的库存数量
     */
    @GetMapping("/allStock")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<SmallData> getAllStock(){
        return success(this.dashboardService.getAllStock());
    }


    /**
     * 商家获取今日的销售额，还有同周比的信息
     * @return
     */
    @GetMapping("/todaySales")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<SmallData> getTodaySales(){
        return success(this.dashboardService.getTodaySales());
    }


    /**
     * 商家获取最近一周的销售数量和销售的总的金额。
     * @return
     */
    @GetMapping("/lastWeekSales")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<List<OrderStatusVo>> getLastWeekSales(){
        return success(this.dashboardService.getLastWeekOrderSales());
    }

    /**
     * 商家获取最近一周的销售数量和销售的总的金额。
     * @return
     */
    @GetMapping("/lastMonthSales")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<List<OrderStatusVo>> getLastMonthSales(){
        return success(this.dashboardService.getLastMonthOrderSales());
    }


    /**
     * 商家获取热销的商品
     * @return
     */
    @GetMapping("/hotSellProduct")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<List<DashboardProduct>> getHotSellProducts(){
        return success(this.dashboardService.getHotSellProducts());
    }


    /**
     * 获取不同类别的商品的销售占比
     * @return
     */
    @GetMapping("/categorySales")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<List<OrderStatusVo>> getCategorySales(){
        return success(this.dashboardService.getCategoryNum());
    }


    /**
     * 商家获取当前的订单状态的分布的占比。
     * @return
     */
    @GetMapping("/statusRate")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<List<OrderStatusVo>> getOrderStatus(){
        return success(this.dashboardService.getOrderStatus());
    }
}
