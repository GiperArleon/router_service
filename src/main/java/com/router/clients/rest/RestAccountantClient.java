package com.router.clients.rest;

import com.router.model.TimeRecord;
import java.time.ZonedDateTime;
import java.util.List;

public interface RestAccountantClient {
    List<TimeRecord> getRecords(Integer userId);
    List<TimeRecord> getRecords(Integer userId, ZonedDateTime dateStart, ZonedDateTime dateEnd);
}
