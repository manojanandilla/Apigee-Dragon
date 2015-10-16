package com.trigital.cm.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import scala.collection.immutable.Page;

import com.trigital.cm.domain.MACDetails;
import com.trigital.cm.exception.DataFormatException;
import com.trigital.cm.service.MACDetailsService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/cablemodem")
@Api(value = "hotels", description = "Hotel API")
public class MACController extends AbstractRestHandler {

    @Autowired
    private MACDetailsService macDetailsService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a hotel resource.", notes = "Returns the URL of the new resource in the Location header.")
    public
    @ResponseBody
    ResponseEntity createHotel(@RequestBody MACDetails macDetails,
                                 HttpServletRequest request, HttpServletResponse response) {
    	return this.success(this.macDetailsService.getMACDetails(macDetails.getIp_Address(), macDetails.getMac_Address()));
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all hotels.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    ResponseEntity helloWorld(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("hellow world");
		MACDetails macDetails = new MACDetails();
		macDetails.setDIP("DIP");
        return this.success(macDetails);
    }
    
    public ResponseEntity success(final Object obj) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return new ResponseEntity(obj, responseHeaders, HttpStatus.OK);
    }


    
}
