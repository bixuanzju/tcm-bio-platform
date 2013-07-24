/**
 * BioInferData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

public class BioInferData {

    private String tcmName;

    private String diseaseName;

    private String diseaseID;

    private String drugID;

    private String drugName;
    
    private String proteinAcce;
    
    private String geneName;
    
    private String targetName;

    public String getTcmName() {
        return tcmName;
    }

    public void setTcmName(final String tcmName) {
        this.tcmName = tcmName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(final String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseID() {
        return diseaseID;
    }

    public void setDiseaseID(final String diseaseID) {
        this.diseaseID = diseaseID;
    }

    public String getDrugID() {
        return drugID;
    }

    public void setDrugID(final String drugID) {
        this.drugID = drugID;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(final String drugName) {
        this.drugName = drugName;
    }
    

    public String getProteinAcce() {
        return proteinAcce;
    }
    
    public void setProteinAcce(final String proteinAcce) {
      this.proteinAcce = proteinAcce;
    }
    

    public String getGeneName() {
        return geneName;
    }
    
    public void setGeneName(final String geneName) {
      this.geneName = geneName;
    }
    
    public String getTargetName() {
      return targetName;
    }
  
    public void setTargetName(final String targetName) {
    	this.targetName = targetName;
    }
}