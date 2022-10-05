package com.router.api.soap.notification;

import ru.soap.teamservice.User;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style=Style.RPC)
public interface NotifyService {
    @WebMethod
    User[] getUsersWithoutTracks(Integer days);

    @WebMethod
    boolean sendNotificationToLeads(String message);

    @WebMethod
    boolean sendNotificationToLectors(String message);
}

