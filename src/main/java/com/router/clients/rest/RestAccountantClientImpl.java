package com.router.clients.rest;

import com.router.model.TimeRecord;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class RestAccountantClientImpl implements RestAccountantClient {
    private final RestRequestHandler restRequestHandler;

    RestAccountantClientImpl(RestRequestHandler restRequestHandler) {
        this.restRequestHandler = restRequestHandler;
    }

    @Override
    public List<TimeRecord> getRecords(Integer userId, Integer days) {
        List<TimeRecord> result = restRequestHandler.getUserRecords(userId, days);
        for(TimeRecord timeRecord: result) {
            log.debug("{} {} {} {} {} {}", timeRecord.getId(), timeRecord.getUserId(), timeRecord.getDate(), timeRecord.getHours(), timeRecord.getMinutes(), timeRecord.getDescription());
        }
        return result;
    }

    @Override
    public void postRecord(String description, String hours, String minutes) {
    }
}
