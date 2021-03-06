/**
 * TcmInferData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

public class TcmInferData {

    private String tcmName;

    private String diseaseName;

    private String diseaseID;

    private String drugID;
    
    private String drugName;

    private String targetID;
    
    private String targetName;

    private String proteinAcce;
    
    private String geneName;

    private String geneGOID;

    private String geneProduct;

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

    public String getTargetID() {
        return targetID;
    }

    public void setTargetID(final String targetID) {
        this.targetID = targetID;
    }
    
    public String getTargetName() {
      	return targetName;
    }

    public void setTargetName(final String targetName) {
      	this.targetName = targetName;
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

    public String getGeneGOID() {
        return geneGOID;
    }

    public void setGeneGOID(final String geneGOID) {
        this.geneGOID = geneGOID;
    }

    public String getGeneProduct() {
        return geneProduct;
    }

    public void setGeneProduct(final String geneProduct) {
        this.geneProduct = geneProduct;
    }
}
