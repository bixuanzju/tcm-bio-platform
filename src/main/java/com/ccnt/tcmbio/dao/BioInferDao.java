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

    public ArrayList<String> getDiseaseName(String tcm, Integer start, Integer offset);

    public ArrayList<String> getDiseaseID(String diseaseName, Integer start, Integer offset);

    public ArrayList<String> getDrugID(String diseaseID, Integer start, Integer offset);

    public ArrayList<String> getTargetID(String drugID, Integer start, Integer offset);

    public ArrayList<String> getProtein(String targetID, Integer start, Integer offset);

    public ArrayList<String> getGeneID(String protein, Integer start, Integer offset);

    public ArrayList<String> getGeneProduct(String geneID, Integer start, Integer offset);

}
