package com.digitalbridge.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.digitalbridge.util.Constants;
import com.google.gson.GsonBuilder;

import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;

/**
 * <p>
 * ElasticSearchConfiguration class.
 * </p>
 *
 * @author rajakolli
 * @version 1:0
 */
@Configuration
public class ElasticSearchConfiguration {

    private static final String DATESTYLE = "yyyy-MM-DD'T'hh:mm:ss";

    @Autowired
    Environment env;

    /**
     * <p>
     * jestClient.
     * </p>
     *
     * @return a {@link io.searchbox.client.http.JestHttpClient} object.
     */
    @Bean
    public JestHttpClient jestClient() {
        // Construct a new Jest client according to configuration via factory
        JestClientFactory factory = new JestClientFactory();
        HttpClientConfig httpClientConfig = new HttpClientConfig.Builder(
                getConnectionURL()).multiThreaded(true).connTimeout(Constants.CONNTIMEOUT)
                        .readTimeout(Constants.READTIMEOUT)
                        .maxTotalConnection(Constants.MAXTOTALCONNECTION)
                        // .requestCompressionEnabled(true)
                        .gson(new GsonBuilder().setDateFormat(DATESTYLE).create())
                        .build();
        factory.setHttpClientConfig(httpClientConfig);
        JestHttpClient jestHttpClient = (JestHttpClient) factory.getObject();
        CloseableHttpClient closableHttpClient = HttpClientBuilder.create().build();
        jestHttpClient.setHttpClient(closableHttpClient);
        return jestHttpClient;
    }

    /**
     * <p>
     * localConnectionURL.
     * </p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<String> localConnectionURL() {
        return Arrays.asList("http://127.0.0.1:9200");
    }

    /**
     * <p>
     * iLabConnectionURL.
     * </p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<String> iLabConnectionURL() {
        return Arrays.asList("http://152.190.139.77:9200");
    }

    /**
     * <p>
     * getConnectionURL.
     * </p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<String> getConnectionURL() {
        if (findProfile("iLab")) {
            return iLabConnectionURL();
        } else {
            return localConnectionURL();
        }
    }

    private boolean findProfile(String profileName) {
        return Arrays.asList(env.getActiveProfiles()).contains(profileName);
    }

    /**
     * <p>LocalElasticSearchClient.</p>
     *
     * @return a {@link org.elasticsearch.client.Client} object.
     * @throws java.net.UnknownHostException if any.
     */
    @Profile("local")
    @Bean(name = "elasticSearchClient")
    public Client localElasticSearchClient() throws UnknownHostException {
        return TransportClient.builder().build().addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    /**
     * <p>iLabElasticSearchClient.</p>
     *
     * @return a {@link org.elasticsearch.client.Client} object.
     * @throws java.net.UnknownHostException if any.
     */
    @Profile("iLab")
    @Bean(name = "elasticSearchClient")
    public Client iLabElasticSearchClient() throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", Constants.APPLICATIONNAME)
                    .put("client.transport.sniff", true)
                .build();
        return TransportClient.builder().settings(settings).build().addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName("152.190.139.77"), 9300));
    }

}
