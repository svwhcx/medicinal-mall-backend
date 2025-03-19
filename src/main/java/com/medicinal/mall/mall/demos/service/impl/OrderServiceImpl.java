package com.medicinal.mall.mall.demos.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.constant.OrderStatusConstant;
import com.medicinal.mall.mall.demos.constant.RefundStatusConstant;
import com.medicinal.mall.mall.demos.dao.*;
import com.medicinal.mall.mall.demos.entity.*;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.order.IOrderManagement;
import com.medicinal.mall.mall.demos.query.OrderPageRequest;
import com.medicinal.mall.mall.demos.query.OrderRequest;
import com.medicinal.mall.mall.demos.query.RefundPageRequest;
import com.medicinal.mall.mall.demos.query.RefundRequest;
import com.medicinal.mall.mall.demos.service.OrderRefundService;
import com.medicinal.mall.mall.demos.service.UserService;
import com.medicinal.mall.mall.demos.util.OrderCodeUtil;
import com.medicinal.mall.mall.demos.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:22
 */
@Service
public class OrderServiceImpl implements OrderRefundService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserService userService;


    @Autowired
    private OrderRefundDao orderRefundDao;


    @Autowired
    private ProductDao productDao;

    @Autowired
    private IOrderManagement orderManagement;


    @Autowired
    private UserAddrDao userAddrDao;

    @Autowired
    private GoodsCommentDao goodsCommentDao;

    @Autowired
    private SkuDao skuDao;


    @Override
    public PageVo<SingleOrderVo> userQueryOrdersPage(OrderPageRequest orderPageRequest) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        // 用户分页获取自己的订单信息
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId)
                .orderByDesc(Order::getCreateTime);
        if (orderPageRequest.getStatus() != null) {
            queryWrapper.eq(Order::getStatus, orderPageRequest.getStatus());
        }
        if (!StringUtils.isEmpty(orderPageRequest.getOrderCode())) {
            queryWrapper.eq(Order::getOrderCode, orderPageRequest.getOrderCode());
        }
        // 分页查询数据
        Page<Order> orderPage = this.orderDao.selectPage(new Page<>(orderPageRequest.getPageNum(), orderPageRequest.getPageSize()), queryWrapper);
        List<SingleOrderVo> singleOrderVos = new ArrayList<>();
        for (Order order : orderPage.getRecords()) {
            // 单个的order需要的东西,比如剩余时间
            SingleOrderVo singleOrderVo = new SingleOrderVo();
            BeanUtils.copyProperties(order, singleOrderVo);
            // 计算剩余时间
            if (order.getStatus() < 1 && (order.getIsExpire() != null && !order.getIsExpire())) {
                singleOrderVo.setLeftTime((order.getInvalidationTime() - System.currentTimeMillis()) / 1000L);
            }
            // 商品的名称
            Product product = this.productDao.selectById(order.getGoodsId());
            singleOrderVo.setName(product.getName());
            singleOrderVo.setImg(product.getImg());
            singleOrderVos.add(singleOrderVo);
        }

        PageVo<SingleOrderVo> pageVo = new PageVo<>();
        pageVo.setPageNum(orderPage.getCurrent());
        pageVo.setTotal(orderPage.getTotal());
        pageVo.setList(singleOrderVos);
        return pageVo;
    }

    /**
     * 商家分页获取它自己的订单的信息
     *
     * @param orderPageRequest
     * @return
     */
    @Override
    public PageVo<SingleOrderVo> sellerQueryOrders(OrderPageRequest orderPageRequest) {
        Integer sellerID = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getSellerId, sellerID);
        if (orderPageRequest.getStatus() != null) {
            queryWrapper.eq(Order::getStatus, orderPageRequest.getStatus());
        }
        if (!StringUtils.isEmpty(orderPageRequest.getOrderCode())) {
            queryWrapper.eq(Order::getOrderCode, orderPageRequest.getOrderCode());
        }
        Page<Order> orderPage = this.orderDao.selectPage(new Page<>(orderPageRequest.getPageNum(), orderPageRequest.getPageSize()), queryWrapper);
        List<SingleOrderVo> singleOrderVos = new ArrayList<>();
        for (Order order : orderPage.getRecords()) {
            SingleOrderVo singleOrderVo = new SingleOrderVo();
            BeanUtils.copyProperties(order, singleOrderVo);
            Product product = this.productDao.selectById(order.getGoodsId());
            singleOrderVo.setImg(product.getImg());
            singleOrderVo.setName(product.getName());
            singleOrderVos.add(singleOrderVo);
        }
        PageVo<SingleOrderVo> pageVo = new PageVo<>();
        pageVo.setTotal(orderPage.getTotal());
        pageVo.setPageNum(orderPage.getCurrent());
        pageVo.setList(singleOrderVos);
        return pageVo;
    }

    @Override
    public PageVo<OrderListVo> userQueryByPage(OrderPageRequest orderPageRequest) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId);
        // 根据status来进行切换
        if (orderPageRequest.getStatus() != null) {
            // TODO 处理订单退款的情况。
            queryWrapper.eq(Order::getStatus, orderPageRequest.getStatus());
            // 如果是退款中，则查询对应的退款订单
//            if (Objects.equals(orderPageRequest.getStatus(), OrderStatusConstant.REFUNDING)) {
//                queryWrapper.eq(Order::getRefundId, orderPageRequest.getRefundId());
//            }
        }
        queryWrapper.orderByDesc(Order::getCreateTime)
                .groupBy(Order::getOrderCode);
        Page<Order> orderPage = this.orderDao.selectPage(new Page<>(orderPageRequest.getPageNum(), orderPageRequest.getPageSize()), queryWrapper);


        /**
         * 这里要单独处理退款的情况。
         */
