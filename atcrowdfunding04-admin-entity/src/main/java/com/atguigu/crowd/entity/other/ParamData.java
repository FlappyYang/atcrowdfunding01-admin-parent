package com.atguigu.crowd.entity.other;

import java.util.List;

public class ParamData {
    private List<Integer> array;

    @Override
    public String toString() {
        return "ParamData{" +
                "array=" + array +
                '}';
    }

    public ParamData() {
    }

    public ParamData(List<Integer> array) {
        this.array = array;
    }

    public List<Integer> getArray() {
        return array;
    }

    public void setArray(List<Integer> array) {
        this.array = array;
    }
}
