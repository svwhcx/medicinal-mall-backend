package com.medicinal.mall.mall.demos.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description 购物车的实体类
 * @Author cxk
 * @Date 2025/2/24 10:08
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("shopping_car")
public class ShoppingCar {


    @TableId
    // id
    private Integer id;

    // 用户的id
    private Integer userId;

    // 要购买的药材的id
    private Integer medicinalId;

    // 预计购买的数量
    private Integer preBuyNum;

}
