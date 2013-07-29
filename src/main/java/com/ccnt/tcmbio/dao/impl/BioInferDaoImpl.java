/**
 * BioInferDaoImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao.impl;

import static com.ccnt.tcmbio.data.GraphNames.DrugBank;
import static com.ccnt.tcmbio.data.GraphNames.Gene_Ontology;
import static com.ccnt.tcmbio.data.GraphNames.Protein_Gene_Mapping;
import static com.ccnt.tcmbio.data.PredictNames.Drugbank_SwissprotId;
import static com.ccnt.tcmbio.data.PredictNames.Drugbank_Target;
import static com.ccnt.tcmbio.data.PredictNames.Rdfs_Label;
import static com.ccnt.tcmbio.data.PredictNames.UniprotGO_ClassifiedWith;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.ccnt.tcmbio.dao.BioInferDao;
import com.ccnt.tcmbio.data.BioInferData;

public class BioInferDaoImpl extends JdbcDaoSupport implements BioInferDao{

    private static final Logger LOGGER = LogManager.getLogger(BioInferDaoImpl.class.getName());

    @Override
    public ArrayList<BioInferData> getDrugInference(final String bio, final Integer start, final Integer offset){

    	final String sparql = "sparql select * where {"
          + "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label \"" + bio + "\"} . "
          + "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
          + "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
          + "graph<http://localhost:8890/TCMGeneDIT> {?tcmName TCMGeneDIT:treatment ?diseaseName}} "
          + "limit(" + offset + ") offset(" + start + ")";

        LOGGER.debug("get bio inference result - query virtuoso: {}", sparql);

        try {
            final ArrayList<BioInferData> bioInferDatas = new ArrayList<BioInferData>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for (final Map<String, Object> map : rows) {
                final BioInferData bioInferData = new BioInferData();
                bioInferData.setTcmName(map.get("tcmName").toString());
                bioInferData.setDiseaseName(map.get("diseaseName").toString());
                bioInferData.setDiseaseID(map.get("diseaseID").toString());
                bioInferData.setDrugID(map.get("drugID").toString());
                bioInferData.setDrugName(bio);
                bioInferDatas.add(bioInferData);
            }
            return bioInferDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public ArrayList<BioInferData> getGeneNameInference(final String geneName, final Integer start, final Integer offset){

    	final String sparql = "sparql select * where {"
    			+ "graph<http://localhost:8890/symbol_geneid_mapping> {?geneID <http://www.ccnt.org/symbol> <" + geneName + "> } . "
    			+ "graph<http://localhost:8890/uniprot_protein_entrez_mapping> {?proteinAcce uniprotGO:classifiedWith ?geneID} . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:swissprotId ?proteinAcce} . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID} . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label ?drugName} . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:name ?targetName} . "
    			+ "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
    			+ "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
    			+ "graph<http://localhost:8890/TCMGeneDIT> {?tcmName TCMGeneDIT:treatment ?diseaseName}} "
          + "limit(" + offset + ") offset(" + start + ")";

        LOGGER.debug("get bio inference result - query virtuoso: {}", sparql);

        try {
            final ArrayList<BioInferData> bioInferDatas = new ArrayList<BioInferData>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for (final Map<String, Object> map : rows) {
                final BioInferData bioInferData = new BioInferData();
                bioInferData.setTcmName(map.get("tcmName").toString());
                bioInferData.setDiseaseName(map.get("diseaseName").toString());
                bioInferData.setDrugName(map.get("drugName").toString());
                bioInferData.setProteinAcce(map.get("proteinAcce").toString());
                bioInferData.setTargetName(map.get("targetName").toString());
                bioInferData.setGeneName(geneName);
                bioInferDatas.add(bioInferData);
            }
            return bioInferDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public ArrayList<BioInferData> getPAInference(final String PAName, final Integer start, final Integer offset){

    	final String sparql = "sparql select * where {"
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:swissprotId <" + PAName + "> } . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID} . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label ?drugName} . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:name ?targetName} . "
    			+ "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
    			+ "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
    			+ "graph<http://localhost:8890/TCMGeneDIT> {?tcmName TCMGeneDIT:treatment ?diseaseName}} "
          + "limit(" + offset + ") offset(" + start + ")";

        LOGGER.debug("get bio inference result - query virtuoso: {}", sparql);

        try {
            final ArrayList<BioInferData> bioInferDatas = new ArrayList<BioInferData>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for (final Map<String, Object> map : rows) {
                final BioInferData bioInferData = new BioInferData();
                bioInferData.setTcmName(map.get("tcmName").toString());
                bioInferData.setDiseaseName(map.get("diseaseName").toString());
                bioInferData.setDrugName(map.get("drugName").toString());
                bioInferData.setProteinAcce(PAName);
                bioInferData.setTargetName(map.get("targetName").toString());
                bioInferDatas.add(bioInferData);
            }
            return bioInferDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public ArrayList<BioInferData> getGOIDInference(final String GOID, final Integer start, final Integer offset){

    	final String sparql = "sparql select * where {"
    			+ "graph<http://uniprot/protein_gene_mapping> {?proteinAcce uniprotGO:classifiedWith <" + GOID + "> } . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:swissprotId ?proteinAcce} . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID} . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label ?drugName} . "
    			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:name ?targetName} . "
    			+ "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
    			+ "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
    			+ "graph<http://localhost:8890/TCMGeneDIT> {?tcmName TCMGeneDIT:treatment ?diseaseName}} "
          + "limit(" + offset + ") offset(" + start + ")";

        LOGGER.debug("get bio inference result - query virtuoso: {}", sparql);

        try {
            final ArrayList<BioInferData> bioInferDatas = new ArrayList<BioInferData>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for (final Map<String, Object> map : rows) {
                final BioInferData bioInferData = new BioInferData();
                bioInferData.setGOID(GOID);
                bioInferData.setTcmName(map.get("tcmName").toString());
                bioInferData.setDiseaseName(map.get("diseaseName").toString());
                bioInferData.setDrugName(map.get("drugName").toString());
                bioInferData.setProteinAcce(map.get("proteinAcce").toString());
                bioInferData.setTargetName(map.get("targetName").toString());
                bioInferDatas.add(bioInferData);
            }
            return bioInferDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer getDrugInferCount(final String bio){

        final String sparql = "sparql select count(*) as ?count where {"
        		 + "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label \"" + bio + "\"} . "
             + "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
             + "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
             + "graph<http://localhost:8890/TCMGeneDIT> {?tcmName TCMGeneDIT:treatment ?diseaseName}} ";

        LOGGER.debug("get bio inference result - query virtuoso: {}", sparql);

        try {
            return getJdbcTemplate().queryForInt(sparql);
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Integer getGeneNameInferCount(final String geneName){

        final String sparql = "sparql select count(*) as ?count where {"
        		+ "graph<http://localhost:8890/symbol_geneid_mapping> {?geneID <http://www.ccnt.org/symbol> <" + geneName + "> } . "
      			+ "graph<http://localhost:8890/uniprot_protein_entrez_mapping> {?proteinAcce uniprotGO:classifiedWith ?geneID} . "
      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:swissprotId ?proteinAcce} . "
      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID} . "
//      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label ?drugName} . "
//      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:name ?targetName} . "
      			+ "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
      			+ "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
      			+ "graph<http://localhost:8890/TCMGeneDIT> {?tcmName TCMGeneDIT:treatment ?diseaseName}} ";

        LOGGER.debug("get bio inference result - query virtuoso: {}", sparql);

        try {
            return getJdbcTemplate().queryForInt(sparql);
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }
    
    @Override
    public Integer getPAInferCount(final String PAName){

        final String sparql = "sparql select count(*) as ?count where {"
        		+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:swissprotId <" + PAName + "> } . "
      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID} . "
//      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label ?drugName} . "
//      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:name ?targetName} . "
      			+ "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
      			+ "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
      			+ "graph<http://localhost:8890/TCMGeneDIT> {?tcmName TCMGeneDIT:treatment ?diseaseName}} ";

        LOGGER.debug("get bio inference result - query virtuoso: {}", sparql);

        try {
            return getJdbcTemplate().queryForInt(sparql);
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }
    
    @Override
    public Integer getGOIDInferCount(final String GOID){

        final String sparql = "sparql select count(*) as ?count where {"
        		+ "graph<http://uniprot/protein_gene_mapping> {?proteinAcce uniprotGO:classifiedWith <" + GOID + "> } . "
      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:swissprotId ?proteinAcce} . "
      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID} . "
//      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label ?drugName} . "
//      			+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:name ?targetName} . "
      			+ "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
      			+ "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
      			+ "graph<http://localhost:8890/TCMGeneDIT> {?tcmName TCMGeneDIT:treatment ?diseaseName}} ";

        LOGGER.debug("get bio inference result - query virtuoso: {}", sparql);

        try {
            return getJdbcTemplate().queryForInt(sparql);
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }
    
    @Override
    public ArrayList<String> getDiseaseName(final String disid, final Integer start, final Integer offset){

        final String sparql = "sparql select * where {"
        				+ "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs " + disid + " }}";

        LOGGER.debug("getDiseaseName - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> diseaseNames = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                diseaseNames.add(row.get("diseaseName").toString());
            }

            return diseaseNames;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public ArrayList<String> getTCMName(final String disname, final Integer start, final Integer offset){

        final String sparql = "sparql select * where {"
        		 + "graph<http://localhost:8890/TCMGeneDIT> {?tcmName TCMGeneDIT:treatment " + disname + "}} ";

        LOGGER.debug("getDiseaseName - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> diseaseNames = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                diseaseNames.add(row.get("tcmName").toString());
            }

            return diseaseNames;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getDiseaseID(final String drugID, final Integer start, final Integer offset){

        final String sparql = "sparql select * where {"
        		 		+ "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug " + drugID + "}} ";

        LOGGER.debug("getDiseaseID - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> diseaseIDs = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                diseaseIDs.add(row.get("diseaseID").toString());
            }

            return diseaseIDs;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getDrugID(final String drugName, final Integer start, final Integer offset){

        final String sparql = "sparql select * where {"
        				+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label \"" + drugName+ "\"}}";
        
        LOGGER.debug("getDrugID - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> drugIDs = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                drugIDs.add(row.get("drugID").toString());
            }

            return drugIDs;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getTargetID(final String drugID, final Integer start, final Integer offset){

        final String sparql = "sparql select * from <" + DrugBank + "> where {"
                + drugID + " " + Drugbank_Target + " ?targetID}";

        LOGGER.debug("getTargetID - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> targetIDs = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                targetIDs.add(row.get("targetID").toString());
            }

            return targetIDs;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getProtein(final String targetID, final Integer start, final Integer offset){

        final String sparql = "sparql select * from <" + DrugBank + "> where {"
                + targetID + " " + Drugbank_SwissprotId + " ?proteinAcce}";

        LOGGER.debug("getProtein - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> proteinAcces = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                proteinAcces.add(row.get("proteinAcce").toString());
            }

            return proteinAcces;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getGeneID(final String protein, final Integer start, final Integer offset){

        final String sparql = "sparql select * from <" + Protein_Gene_Mapping + "> where {"
                + protein + " <" + UniprotGO_ClassifiedWith + "> ?geneID}";

        LOGGER.debug("getGeneID - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> geneIDs = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                geneIDs.add(row.get("geneID").toString());
            }

            return geneIDs;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getGeneProduct(final String geneID, final Integer start, final Integer offset){

        final String sparql = "sparql select * from <" + Gene_Ontology + "> where {"
                + geneID + " " + Rdfs_Label + " ?geneProduct}";

        LOGGER.debug("getGeneProduct - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> geneProducts = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                geneProducts.add(row.get("geneProduct").toString());
            }

            return geneProducts;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
