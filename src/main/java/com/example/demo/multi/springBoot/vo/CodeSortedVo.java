package com.example.demo.multi.springBoot.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/19 8:35
 * @modefied:
 */
public class CodeSortedVo {

    private List<Map.Entry<Integer, Integer>> redSortedList;
    private List<Map.Entry<Integer, Integer>> blueSortedList;
    @JsonIgnore
    private Map<Integer, Integer> mapR;
    @JsonIgnore
    private Map<Integer, Integer> mapB;


    public List<Map.Entry<Integer, Integer>> getRedSortedList() {
        return redSortedList;
    }

    public void setRedSortedList(List<Map.Entry<Integer, Integer>> redSortedList) {
        this.redSortedList = redSortedList;
    }

    public List<Map.Entry<Integer, Integer>> getBlueSortedList() {
        return blueSortedList;
    }

    public void setBlueSortedList(List<Map.Entry<Integer, Integer>> blueSortedList) {
        this.blueSortedList = blueSortedList;
    }

    public Map<Integer, Integer> getMapR() {
        return mapR;
    }

    public void setMapR(Map<Integer, Integer> mapR) {
        this.mapR = mapR;
    }

    public Map<Integer, Integer> getMapB() {
        return mapB;
    }

    public void setMapB(Map<Integer, Integer> mapB) {
        this.mapB = mapB;
    }

    @Override
    public String toString() {
        return "CodeSortedVo{" +
                "redSortedList=" + redSortedList +
                ", blueSortedList=" + blueSortedList +
                ", mapR=" + mapR +
                ", mapB=" + mapB +
                '}';
    }
}
