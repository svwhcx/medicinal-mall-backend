package com.medicinal.mall.mall.demos.pay;

import com.medicinal.mall.mall.demos.entity.Order;
import com.medicinal.mall.mall.demos.query.OrderRequest;
import com.medicinal.mall.mall.demos.vo.OrderVo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @description 支付宝支付
 * @Author cxk
 * @Date 2025/3/5 22:23
 */
@Service("aliPay")
public class AliPay extends AbstractPay{

    /**
     * TODO 这里需要处理一个临界状态，
     * 1、当支付成功的时候，需要将订单状态设置成已支付
     * 2、这个时候，订单管理器的时间到期了。查询了数据库的状态
     * 3、这个时候用户支付成功，修改了订单状态。
     * 3、这个时候，订单支付过期线程恢复运行，覆盖了订单状态。<p><p>
     *
     *
     *
     * 解决办法：使用两阶段提交，或者新开一个字段，标记当前订单已经失效
     *
     *
     * TODO： 但是又会引发一个新的问题：
     * 那就是订单正好在处理的时候，订单支付到期了，设置成了已过期，然后用户支付成功了。
     * 所以我们就应该使用两阶段提交，让支付状态和过期状态处于两个字段，不会互相影响。
     * @param order 订单信息
     */
    @Override
    public void pay(OrderVo orderVo) {
        // TODO 支付一个订单，需要
        // 1. 获取订单的信息
        // 2. 调用支付宝支付接口
        // 3. 判断支付状态，支付成功修改Order状态。
        // TODO 需要添加订单支付记录
        // TODO 这里也需要处理一个
        paySuccess(orderVo);
    }
    @Override
    public void judgePay(String payCode) {

    }
}
