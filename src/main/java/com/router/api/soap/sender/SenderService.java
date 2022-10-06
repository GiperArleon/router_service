package com.router.api.soap.sender;

import com.router.clients.rest.model.TimeRecord;
import ru.soap.teamservice.User;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface SenderService {
    @WebMethod
    User[] getAllUsers();

    @WebMethod
    TimeRecord[] getRecordsForToday(Long userId);

    @WebMethod
    boolean sendTxtReportToLectors(String report);

    @WebMethod
    boolean sendPdfReportToLectors(String report);
}
