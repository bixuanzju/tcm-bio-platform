/**
 * MappingData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

public class MappingData {

    private String ontoName;
    private Integer totalNum;
    private Integer tripleNum;

    public String getOntoName() {
        return ontoName;
    }
    public void setOntoName(final String ontoName) {
        this.ontoName = ontoName;
    }
    public Integer getTotalNum() {
        return totalNum;
    }
    public void setTotalNum(final Integer totalNum) {
        this.totalNum = totalNum;
    }
    
    public Integer getTripleNum() {
      	return tripleNum;
    }
    public void setTripleNum(final Integer tripleNum) {
    		this.tripleNum = tripleNum;
    }

}
