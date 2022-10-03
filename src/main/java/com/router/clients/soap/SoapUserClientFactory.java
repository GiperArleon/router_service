package com.router.clients.soap;

import lombok.extern.slf4j.Slf4j;
import ru.soap.teamservice.DaoUser;
import ru.soap.teamservice.DaoUserImplService;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class SoapUserClientFactory {
    private static DaoUser instance;

    private SoapUserClientFactory() {
    }

    public static DaoUser GetSoapUserClient() {
        if(instance == null) {
            try {
                URL url = new URL("http://localhost:9898/team1?wsdl");
                DaoUserImplService daoUserImplService = new DaoUserImplService(url);
                instance = daoUserImplService.getDaoUserImplPort();
            } catch(MalformedURLException e) {
                log.error("Can not init user soap interface: {}", e.toString());
            }
        }
        return instance;
    }
}
