package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Abid Hussain
 * @created on 29/10/2020 2:07 PM
 */
@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer connectionTimeout;

    private final Integer socketTimeout;

    private final Integer maxTotal;

    private final Integer maxPerRoute;

    public BlockingRestTemplateCustomizer(@Value("${bio.connection.timeout}") Integer connectionTimeout,
                                          @Value("${bio.socket.timeout}") Integer socketTimeout,
                                          @Value("${bio.max.total}") Integer maxTotal,
                                          @Value("${bio.max.perroute}") Integer maxPerRoute) {
        this.connectionTimeout = connectionTimeout;
        this.socketTimeout = socketTimeout;
        this.maxTotal = maxTotal;
        this.maxPerRoute = maxPerRoute;
    }


    public ClientHttpRequestFactory clientHttpRequestFactory(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(this.maxTotal);
        connectionManager.setDefaultMaxPerRoute(this.maxPerRoute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(this.connectionTimeout)
                .setSocketTimeout(this.socketTimeout)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
