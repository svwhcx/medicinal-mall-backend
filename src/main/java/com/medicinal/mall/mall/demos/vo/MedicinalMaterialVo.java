package com.medicinal.mall.mall.demos.vo;

import com.medicinal.mall.mall.demos.entity.MedicinalMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description 查看商品的详细信息的时候展示的内容
 * @Author cxk
 * @Date 2025/2/27 17:54
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicinalMaterialVo extends MedicinalMaterial {

    // 关于本商品的一些图片的访问地址。
    private List<String> photoUrl;

}
