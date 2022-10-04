package com.router.clients.soap;

import lombok.extern.slf4j.Slf4j;
import ru.soap.teamservice.DaoGroup;
import ru.soap.teamservice.DaoGroupImplService;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class SoapUserGroupsClientFactory {
    private static DaoGroup instance;

    private SoapUserGroupsClientFactory() {
    }

    public static DaoGroup getSoapUserGroupsClient() {
        if(instance == null) {
            try {
                URL url = new URL("http://localhost:9898/Groups?wsdl");
                DaoGroupImplService daoGroupImplService = new DaoGroupImplService(url);
                instance = daoGroupImplService.getDaoGroupImplPort();
            } catch(MalformedURLException e) {
                log.error("Can not init group soap interface: {}", e.toString());
            }
        }
        return instance;
    }
}
