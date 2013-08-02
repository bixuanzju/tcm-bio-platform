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
    
    @RequestMapping(value="/v0.9/bioinfer/searchDis/kw={disName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getDisInference(@PathVariable final String disName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/searchGOID/kw={}&s={}&o={}", disName, start, offset);
        try {
            final BioInferSearchData bioSearchDatas = bioInferService.getDisInference(disName, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(bioSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/drugName2drugID/kw={drugName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getDrugName2DrugID(@PathVariable final String drugName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", drugName, start, offset);

        try {
            return bioInferService.getDrugName2DrugID(drugName, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/drugID2diseaseID/kw={drugID}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getDrugID2DisID(@PathVariable final String drugID, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", drugID, start, offset);

        try {
            return bioInferService.getDrugID2DisID(drugID, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/disid2disname/kw={disid}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getDisID2DisName(@PathVariable final String disid, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", disid, start, offset);

        try {
            return bioInferService.getDisID2DisName(disid, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/disname2tcm/kw={disname}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getDisName2TCMName(@PathVariable final String disname, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", disname, start, offset);

        try {
            return bioInferService.getDisName2TCMName(disname, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/geneName2PA/kw={geneName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getGeneName2PA(@PathVariable final String geneName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", geneName, start, offset);

        try {
            return bioInferService.getGeneName2PA(geneName, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/PA2targetName/kw={PAname}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getPA2TargetName(@PathVariable final String PAname, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", PAname, start, offset);

        try {
            return bioInferService.getPA2TargetName(PAname, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/targetName2drugName/kw={targetName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getTargetName2DrugName(@PathVariable final String targetName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", targetName, start, offset);

        try {
            return bioInferService.getTargetName2DrugName(targetName, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/v0.9/bioinfer/drugName2disName/kw={drugName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getDrugName2DisName(@PathVariable final String drugName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", drugName, start, offset);

        try {
            return bioInferService.getDrugName2DisName(drugName, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/v0.9/bioinfer/GO2PA/kw={GOName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody Graphml getGO2PA(@PathVariable final String GOName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/drugName2drugID/kw={}&s={}&o={}", GOName, start, offset);

        try {
            return bioInferService.getGO2PA(GOName, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}