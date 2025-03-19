package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.entity.Order;
import com.medicinal.mall.mall.demos.entity.OrderRefund;
import com.medicinal.mall.mall.demos.order.IOrderManagement;
import com.medicinal.mall.mall.demos.pay.IPay;
import com.medicinal.mall.mall.demos.query.OrderPageRequest;
import com.medicinal.mall.mall.demos.query.OrderRequest;
import com.medicinal.mall.mall.demos.query.RefundPageRequest;
import com.medicinal.mall.mall.demos.query.RefundRequest;
import com.medicinal.mall.mall.demos.service.OrderRefundService;
import com.medicinal.mall.mall.demos.service.OrderService;
import com.medicinal.mall.mall.demos.vo.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:18
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;


    @Autowired
    private OrderRefundService orderRefundService;


    @Autowired
    private IPay pay;

    /**
     * 用户添加一个订单，也就是没有支付的状态
     * @param order 订单信息
     * @return 返回当前添加的订单的订单编号，如果是单个商品，那么则还需要orderId
     */
    @PostMapping("/create")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<OrderVo> addOrderList(@RequestBody OrderRequest order){
        return success(orderService.add(order));
    }

    /**
     * 用户分页查询自己的订单
     * @param orderPageRequest 分页参数
     * @return 用户的订单列表
     */
//    @GetMapping("/list")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<PageVo<OrderListVo>> userQueryList(OrderPageRequest orderPageRequest){
        return success(orderService.userQueryByPage(orderPageRequest));
    }


    /**
     * 用户分页查询自己的订单
     * @param orderPageRequest 分页参数
     * @return 用户的订单列表
     */
    @GetMapping("/list")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<PageVo<SingleOrderVo>> userQueryOrdersPage(OrderPageRequest orderPageRequest){
        return success(orderService.userQueryOrdersPage(orderPageRequest));
    }



    /**
     * 用户获取一个订单的详细信息
     * @param orderVo 带有订单编号的信息
     * @return
     */
    @GetMapping
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<OrderDetailVo> userQueryOrder(OrderVo orderVo){
        return success(orderService.userQueryOrderDetailInfo(orderVo));
    }

    /**
     * TODO <p>
     * 商家获取所有的订单信息.
     * 就是包括没有处理的
     * @return
     */
    @GetMapping("/seller/list")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<PageVo<SingleOrderVo>> sellerQueryOrder(OrderPageRequest orderPageRequest){
        return success(this.orderService.sellerQueryOrders(orderPageRequest));
//        reutrn success(orderService.sellerQueryByPage(new OrderPageRequest(1)));
    }


    /**
     * 管理员发货一个订单
     */
    @PutMapping("/deliver/{orderId}")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<Void> sellerDeliverOrder(@PathVariable("orderId")Integer orderId){
        this.orderService.deliverOrder(orderId);
        return success();
    }


    /**
     * 用户取消一个订单
     * @param orderId 订单id
     * @return
     */
    @PutMapping("/cancel/{orderId}")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> cancelOrder(@PathVariable("orderId")Integer orderId){
        orderService.cancelOrder(orderId);
        return success();
    }

    /**
     * 用户确认一个订单已经完成, 也就是确认收货
     * @param orderId 订单id
     * @return
     */
    @PutMapping("/confirm/{orderId}")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> confirmOrder(@PathVariable("orderId")Integer orderId){
        orderService.completeOrder(orderId);
        return success();
    }


    /**
     * 获取一个订单的剩余过期时间
     * @param orderCode 订单的编号
     * @return
     */
    @GetMapping("/leftTime/{orderCode}")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Long> getLeftTime(@PathVariable("orderCode") String orderCode){
        return success(orderService.getLeftTime(orderCode));
    }

    /**
     * TODO<p>
     * 根据订单编号来进行支付操作。
     * @param orderVo 订单编号的详细信息。
     * @return
     */
    @PostMapping("/pay")
    public ResultVO<Void> payOrderByOrderCode(@RequestBody OrderVo orderVo){
        // 这里调用支付接口
        // TODO 这里也可能是调用查询支付的流水号。
        pay.pay(orderVo);
        return success();
    }


     /**
     * 用户对自己的已支付的订单进行退款的操作
     * @param refundRequest 退款的详细数据
     * @return
     */
    @PostMapping("/refund")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> userRefund(@RequestBody RefundRequest refundRequest){
        this.orderRefundService.createRefund(refundRequest);
        return success();
    }

    /**
     * 用户根据退款取消一个退款请求
     * @param refundId 退款的id
     * @return
     */
    @PutMapping("/{refundId}/refund/cancel")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> cancelRefund(@PathVariable("refundId") Integer refundId){
        this.orderRefundService.cancelRefund(refundId);
        return success();
    }

    /**
     *
     * 商家拒绝一位用户的退款操作
     * @param refundRequest 用户的订单退款操作
     * @return
     */
    @PutMapping("/refund/refuse")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<Void> refuseRefund(@RequestBody RefundRequest refundRequest){
        this.orderRefundService.refuseRefund(refundRequest);
        return success();
    }

    /**
     * 商家同意一个用户的退款操作
     * @param refundRequest 用户的订单退款操作
     * @return
     */
    @PutMapping("/refund/accept")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<Void> acceptRefund(@RequestBody RefundRequest refundRequest){
        this.orderRefundService.acceptRefund(refundRequest);
        return success();
    }


    /**
     * 商家获取自己的被退款的列表
     * @param refundPageRequest 退款的分页信息
     * @return
     */
    @GetMapping("/refund/list")
    @TokenVerify(value = {RoleEnum.seller,RoleEnum.user},isNeedInfo = true)
    public ResultVO<PageVo<OrderRefundVo>> getRefundVoList(RefundPageRequest refundPageRequest){
        return success(this.orderRefundService.sellerQueryRefundList(refundPageRequest));
    }


    /**
     * 商家或者用户根据id来查询一个退款的详细信息
     * @param refundId 退款的id
     * @return
     */
    @GetMapping("/refund/detail/{refundId}")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<OrderRefund> getRefundDetail(@PathVariable("refundId")Integer refundId){
        return success(this.orderRefundService.queryRefundDetail(refundId));
    }


}
