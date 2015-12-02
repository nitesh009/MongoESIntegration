package com.digitalbridge;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.RequestDispatcher;

import org.junit.Test;

public class DigitalBridgeApplicationMVCTests extends DigitalBridgeApplicationTests
{

    @Test
    public void errorExample() throws Exception
    {
        this.mockMvc
                .perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                        .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/restapi")
                        .requestAttr(RequestDispatcher.ERROR_MESSAGE,
                                "The tag 'http://localhost:8080/restapi/123' does not exist"))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue())))
                .andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue())))
                .andDo(document("error-example",
                        responseFields(
                                fieldWithPath("error").description(
                                        "The HTTP error that occurred, e.g. 'Bad Request'"),
                        fieldWithPath("message")
                                .description("A description of the cause of the error"),
                        fieldWithPath("path")
                                .description("The path to which the request was made"),
                        fieldWithPath("status")
                                .description("The HTTP status code, e.g. '400'"),
                        fieldWithPath("timestamp").description(
                                "The time, in milliseconds, at which the error occurred"))));
    }
    
    @Test
    public void indexExample() throws Exception {
        this.mockMvc.perform(get("/restapi")
            .header("Authorization", "Basic YXBwVXNlcjphcHBQYXNzd29yZA=="))
            .andExpect(status().isOk())
            .andDo(document("index-example",
                    links(
                            linkWithRel("notes").description("The <<resources-notes,Notes resource>>"),
                            linkWithRel("appUsers").description("The <<resources-appUsers, Users resource>>"),
                            linkWithRel("assets").description("The <<resources-assets, Assets resource>>"),
                            linkWithRel("address").description("The <<resources-address, Address resource>>"),
                            linkWithRel("profile").description("The ALPS profile for the service")),
                    responseFields(
                            fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources")),
                    requestHeaders(
                            headerWithName("Authorization").description("Basic auth credentials"))));

    }
}