/*
        if (OrderStatusConstant.REFUNDING.equals(orderPageRequest.getStatus()) || OrderStatusConstant.REFUNDED.equals(orderPageRequest.getStatus())){
            Integer refundStatus = OrderStatusConstant.REFUNDING.equals(orderPageRequest.getStatus())?RefundStatusConstant.REFUND_STATUS_REFUNDING:RefundStatusConstant.REFUND_STATUS_ACCEPTED;
            // 如果是退款中，那么应该分页查询退款的数据
            LambdaQueryWrapper<OrderRefund> orderRefundQueryWrapper = new LambdaQueryWrapper<OrderRefund>()
                    .eq(OrderRefund::getOrderCode, orderPageRequest.getOrderCode())
                    .eq(OrderRefund::getStatus, refundStatus)
                    .orderByDesc(OrderRefund::getCreateTime);
            Page<OrderRefund> orderRefundPage = this.orderRefundDao.selectPage(new Page<>(orderPageRequest.getPageNum(), orderPageRequest.getPageSize()), orderRefundQueryWrapper);
            // 分页后联合查询订单表的数据
            for (OrderRefund orderRefund : orderRefundPage.getRecords()) {
                LambdaQueryWrapper<Order> orderQueryWdrapper = new LambdaQueryWrapper<>();
                orderQueryWrapper.eq(Order::getOrderCode, orderRefund.getOrderCode())
                        .eq(Order::getStatus, OrderStatusConstant.REFUNDING)
                        .eq(Order::getRefundId, orderRefund.getId());
                Order order = this.orderDao.selectOne(orderQueryWrapper);
            }
        }*/

        List<OrderListVo> orderListVos = new ArrayList<>();
        // TODO 进行订单的详细情况中进行查询
        for (Order order : orderPage.getRecords()) {
            // 根据order的编号，查询全部的商品订单
            List<Order> orderList = this.orderDao.selectList(new LambdaQueryWrapper<Order>()
                    .eq(Order::getOrderCode, order.getOrderCode())
                    .orderByDesc(Order::getCreateTime));
            // 进行数据的组合
            OrderListVo orderListVo = new OrderListVo();
            orderListVo.setOrderCode(order.getOrderCode());
            orderListVo.setCreateTime(order.getCreateTime());
            orderListVo.setStatus(order.getStatus());

            // 计算订单的金额
            BigDecimal totalAmount = new BigDecimal(0);

            // 组装订单内部的商品信息
            List<OrderProductVo> orderProductVos = new ArrayList<>();
            for (Order orderOrder : orderList) {
                // 查询商品信息，然后返回组装
                LambdaQueryWrapper<Product> productQueryWrapper = new LambdaQueryWrapper<>();
                productQueryWrapper.eq(Product::getId, orderOrder.getGoodsId())
                        .select(Product::getImg, Product::getName, Product::getPrice, Product::getId);
                Product product = this.productDao.selectOne(productQueryWrapper);
                OrderProductVo orderProductVo = new OrderProductVo();
                orderProductVo.setImg(product.getImg());
                orderProductVo.setOrderId(order.getId());
                orderProductVo.setPrice(order.getPrice());
                orderProductVo.setNum(order.getBuyNum());
                orderProductVo.setName(product.getName());
                orderProductVo.setStatus(order.getStatus());
                totalAmount = totalAmount.add(orderOrder.getPrice());
                // TODO 这里应该是商品的SKU信息也要，待定
                orderProductVos.add(orderProductVo);
            }
            orderListVo.setTotalAmount(totalAmount);
            // 设置当前订单中的商品的信息
            orderListVo.setOrderProductVoList(orderProductVos);
            // 向当前的订单列表中添加数据
            orderListVos.add(orderListVo);
        }
        PageVo<OrderListVo> pageVo = new PageVo<>();
        pageVo.setPageNum(orderPage.getCurrent());
        pageVo.setTotal(orderPage.getTotal());
        pageVo.setList(orderListVos);
        return pageVo;
    }

    @Override
    public OrderDetailVo userQueryOrderDetailInfo(OrderVo orderVo) {
        // 根据订单编号查询数据
        // 同时要验证订单编号是否是当前的用户创建的
        Integer userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<>();
        orderQueryWrapper
                .eq(Order::getOrderCode, orderVo.getOrderCode())
                .eq(Order::getUserId, userId);
        List<Order> orderList = this.orderDao.selectList(orderQueryWrapper);
        if (orderList == null || orderList.isEmpty()) {
            // 如果当前用户没有这个订单编号，则提示非法操作！
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        // 获取并配置收货地址的详细数据
        LambdaQueryWrapper<UserAddr> userAddrQueryWrapper = new LambdaQueryWrapper<>();
        userAddrQueryWrapper.eq(UserAddr::getId, orderList.get(0).getAddrId());
        UserAddr userAddr = this.userAddrDao.selectOne(userAddrQueryWrapper);
        if (userAddr == null) {
            // 订单地址为空，直接抛出异常（正常来说这里是不太可能为空的）
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        return getOrderDetailVo(userAddr, orderList);
    }

    /**
     * 获取一个订单的详细数据，需要配合商品、评论等信息
     *
     * @param userAddr  用户的地址
     * @param orderList 用户的同已订单编号的商品订单。
     * @return
     */
    private OrderDetailVo getOrderDetailVo(UserAddr userAddr, List<Order> orderList) {
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setReceipt(userAddr.getRecipient());
        orderDetailVo.setContactDetailInfo(userAddr.getContactDetail());
        orderDetailVo.setAddr(userAddr.getAddr());

        // 配置订单数据。
        orderDetailVo.setCreateTime(orderList.get(0).getCreateTime());
        orderDetailVo.setPayTime(orderList.get(0).getPayTime());
        orderDetailVo.setOrderCode(orderList.get(0).getOrderCode());

        // 这里要计算不同的商品的总金额
        BigDecimal totalAmount = new BigDecimal(0);
        // 构建同意订单号的各个商品的情况
        List<OrderProductVo> orderProductVos = new ArrayList<>();
        for (Order order : orderList) {
            // 查询单个商品的详细数据
            // 这里只拿需要的数据，提高响应速度
            LambdaQueryWrapper<Product> productQueryWrapper = new LambdaQueryWrapper<>();
            productQueryWrapper.eq(Product::getId, order.getGoodsId())
                    .select(Product::getImg, Product::getName, Product::getPrice, Product::getId);
            Product product = this.productDao.selectOne(productQueryWrapper);
            totalAmount = totalAmount.add(order.getPrice());
            OrderProductVo orderProductVo = new OrderProductVo();
            orderProductVo.setProductId(product.getId());
            orderProductVo.setImg(product.getImg());
            orderProductVo.setPrice(order.getPrice());
            orderProductVo.setStatus(order.getStatus());
            orderProductVo.setNum(order.getBuyNum());
            orderProductVo.setName(product.getName());
            // 将订单号一起带上，用于单个商品的退款操作。
            orderProductVo.setOrderId(order.getId());
            // 查询是否评论过该商品
            LambdaQueryWrapper<GoodsComment> goodsCommentQueryWrapper = new LambdaQueryWrapper<>();
            goodsCommentQueryWrapper.eq(GoodsComment::getUserId, UserInfoThreadLocal.get().getUserId())
                    .eq(GoodsComment::getGoodsId, product.getId());
            long isComment = this.goodsCommentDao.selectCount(goodsCommentQueryWrapper);
            orderProductVo.setIsComment(isComment != 0);
            orderProductVos.add(orderProductVo);
        }
        // 配置本次订单的总金额
        orderDetailVo.setTotalAmount(totalAmount);
        orderDetailVo.setOrderProductVoList(orderProductVos);
        return orderDetailVo;
    }


    @Override
    public PageVo<Order> sellerQueryByPage(OrderPageRequest orderPageRequest) {
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<>();
        if (orderPageRequest.getStatus() != null) {
            orderQueryWrapper.eq(Order::getStatus, orderPageRequest.getStatus());
        }
        if (orderPageRequest.getOrderCode() != null) {
            orderQueryWrapper.eq(Order::getOrderCode, orderPageRequest.getOrderCode());
        }
        orderQueryWrapper.eq(Order::getSellerId, UserInfoThreadLocal.get().getUserId());
        Page<Order> orderPage = this.orderDao.selectPage(new Page<>(orderPageRequest.getPageNum(), orderPageRequest.getPageSize()), orderQueryWrapper);
        return PageVo.build(orderPage);
    }


    @Override
    @Transactional
    public OrderVo add(OrderRequest orderRequest) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        String orderCode = OrderCodeUtil.generate();
        List<Order> orderList = orderRequest.getOrders();
        // 同一批次的多个商品订单信息，共享同一个订单编号
        orderList.forEach(order -> {
            LambdaQueryWrapper<Product> productQueryWrapper = new LambdaQueryWrapper<>();
            productQueryWrapper.eq(Product::getId, order.getGoodsId());
//                    .select(Product::getSellerId)
//                    .select(Product::getPrice);
            Product product = this.productDao.selectOne(productQueryWrapper);
            order.setSellerId(product.getSellerId());
            // 设置价格
            SKU sku = skuDao.selectById(order.getSkuId());
            // 先判断商品的库存是否足够
            if (sku.getStock() < order.getBuyNum()) {
                throw new ParamException("商品: "+ product.getName() + "没有足够的库存!");
            }
            order.setPrice(sku.getPrice().multiply(BigDecimal.valueOf(order.getBuyNum())));
            order.setOrderCode(orderCode);
            order.setCreateTime(LocalDateTime.now());
            order.setInvalidationTime(System.currentTimeMillis() + IOrderManagement.DEFAULT_EXPIRE_TIME);
            order.setUserId(userId);
            order.setStatus(OrderStatusConstant.CREATED);
            order.setAddrId(orderRequest.getAddressId());

            // 提前锁单，进行扣减库存的操作
            LambdaUpdateWrapper<SKU> skuUpdateWrapper = new LambdaUpdateWrapper<>();
            skuUpdateWrapper.eq(SKU::getId, order.getSkuId());
            skuUpdateWrapper.setSql("stock = stock - " + order.getBuyNum());
            skuDao.update(skuUpdateWrapper);
        });
//
//        // 进行锁单的操作
//        // 在创建多个订单之前，先去预扣除对应商品的库存信息
//        orderList.forEach(order -> {
//            // TODO 订单数量溢出检查
//            // TODO 后续的优化方案，可以建立多个订单池
//            // TODO 来应对高并发库存减为负的场景
//            LambdaUpdateWrapper<Product> productUpdateWrapper = new LambdaUpdateWrapper<>();
//            productUpdateWrapper.eq(Product::getId, order.getGoodsId());
//            productUpdateWrapper.setSql("stock = stock - " + order.getBuyNum());
////            productUpdateWrapper.setSql("stock = stock - " + order.getBuyNum() + "where stock - num > 0");
//            this.productDao.update(productUpdateWrapper);
//        });
        // 一起插入
        this.orderDao.insert(orderList);
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderCode(orderCode);
        orderManagement.addOrderWork(orderVo);
        // 插入成功后，还需要将当前的订单编号，放到订单管理器中
        // 用于计算订单的超时状态，以及过期的状态。
        return orderVo;
    }

    @Override
    public void paySuccess(OrderVo orderVo) {
        // 一个订单支付成功了修改订单的状态
        // TODO 是否要通知订单过期队列，让它执行移除队列的操作。
        // TODO 如果为了加速处理的话，那么可以使用Redis做缓存，
        // TODO 缓存订单编号，以及订单编号的过期时间。
        // TODO 而不用保存到数据库中去。
        LambdaUpdateWrapper<Order> orderUpdateWrapper = new LambdaUpdateWrapper<>();
        orderUpdateWrapper.eq(Order::getOrderCode, orderVo.getOrderCode())
                .set(Order::getStatus, OrderStatusConstant.PAYED)
                .set(Order::getPayTime, LocalDateTime.now());
        this.orderDao.update(orderUpdateWrapper);
    }

    @Override
    public void completeOrder(Integer orderId) {
        // 这个过程是需要用户自己点击收货。
        LambdaUpdateWrapper<Order> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Order::getId, orderId)
                .set(Order::getStatus, OrderStatusConstant.COMPLETED)
                .set(Order::getCompleteTime, LocalDateTime.now());
        this.orderDao.update(updateWrapper);
    }

    @Override
    public void expireOrder(Order order) {
        order.setIsExpire(true);
        order.setExpireTime(LocalDateTime.now());
        this.orderDao.updateById(order);
        // 订单过期应该对订单中的商品的库存进行恢复。
    }

    @Override
    public void updateOrder(OrderVo orderVo) {
        // 验证当前操作的合法性
        Integer userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<>();
        orderQueryWrapper
                .ne(Order::getStatus, OrderStatusConstant.CREATED)
                .eq(Order::getOrderCode, orderVo.getOrderCode())
                .eq(Order::getUserId, userId);
        if (this.orderDao.selectCount(orderQueryWrapper) == 0) {
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        // 只能修改地址信息
        // 然后进行所有的该订单号的地址修改操作。
        LambdaUpdateWrapper<Order> orderUpdateWrapper = new LambdaUpdateWrapper<>();
        orderUpdateWrapper.eq(Order::getOrderCode, orderVo.getOrderCode())
                .set(Order::getAddrId, orderVo.getAddrId());
        this.orderDao.update(orderUpdateWrapper);
    }

    @Override
    @Transactional
    public void cancelOrder(Integer orderId) {
        // 用户取消一个订单的操作。
        // 更新当前批次的订单里面的所有的订单的信息。
        LambdaUpdateWrapper<Order> orderUpdateWrapper = new LambdaUpdateWrapper<>();
        orderUpdateWrapper.eq(Order::getId, orderId)
                .set(Order::getStatus, OrderStatusConstant.CANCELED);
        this.orderDao.update(orderUpdateWrapper);
        // 取消订单了，要将订单里面的商品的库存数量进行恢复。
        recoverStock(orderId);
    }


    @Override
    @Transactional
    public void recoverStock(Integer orderId) {
        // 恢复订单中的商品的库存信息
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<>();
        orderQueryWrapper
                .eq(Order::getId, orderId)
                .select(Order::getGoodsId, Order::getBuyNum,Order::getSkuId);
        Order order = this.orderDao.selectOne(orderQueryWrapper);
        // 进行回退操作。
        recoverOrderStock(order);
    }

    @Override
    public void deliverOrder(Integer orderId) {
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        LambdaUpdateWrapper<Order> orderUpdateWrapper = new LambdaUpdateWrapper<>();
        orderUpdateWrapper.eq(Order::getId, orderId)
                .set(Order::getStatus, OrderStatusConstant.DELIVERED)
                .eq(Order::getSellerId, sellerId);
        this.orderDao.update(orderUpdateWrapper);
    }

    @Override
    public Long getLeftTime(String orderCode) {
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getOrderCode, orderCode)
                .select(Order::getInvalidationTime);
        List<Order> orderList = this.orderDao.selectList(orderLambdaQueryWrapper);
        if (orderList.isEmpty()){
            return -1L;
        }
        return (orderList.get(0).getInvalidationTime() - System.currentTimeMillis())/1000;
    }

    /**
     * 恢复一个商品的库存
     *
     * @param order 某一个具体的订单，包含商品的id和购买的数据信息。
     */
    private void recoverOrderStock(Order order) {
        // 更新的是SKU中的库存数据
        LambdaUpdateWrapper<SKU> skuUpdateWrapper = new LambdaUpdateWrapper<>();
        skuUpdateWrapper.eq(SKU::getId, order.getSkuId())
                .setSql("stock = stock + " + order.getBuyNum());
        skuDao.update(skuUpdateWrapper);
//        // 恢复商品的库存
//        LambdaUpdateWrapper<Product> productUpdateWrapper = new LambdaUpdateWrapper<>();
//        productUpdateWrapper.eq(Product::getId, order.getGoodsId())
//                .setSql("stock = stock + " + order.getBuyNum());
//        // 这里应该是更新的SKU中的库存
//        this.productDao.update(productUpdateWrapper);
    }

    @Override
    @Transactional
    public void createRefund(RefundRequest refundRequest) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        // 根据一个订单编号和订单的id来查询具体的订单信息
        // 同样要对用户的操作进行合法性验证，判断是否是当前用户的订单数据。
        Order order = this.orderDao.selectOne(new LambdaQueryWrapper<Order>()
//                .eq(Order::getOrderCode, refundRequest.getOrderCode())
                .eq(Order::getUserId, userId)
                .eq(Order::getId, refundRequest.getOrderId()));
        if (order == null) {
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        if (order.getRefundId() != null) {
            throw new ParamException(ResponseDataEnum.ORDER_HAS_REFUND);
        }

        // 拼接图片列表,目前是直接拼接的地址。

        StringBuilder stringBuilder = new StringBuilder();
        if (refundRequest.getImgs() != null) {
            for (String img : refundRequest.getImgs()) {
                stringBuilder.append(img).append(",");
            }
        }


        // 用户发起一个退款申请并填充一些信息
        OrderRefund orderRefund = new OrderRefund();
        orderRefund.setUserId(userId);
        orderRefund.setOrderId(order.getId());
        orderRefund.setOrderCode(order.getOrderCode());
        orderRefund.setReason(refundRequest.getRefundReason());
        orderRefund.setImages(stringBuilder.toString());
        orderRefund.setOrderAmount(order.getPrice());
        orderRefund.setCreateTime(LocalDateTime.now());
        orderRefund.setStatus(RefundStatusConstant.REFUND_STATUS_REFUNDING);
        // 这里要插入sellerId
        LambdaQueryWrapper<Product> productQueryWrapper = new LambdaQueryWrapper<>();
        productQueryWrapper.eq(Product::getId, order.getGoodsId())
                .select(Product::getSellerId);
        Product product = this.productDao.selectOne(productQueryWrapper);
        orderRefund.setSellerId(product.getSellerId());
        this.orderRefundDao.insert(orderRefund);
        // 然后修改订单表中的status状态为退款中。
        LambdaUpdateWrapper<Order> orderUpdateWrapper = new LambdaUpdateWrapper<>();
        orderUpdateWrapper.eq(Order::getId, order.getId())
                .set(Order::getRefundId, orderRefund.getId());
        this.orderDao.update(orderUpdateWrapper);

    }

    @Override
    public void acceptRefund(RefundRequest refundRequest) {
        // 商家同意一个用户的退款申请
        // 先校验商家的处理权限
        checkSellerRefund(refundRequest);
        // 现在直接更新退款的信息
        LambdaUpdateWrapper<OrderRefund> orderRefundUpdateWrapper = new LambdaUpdateWrapper<>();
        orderRefundUpdateWrapper.eq(OrderRefund::getId, refundRequest.getRefundId())
                .set(OrderRefund::getUpdateTime, LocalDateTime.now())
                .set(OrderRefund::getStatus, RefundStatusConstant.REFUND_STATUS_ACCEPTED);
        // 直接进行更新操作。
        this.orderRefundDao.update(orderRefundUpdateWrapper);
    }

    @Override
    @Transactional
    public void cancelRefund(Integer refundId) {
        // 根据退款id取消一个退款操作。
        // 1. 先验证用户操作是否合法
        Integer userId = UserInfoThreadLocal.get().getUserId();
        // 1.1 先根据退款id找到退款的详细信息
        OrderRefund orderRefund = this.orderRefundDao.selectById(refundId);
        if (orderRefund == null) {
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        // 1.2. 根据orderRefund中的orderID查询对应的订单信息
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<>();
        orderQueryWrapper.eq(Order::getId, orderRefund.getOrderId())
                .eq(Order::getUserId, userId);
        boolean exist = this.orderDao.exists(orderQueryWrapper);
        if (!exist) {
            // 1.3 如果不存在，则直接非法操作
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }

        // 2. 验证通过，修改退款状态为取消，同时将订单表的退款id置为空
        // 2.1 修改退款的状态
        // TODO 如果在用户取消退款的时候，商家点击了同意退款，则会导致订单信息不会改变
        LambdaUpdateWrapper<OrderRefund> orderRefundUpdateWrapper = new LambdaUpdateWrapper<>();
        orderRefundUpdateWrapper.eq(OrderRefund::getId, refundId)
                .set(OrderRefund::getUpdateTime, LocalDateTime.now())
                .set(OrderRefund::getStatus, RefundStatusConstant.REFUND_STATUS_CANCELED);
        this.orderRefundDao.update(orderRefundUpdateWrapper);
        // 2.2 然后将订单表中的退款id置为空，表示当前订单可以继续进行
        LambdaUpdateWrapper<Order> orderUpdateWrapper = new LambdaUpdateWrapper<>();
        orderUpdateWrapper.eq(Order::getId, orderRefund.getOrderId())
                .set(Order::getRefundId, null);
        this.orderDao.update(orderUpdateWrapper);
    }

    @Override
    public void refuseRefund(RefundRequest refundRequest) {
        // 商家拒绝一个用户的退款申请
        // 校验商家的操作是否合法。
        checkSellerRefund(refundRequest);
        // 现在直接更新退款的信息，将状态改为拒绝状态。
        LambdaUpdateWrapper<OrderRefund> orderRefundUpdateWrapper = new LambdaUpdateWrapper<>();
        orderRefundUpdateWrapper.eq(OrderRefund::getId, refundRequest.getRefundId())
                .set(OrderRefund::getRefuseReason, refundRequest.getRefuseReason())
                .set(OrderRefund::getUpdateTime, LocalDateTime.now())
                .set(OrderRefund::getStatus, RefundStatusConstant.REFUND_STATUS_REFUSED);
        // 直接进行更新操作。
        this.orderRefundDao.update(orderRefundUpdateWrapper);
    }

    @Override
    public PageVo<OrderRefundVo> sellerQueryRefundList(RefundPageRequest refundPageRequest) {
        // 获取当前商家的退款申请列表
        Integer userId = UserInfoThreadLocal.get().getUserId();
        // 这里要进行refund、order、product的联合分页查询了，
        // 这里如果是多个
        LambdaQueryWrapper<OrderRefund> orderRefundQueryWrapper = new LambdaQueryWrapper<>();
        if (refundPageRequest.getStatus() != null) {
            if (refundPageRequest.getStatus().equals(RefundStatusConstant.REFUND_STATUS_COMPLETED)) {
                orderRefundQueryWrapper.gt(OrderRefund::getStatus, RefundStatusConstant.REFUND_STATUS_REFUNDING);
            } else {
                orderRefundQueryWrapper.eq(OrderRefund::getStatus, refundPageRequest.getStatus());
            }
        }
        if (!StringUtils.isEmpty(refundPageRequest.getOrderCode())) {
            orderRefundQueryWrapper.eq(OrderRefund::getOrderCode, refundPageRequest.getOrderCode());
        }
        Integer roleId = UserInfoThreadLocal.get().getRoleId();
        if (roleId.equals(RoleEnum.seller.getRoleId())) {
            orderRefundQueryWrapper.eq(OrderRefund::getSellerId, userId);
        } else if (roleId.equals(RoleEnum.user.getRoleId())) {
            orderRefundQueryWrapper.eq(OrderRefund::getUserId, userId);
        }
        orderRefundQueryWrapper.orderByDesc(OrderRefund::getCreateTime);
        Page<OrderRefund> orderRefundPage = this.orderRefundDao.selectPage(new Page<>(refundPageRequest.getPageNum(), refundPageRequest.getPageSize()), orderRefundQueryWrapper);
        // 分页查询后组装商品的信息
        List<OrderRefundVo> orderRefundVos = new ArrayList<>();
        for (OrderRefund orderRefund : orderRefundPage.getRecords()) {
            OrderRefundVo orderRefundVo = new OrderRefundVo();
            BeanUtils.copyProperties(orderRefund, orderRefundVo);
            Order order = this.orderDao.selectById(orderRefund.getOrderId());
            Product product = this.productDao.selectById(order.getGoodsId());
            orderRefundVo.setProductName(product.getName());
            orderRefundVo.setProductImage(product.getImg());
            orderRefundVo.setProductId(product.getId());
            orderRefundVos.add(orderRefundVo);
        }
        PageVo<OrderRefundVo> pageVo = new PageVo<>();
        pageVo.setTotal(orderRefundPage.getTotal());
        pageVo.setPageNum(orderRefundPage.getCurrent());
        pageVo.setList(orderRefundVos);
        return pageVo;
    }

    @Override
    public OrderRefund queryRefundDetail(Integer refundId) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        OrderRefund orderRefund = this.orderRefundDao.selectById(refundId);
        if (orderRefund == null) {
            // 如果不存在则非法操作
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        return orderRefund;
    }

    /**
     * 校验当前的商家对当前的退款订单的操作权限
     *
     * @param refundRequest 包含订单和详细的退款请求
     */
    private void checkSellerRefund(RefundRequest refundRequest) {
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        // 验证当前的订单对应的商品是不是当前用户创建的
        // 1. 现根据订单号和订单编号查询商品id
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<>();
        orderQueryWrapper.eq(Order::getOrderCode, refundRequest.getOrderCode())
                .eq(Order::getGoodsId, refundRequest.getGoodsId());
        Order order = this.orderDao.selectOne(orderQueryWrapper);
        if (order == null) {
            // 如果当前的订单编号和订单id不存在，则说明非法操作。
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        // 2. 验证商品的所有者是否是当前的用户。
        LambdaQueryWrapper<Product> productQueryWrapper = new LambdaQueryWrapper<>();
        productQueryWrapper.eq(Product::getSellerId, sellerId)
                .eq(Product::getId, order.getGoodsId());
        boolean exists = this.productDao.exists(productQueryWrapper);
        // 如果不是当前商家的，则是非法操作。
        if (!exists) {
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
    }


}
