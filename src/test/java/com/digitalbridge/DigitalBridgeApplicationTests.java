package com.digitalbridge;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.digitalbridge.controller.AggregationSearchController;
import com.digitalbridge.mongodb.repository.AssetWrapperRepository;
import com.digitalbridge.mongodb.repository.NotesRepository;
import com.digitalbridge.mongodb.repository.UserRepository;
import com.digitalbridge.service.AssetWrapperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DigitalBridgeApplication.class ,webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
public abstract class DigitalBridgeApplicationTests {

    protected static String assetID = "568f50dc0eda66a0b96fed84";
    protected static final String USERNAME = "JUNIT_TEST";
    protected static final String PASSWORD = "JUNIT_PASSWORD";
    protected static final String ROLE_USER = "ROLE_USER";
    protected static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(
            "target/generated-snippets");
    
    @Autowired
    protected AggregationSearchController aggregationSearchController;
    
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AssetWrapperService assetWrapperService;

    @Autowired
    protected AssetWrapperRepository assetWrapperRepository;
    @Autowired
    protected NotesRepository notesRepository;
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MongoClient mongoClient;

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    protected Pageable pageable = new PageRequest(0, 10);

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(document("{method-name}/{step}/",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @After
    public void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
    }

    public static String getAssetid() {
        return assetID;
    }

    public static void setAssetid(String assetId) {
        assetID = assetId;
    }

}
