package com.router.clients.soap;

import lombok.extern.slf4j.Slf4j;
import ru.soap.teamservice.DaoRole;
import ru.soap.teamservice.DaoRoleImplService;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class SoapUserRolesClientFactory {
    private static DaoRole instance;

    private SoapUserRolesClientFactory() {
    }

    public static DaoRole getSoapUserRolesClient() {
        if(instance == null) {
            try {
                URL url = new URL("http://localhost:9898/Roles?wsdl");
                DaoRoleImplService daoRoleImplService = new DaoRoleImplService(url);
                instance = daoRoleImplService.getDaoRoleImplPort();
            } catch(MalformedURLException e) {
                log.error("Can not init user role soap interface: {}", e.toString());
            }
        }
        return instance;
    }
}
