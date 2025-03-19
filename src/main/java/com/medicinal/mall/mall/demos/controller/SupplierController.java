package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.entity.Supplier;
import com.medicinal.mall.mall.demos.query.SupplierPageQuery;
import com.medicinal.mall.mall.demos.service.SupplierService;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description 供应商管理
 * @Author cxk
 * @Date 2025/3/18 22:25
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController extends BaseController {

    @Autowired
    private SupplierService supplierService;

    /**
     *
     * @param supplierPageQuery 分页参数
     * @return 分页的供应商信息
     */
    @GetMapping("/list")
    public ResultVO<PageVo<Supplier>> list(SupplierPageQuery supplierPageQuery) {
        return success(supplierService.queryPage(supplierPageQuery));
    }

    /**
     * 获取所有的供应商列表（用于前端下拉框选择）
     *
     * @return 所有的供应商列表
     */
    @GetMapping("/all")
    public ResultVO<List<Supplier>> queryAll() {
        return success(supplierService.queryAll());
    }

    /**
     * 获取一个详细信息
     *
     * @param id 供应商id
     */
    @GetMapping("/{id}")
    public ResultVO<Supplier> getById(@PathVariable("id") Integer id) {
        return success(supplierService.getById(id));
    }

    /**
     * 添加
     *
     * @param supplier 供应商信息
     */
    @PostMapping
    public ResultVO<Void> add(Supplier supplier) {
        supplierService.add(supplier);
        return success();
    }


    /**
     * 更新一个供应商的信息
     *
     * @param supplier 供应商的信息
     */
    @PutMapping
    public ResultVO<Void> update(@RequestBody Supplier supplier) {
        supplierService.update(supplier);
        return success();
    }


    /**
     * 删除一个供应商
     * @param id 供应商id
     */
    @DeleteMapping("/{id}")
    public ResultVO<Void> delete(@PathVariable("id") Integer id) {
        supplierService.delete(id);
        return success();
    }
}
