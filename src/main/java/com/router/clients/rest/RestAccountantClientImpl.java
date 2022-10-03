package com.router.clients.rest;

import com.router.model.TimeRecord;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestAccountantClientImpl implements RestAccountantClient {
    @Override
    public List<TimeRecord> getRecord(Integer userId) {
        return getRecord(userId, null, null);
    }

    @Override
    public List<TimeRecord> getRecord(Integer userId, ZonedDateTime dateStart, ZonedDateTime dateEnd) {
        List<TimeRecord> result = new ArrayList<>();
        return result;
    }
}
