package com.router.clients.rest;

import static com.router.tools.PropertyReader.PROPERTIES;

public class RestRequestUrls {
    public static final String ACCOUNTANT_SERVER = PROPERTIES.getProperties().get("accountant.server.url");
    public static final String GET_USER_RECORDS_URL = ACCOUNTANT_SERVER + "/get_user_by_id/?user_id=%d&days=%d";
    public static final String POST_USER_URL = ACCOUNTANT_SERVER + "/create_record";
    public static final String POST_USER_BODY = "{ \"userId\": \"%d\",  \"description\": \"%s\",  \"hours\": \"%d\",  \"minutes\": \"%d\"}";
}
