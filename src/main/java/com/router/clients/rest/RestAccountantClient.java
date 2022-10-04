package com.router.clients.rest;

import com.router.model.TimeRecord;
import java.util.List;

public interface RestAccountantClient {
    List<TimeRecord> getRecords(Integer userId, Integer days);
}
