/**
 * BioInferController.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ccnt.tcmbio.data.BioInferSearchData;
import com.ccnt.tcmbio.data.graph.Graphml;
import com.ccnt.tcmbio.service.BioInferService;
//import com.ccnt.tcmbio.data.graph.Graphml;

@Controller
@RequestMapping("/")
public class BioInferController {

    private static final Logger LOGGER = LogManager.getLogger(BioInferController.class.getName());

    @Autowired
    private BioInferService bioInferService;

    public void setBioInferService(final BioInferService bioInferService) {
        this.bioInferService = bioInferService;
    }

    @RequestMapping(value="/v0.9/bioinfer/searchdrug/kw={bioName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getDrugInference(@PathVariable final String bioName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/searchdrug/kw={}&s={}&o={}", bioName, start, offset);
        try {
            final BioInferSearchData bioSearchDatas = bioInferService.getDrugInference(bioName, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(bioSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/searchGeneName/kw={geneName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getGeneNameInference(@PathVariable final String geneName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/searchGeneName/kw={}&s={}&o={}", geneName, start, offset);
        try {
            final BioInferSearchData bioSearchDatas = bioInferService.getGeneNameInference(geneName, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(bioSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/searchPA/kw={PAName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getPAInference(@PathVariable final String PAName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/searchPA/kw={}&s={}&o={}", PAName, start, offset);
        try {
            final BioInferSearchData bioSearchDatas = bioInferService.getPAInference(PAName, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(bioSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/searchGOID/kw={GOID}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getGOIDInference(@PathVariable final String GOID, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/searchGOID/kw={}&s={}&o={}", GOID, start, offset);
        try {
            final BioInferSearchData bioSearchDatas = bioInferService.getGOIDInference(GOID, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(bioSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/drugName2drugID/kw={drugName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getDrugID(@PathVariable final String drugName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", drugName, start, offset);

        try {
            return bioInferService.getDrugID(drugName, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/drugID2diseaseID/kw={drugID}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getDiseaseID(@PathVariable final String drugID, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", drugID, start, offset);

        try {
            return bioInferService.getDiseaseID(drugID, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/disid2disname/kw={disid}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getDiseaseName(@PathVariable final String disid, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", disid, start, offset);

        try {
            return bioInferService.getDiseaseName(disid, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/disname2tcm/kw={disname}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getTCMName(@PathVariable final String disname, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", disname, start, offset);

        try {
            return bioInferService.getTCMName(disname, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}