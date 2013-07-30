/**
 * BioInferDao.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao;

import java.util.ArrayList;
import com.ccnt.tcmbio.data.BioInferData;

public interface BioInferDao {

    public ArrayList<BioInferData> getDrugInference(String bio, Integer start, Integer offset);

    public Integer getDrugInferCount(String bio);
    
    public ArrayList<BioInferData> getGeneNameInference(String drugName, Integer start, Integer offset);

    public Integer getGeneNameInferCount(String drugName);
    
    public ArrayList<BioInferData> getPAInference(String PAName, Integer start, Integer offset);

    public Integer getPAInferCount(String PAName);
    
    public ArrayList<BioInferData> getGOIDInference(String GOID, Integer start, Integer offset);

    public Integer getGOIDInferCount(String GOID);

    public ArrayList<String> getDisID2DisName(String disid, Integer start, Integer offset);
    
    public ArrayList<String> getDisName2TCMName(String disname, Integer start, Integer offset);

    public ArrayList<String> getDrugID2DisID(String drugID, Integer start, Integer offset);

    public ArrayList<String> getDrugName2DrugID(String drugName, Integer start, Integer offset);

    public ArrayList<String> getPA2TargetName(String PAname, Integer start, Integer offset);
    
    public ArrayList<String> getTargetName2DrugName(String target, Integer start, Integer offset);
    
    public ArrayList<String> getDrugName2DisName(String drugName, Integer start, Integer offset);

    public ArrayList<String> getGeneName2PA(String geneName, Integer start, Integer offset);
    
    public ArrayList<String> getGO2PA(String GOName, Integer start, Integer offset);

    public ArrayList<String> getGeneID(String protein, Integer start, Integer offset);

    public ArrayList<String> getGeneProduct(String geneID, Integer start, Integer offset);

}
