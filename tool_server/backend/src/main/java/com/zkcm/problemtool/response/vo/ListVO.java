package com.zkcm.problemtool.response.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListVO<T extends BaseVO> {


    private List<T> voList = new ArrayList<T>();

    public ListVO(List poList, Class<T> VOType){
        if(poList==null || VOType==null){
            return;
        }

        for(Object obj : poList){
            try {
                BaseVO vo = VOType.newInstance();
                vo.convertPOToVO(obj);
                voList.add((T) vo);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}