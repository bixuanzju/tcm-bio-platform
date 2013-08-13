/**
 * TcmInferDaoImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao.impl;

import static com.ccnt.tcmbio.data.GraphNames.Diseasome;
import static com.ccnt.tcmbio.data.GraphNames.DrugBank;
import static com.ccnt.tcmbio.data.GraphNames.TCMGeneDIT;
import static com.ccnt.tcmbio.data.GraphNames.Tcm_Diseasesome_Mapping;
import static com.ccnt.tcmbio.data.Namespaces.TCMGeneDITID;
import static com.ccnt.tcmbio.data.PredictNames.Diseasesome_PossibleDrug;
import static com.ccnt.tcmbio.data.PredictNames.Drugbank_SwissprotId;
import static com.ccnt.tcmbio.data.PredictNames.OWL_SameAs;
import static com.ccnt.tcmbio.data.PredictNames.TCMGeneDIT_Treatment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.ccnt.tcmbio.dao.TcmInferDao;
import com.ccnt.tcmbio.data.TcmInferData;

public class TcmInferDaoImpl extends JdbcDaoSupport implements TcmInferDao {

	private static final Logger LOGGER = LogManager
			.getLogger(TcmInferDaoImpl.class.getName());

	@Override
	public ArrayList<TcmInferData> getTcmInference(final String tcm,
			final Integer start, final Integer offset) {

		String sparql0 = "sparql select * where {graph<" + TCMGeneDIT + "> " + "{<"
				+ TCMGeneDITID + "medicine/" + tcm.replace(" ", "_") + "> ?p ?o }}";

		LOGGER.debug("get tcm name1 - query virtuoso: {}", sparql0);

		List<Map<String, Object>> rows0 = getJdbcTemplate().queryForList(sparql0);

		if (rows0.size() == 0) {

			sparql0 = "sparql select distinct ?tcmName where {graph<" + TCMGeneDIT
					+ "> " + "{?tcmName ?p ?o " + ". filter (?tcmName LIKE \"@"
					+ TCMGeneDITID + "medicine/" + tcm.replace(" ", "_")
					+ "\")}} ";

			LOGGER.debug("get tcm name2 - query virtuoso: {}", sparql0);
			rows0 = getJdbcTemplate().queryForList(sparql0);
//
//			if (rows0.size() == 0) {
//				sparql0 = "sparql select distinct ?tcmName where {graph<" + TCMGeneDIT
//						+ "> " + "{?tcmName ?p ?o " + "filter regex(?tcmName, \""
//						+ TCMGeneDITID + "medicine/.*(";
//
//				if (tcm.contains(" ")) {
//					final String[] kws = tcm.split(" ");
//					for (final String kw : kws) {
//						sparql0 += kw;
//						if (kw != kws[kws.length - 1]) {
//							sparql0 += "|";
//						}
//					}
//				}
//				else {
//					sparql0 += tcm;
//				}
//				sparql0 += ").*\", \"i\")}}";
//
//				LOGGER.debug("get tcm name3 - query virtuoso: {}", sparql0);
//
//				rows0 = getJdbcTemplate().queryForList(sparql0);
//
//			}
		}
		else {
			rows0.clear();
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("tcmName", TCMGeneDITID + "medicine/" + tcm.replace(" ", "_"));
			rows0.add(map);
		}

		final ArrayList<TcmInferData> tcmInferDatas = new ArrayList<TcmInferData>();

		for (final Map<String, Object> row0 : rows0) {

			final String tcmName = row0.get("tcmName").toString();

			final String sparql = "sparql select * where {"
					+ "graph<http://localhost:8890/TCMGeneDIT> {<"
					+ tcmName
					+ "> TCMGeneDIT:treatment ?diseaseName} . "
					+ "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
					+ "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
					+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID} . "
					+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label ?drugName} . "
					+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:swissprotId ?proteinAcce} . "
					+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:name ?targetName} . "
					+ "graph<http://uniprot/protein_gene_mapping> {?proteinAcce uniprotGO:classifiedWith ?GOID} . "
					+ "graph<http://localhost:8890/uniprot_protein_entrez_mapping> {?proteinAcce uniprotGO:classifiedWith ?geneID} . "
					+ "graph<http://localhost:8890/symbol_geneid_mapping> {?geneID <http://www.ccnt.org/symbol> ?geneName} . "
					+ "graph<http://localhost:8890/gene_ontology> {?GOID rdfs:label ?genePro}} "
					+ "limit(" + offset + ") offset(" + start + ")";

			LOGGER.debug("get tcm inference result - query virtuoso: {}", sparql);

			try {

				final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(
						sparql);

				for (final Map<String, Object> map : rows) {
					final TcmInferData tcmInferData = new TcmInferData();
					tcmInferData.setTcmName(tcmName);
					tcmInferData.setDiseaseName(map.get("diseaseName").toString());
					tcmInferData.setDiseaseID(map.get("diseaseID").toString());
					tcmInferData.setDrugID(map.get("drugID").toString());
					tcmInferData.setDrugName(map.get("drugName").toString());
					tcmInferData.setTargetID(map.get("targetID").toString());
					tcmInferData.setTargetName(map.get("targetName").toString());
					tcmInferData.setProteinAcce(map.get("proteinAcce").toString());
					tcmInferData.setGeneName(map.get("geneName").toString());
					tcmInferData.setGeneGOID(map.get("GOID").toString());
					tcmInferData.setGeneProduct(map.get("genePro").toString());
					tcmInferDatas.add(tcmInferData);
				}

			}
			catch (final DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tcmInferDatas;
		// return null;
	}

	@Override
	public Integer getTcmInferCount(final String tcm) {

		final String sparql = "sparql select count(*) as ?count where {"
				+ "graph<http://localhost:8890/TCMGeneDIT> {<"
				+ tcm
				+ "> TCMGeneDIT:treatment ?diseaseName} . "
				+ "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
				+ "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
				+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID} . "
				+ "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:swissprotId ?proteinAcce} . "
				+ "graph<http://uniprot/protein_gene_mapping> {?proteinAcce uniprotGO:classifiedWith ?GOID} . "
				+ "graph<http://localhost:8890/gene_ontology> {?GOID rdfs:label ?genePro}}";

		LOGGER.debug("get tcm inference count - query virtuoso: {}", sparql);

		try {
			return getJdbcTemplate().queryForInt(sparql);
		}
		catch (final DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<String> getDiseaseName(final String tcm,
			final Integer start, final Integer offset) {

		final String sparql = "sparql select * from <" + TCMGeneDIT + "> where {"
				+ tcm + " " + TCMGeneDIT_Treatment + " ?diseaseName}";

		LOGGER.debug("getDiseaseName - query virtuoso: {}", sparql);

		try {
			final ArrayList<String> diseaseNames = new ArrayList<String>();
			final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(
					sparql);

			for (final Map<String, Object> row : rows) {
				diseaseNames.add(row.get("diseaseName").toString());
			}

			return diseaseNames;
		}
		catch (final DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<String> getDiseaseID(final String diseaseName,
			final Integer start, final Integer offset) {

		final String sparql = "sparql select * from <" + Tcm_Diseasesome_Mapping
				+ "> where {" + diseaseName + " " + OWL_SameAs + " ?diseaseID}";

		LOGGER.debug("getDiseaseID - query virtuoso: {}", sparql);

		try {
			final ArrayList<String> diseaseIDs = new ArrayList<String>();
			final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(
					sparql);

			for (final Map<String, Object> row : rows) {
				diseaseIDs.add(row.get("diseaseID").toString());
			}

			return diseaseIDs;
		}
		catch (final DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<String> getDrugName(final String diseaseID,
			final Integer start, final Integer offset) {

		final String sparql = "sparql select * where {"
				+ "graph<"
				+ Diseasome
				+ "> {"
				+ diseaseID
				+ " "
				+ Diseasesome_PossibleDrug
				+ " ?drugID} . "
				+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label ?drugName}}";

		LOGGER.debug("getDrugID - query virtuoso: {}", sparql);

		try {
			final ArrayList<String> drugIDs = new ArrayList<String>();
			final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(
					sparql);

			for (final Map<String, Object> row : rows) {
				drugIDs.add(row.get("drugName").toString());
			}

			return drugIDs;
		}
		catch (final DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<String> getTargetID(final String drugName,
			final Integer start, final Integer offset) {

		final String sparql = "sparql select * where {"
				+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID rdfs:label \""
				+ drugName
				+ "\"} . "
				+ "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID}}";

		LOGGER.debug("getTargetID - query virtuoso: {}", sparql);

		try {
			final ArrayList<String> targetIDs = new ArrayList<String>();
			final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(
					sparql);

			for (final Map<String, Object> row : rows) {
				targetIDs.add(row.get("targetID").toString());
			}

			return targetIDs;
		}
		catch (final DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<String> getProtein(final String targetID,
			final Integer start, final Integer offset) {

		final String sparql = "sparql select * from <" + DrugBank + "> where {"
				+ targetID + " " + Drugbank_SwissprotId + " ?proteinAcce}";

		LOGGER.debug("getProtein - query virtuoso: {}", sparql);

		try {
			final ArrayList<String> proteinAcces = new ArrayList<String>();
			final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(
					sparql);

			for (final Map<String, Object> row : rows) {
				proteinAcces.add(row.get("proteinAcce").toString());
			}

			return proteinAcces;
		}
		catch (final DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<String> getGeneID(final String protein, final Integer start,
			final Integer offset) {

		final String sparql = "sparql select * where {"
				+ "graph<http://localhost:8890/uniprot_protein_entrez_mapping> {"
				+ protein
				+ " uniprotGO:classifiedWith ?geneID} . "
				+ "graph<http://localhost:8890/symbol_geneid_mapping> {?geneID <http://www.ccnt.org/symbol> ?geneName}}";

		LOGGER.debug("getGeneID - query virtuoso: {}", sparql);

		try {
			final ArrayList<String> geneIDs = new ArrayList<String>();
			final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(
					sparql);

			for (final Map<String, Object> row : rows) {
				geneIDs.add(row.get("geneName").toString());
			}

			return geneIDs;
		}
		catch (final DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<String> getGeneProduct(final String geneName,
			final Integer start, final Integer offset) {

		final String sparql = "sparql select * where {"
				+ "graph<http://localhost:8890/symbol_geneid_mapping> {?geneID <http://www.ccnt.org/symbol> "
				+ geneName
				+ "} . "
				+ "graph<http://localhost:8890/uniprot_protein_entrez_mapping> {?proteinAcce uniprotGO:classifiedWith ?geneID} . "
				+ "graph<http://uniprot/protein_gene_mapping> {?proteinAcce uniprotGO:classifiedWith ?GOID} . "
				+ "graph<http://localhost:8890/gene_ontology> {?GOID rdfs:label ?geneProduct}} ";

		LOGGER.debug("getGeneProduct - query virtuoso: {}", sparql);

		try {
			final ArrayList<String> geneProducts = new ArrayList<String>();
			final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(
					sparql);

			for (final Map<String, Object> row : rows) {
				geneProducts.add(row.get("geneProduct").toString());
			}

			return geneProducts;
		}
		catch (final DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
