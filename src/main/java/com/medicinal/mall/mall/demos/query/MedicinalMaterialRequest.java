package com.medicinal.mall.mall.demos.query;

import com.medicinal.mall.mall.demos.entity.MedicinalMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description 主要是给上传的前端一些额外的数据
 * @Author cxk
 * @Date 2025/2/27 17:10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicinalMaterialRequest extends MedicinalMaterial {

    private List<Integer> photoIds;
}
