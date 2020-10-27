package com.zkcm.problemtool.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkcm.problemtool.response.vo.BaseVO;
import com.zkcm.problemtool.response.vo.ListVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description： 封装分页结果
 * @author     ：lwl
 * @date       ：2019/10/11 17:38
 * @version:
 */
@Data
@Accessors(chain = true)
public class PageVO <T extends BaseVO>  {
    //当前页
    private  long pageNum;
    //总页码
    private  long total;
    //
    private List<?> data;

    public PageVO(){

    }

    public PageVO(IPage<?> page){
        this.pageNum = page.getCurrent();
        this.total = page.getTotal();
        this.data = page.getRecords();
    }

    /**
     * 将分页对象转化为前台展示结果
     * @param page 分页对象
     * @param VOType 分页对象中的实体类.class
     *result:
     *creater: lwl
     *time: 2019/9/29 16:14
     */
    public PageVO(IPage<?> page,Class<T> VOType){
        this.total = page.getTotal();
        this.pageNum = page.getCurrent();
        this.data = new ListVO(page.getRecords(),VOType).getVoList();
    }

    public static PageVO getInstance(IPage<?> page){
        return new PageVO(page);
    }
}
