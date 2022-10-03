package com.router.clients.rest;

import com.router.model.TimeRecord;
import java.time.ZonedDateTime;
import java.util.List;

public interface RestAccountantClient {
    List<TimeRecord> getRecord(Integer userId);
    List<TimeRecord> getRecord(Integer userId, ZonedDateTime dateStart, ZonedDateTime dateEnd);
}
