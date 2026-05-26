package com.example.businessservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class UserServiceClientConfig {

    @Bean
    @LoadBalanced
    public RestClient.Builder userServiceRestBuilder(){
        return RestClient.builder();
    }

    @Bean
    public UserServiceClient userServiceClientInterface(
            @Qualifier("userServiceRestBuilder") RestClient.Builder userServiceBuilder){
        RestClient restClient = userServiceBuilder
                .baseUrl("http://userservice")
                .build();

        RestClientAdapter clientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory serviceProxyFactory = HttpServiceProxyFactory
                .builderFor(clientAdapter).build();

        return serviceProxyFactory.createClient(UserServiceClient.class);
    }
}
