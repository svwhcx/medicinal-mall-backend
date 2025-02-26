package com.medicinal.mall.mall.demos.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 22:39
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T>{

    // 当前的页数
    private Long pageNum;

    // 一共有多少条数据
    private Long total;

    // 数据列表
    private List<T> list;




    /**
     * mybatis的分页的一个适配
     * @param page mybatis的分页查询情况
     * @return
     * @param <T>
     */
    public static <T> PageVo<T> build(IPage<T> page) {
        PageVo<T>pageVo = new PageVo<>();
        pageVo.setPageNum(page.getCurrent());
        pageVo.setTotal(page.getTotal());
        pageVo.setList(page.getRecords());
        return pageVo;
    }
}
