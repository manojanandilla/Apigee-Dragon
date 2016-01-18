package com.tmobile.apigee.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tmobile.apigee.domain.WSDL;
import com.tmobile.apigee.service.DMValidatorService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/dmvalidator")
@Api(value = "datamapping", description = "Data Mapping Validator API")
public class DataMappingValidatorController extends AbstractRestHandler {
    
    @Autowired
    private DMValidatorService dmValidatorService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a hotel resource.", notes = "Returns the URL of the new resource in the Location header.")
    public
    @ResponseBody
    ResponseEntity createHotel(@RequestBody WSDL wsdl,
                                 HttpServletRequest request, HttpServletResponse response) {
    	return this.success(this.dmValidatorService.validateDM(wsdl));
    }

    
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get List of WSDLS", notes = "Select any one operations from the selected WSDL")
    public
    @ResponseBody
    ResponseEntity helloWorld(HttpServletRequest request, HttpServletResponse response) {
		
        return this.success(dmValidatorService.getWSDLs());
    }
    
    
    
    
    @RequestMapping(value = "/operations",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get List of WSDLS", notes = "Select any one operations from the selected WSDL")
   
    public
    @ResponseBody
    ResponseEntity getOperations(HttpServletRequest request, HttpServletResponse response, @RequestParam("name") String wsdlName) {
		
        return this.success(dmValidatorService.getOperations(wsdlName));
    }
    
    
    
    
    
    public ResponseEntity success(final Object obj) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return new ResponseEntity(obj, responseHeaders, HttpStatus.OK);
    }
    


    
}
