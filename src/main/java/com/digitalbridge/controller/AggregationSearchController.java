package com.digitalbridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbridge.exception.DigitalBridgeException;
import com.digitalbridge.request.SearchResponse;
import com.digitalbridge.service.AggregationSearchService;
import com.digitalbridge.util.Constants;

/**
 * <p>
 * AggregationSearch class.
 * </p>
 *
 * @author rajakolli
 * @version 1:0
 */
@RestController
@RequestMapping(value = Constants.BASE_MAIN_URL)
public class AggregationSearchController
{

    @Autowired
    AggregationSearchService aggregationSearchService;

    /**
     * <p>
     * performBasicAggregationSearch.
     * </p>
     *
     * @param refresh
     *            a boolean.
     * @param sortField
     *            a {@link java.lang.String} object.
     * @param sortOrder
     *            a {@link java.lang.String} object.
     * @param searchKeyword
     *            a {@link java.lang.String} object.
     * @param fieldNames
     *            a {@link java.lang.String} object.
     * @return a {@link com.digitalbridge.request.SearchResponse} object.
     * @throws com.digitalbridge.exception.DigitalBridgeException
     *             if any.
     */
    @Secured({ "ROLE_USER" })
    @RequestMapping(value = "/performBasicAggregationSearch", method = { RequestMethod.POST,
            RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SearchResponse performBasicAggregationSearch(
            @RequestParam(required = false, defaultValue = "false", name = "refresh") boolean refresh,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, defaultValue = "ASC", name = "sortOrder") String sortOrder,
            @RequestParam(required = true, name = "searchKeyword") String searchKeyword,
            @RequestParam(required = true, name = "fieldNames") String... fieldNames)
                    throws DigitalBridgeException
    {
        return aggregationSearchService.performBasicAggregationSearch(searchKeyword,
                fieldNames, refresh, sortField, sortOrder);
    }

}
