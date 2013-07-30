/**
 * BioInferService.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service;

import java.util.ArrayList;
import com.ccnt.tcmbio.data.BioInferSearchData;
import com.ccnt.tcmbio.data.TcmInferData;
import com.ccnt.tcmbio.data.graph.Graphml;

public interface BioInferService {

    public BioInferSearchData getDrugInference(String tcmName, Integer start, Integer offset);
    
    public BioInferSearchData getGeneNameInference(String geneName, Integer start, Integer offset);
    
    public BioInferSearchData getPAInference(String PAName, Integer start, Integer offset);
    
    public BioInferSearchData getGOIDInference(String GOID, Integer start, Integer offset);

    public ArrayList<TcmInferData> getAndCacheTcmInference(String tcmName);

    public boolean searchTcm(String tcmName);

    public ArrayList<String> fuzzyMatchTcm(String tcmName);

    public Graphml getDisID2DisName(String disid, Integer start, Integer offset);
    
    public Graphml getDisName2TCMName(String disname, Integer start, Integer offset);

    public Graphml getDrugID2DisID(String drugID, Integer start, Integer offset);

    public Graphml getDrugName2DrugID(String drugName, Integer start, Integer offset);

    public Graphml getPA2TargetName(String PAname, Integer start, Integer offset);

    public Graphml getGeneName2PA(String geneName, Integer start, Integer offset);
    
    public Graphml getTargetName2DrugName(String targetName, Integer start, Integer offset);
    
    public Graphml getDrugName2DisName(String drugName, Integer start, Integer offset);
    
    public Graphml getGO2PA(String GOName, Integer start, Integer offset);

    public Graphml getGeneID(String protein, Integer start, Integer offset);

    public Graphml getGeneProduct(String geneID, Integer start, Integer offset);

}
