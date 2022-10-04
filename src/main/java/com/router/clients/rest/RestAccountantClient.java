package com.router.clients.rest;

import com.router.clients.rest.model.TimeRecord;
import java.util.List;

public interface RestAccountantClient {
    List<TimeRecord> getRecords(Long userId, Integer days);
    void postRecord(Integer user, Integer hours, Integer minutes, String description);
}
