package org.spend.reportserver.integration.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import play.libs.F.Promise;
import play.mvc.Result;

import java.util.List;

import static java.util.Arrays.asList;
import static play.libs.Json.toJson;

public interface WelcomeController {
    String FLASH_KEY_MESSAGE = "message";

    Result page();

    Result supportedFormats();

    Promise<Result> export(Integer id, String exportFormat);
}
