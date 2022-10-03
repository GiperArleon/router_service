package com.router.clients.rest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestAccountantClientFactory {
    private static RestAccountantClient instance;

    private RestAccountantClientFactory() {
    }

    public static RestAccountantClient GetRestAccountantClient() {
        if(instance == null) {
            try {
                instance = new RestAccountantClientImpl();
            } catch(Exception e) {
                log.error("Can not init rest client: {}", e.toString());
            }
        }
        return instance;
    }
}
