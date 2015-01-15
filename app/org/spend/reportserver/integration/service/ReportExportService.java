package org.spend.reportserver.integration.service;

import org.spend.reportserver.integration.dto.ReportDto;
import play.libs.F.Promise;

import java.io.InputStream;
import java.util.List;

import static java.util.Arrays.asList;
import static play.Play.application;

public interface ReportExportService {
    List<String> supportedFormats = asList("xlsx", "json", "pdf", "html");
    String URL = application().configuration().getString("external-ws.reportserver.url");
    String USERNAME = application().configuration().getString("external-ws.reportserver.username");
    String PASSWORD = application().configuration().getString("external-ws.reportserver.password");
    String EXPORT_URL_PATTERN = URL + "/reportserver/httpauthexport?id=%d&format=%s&user=" + USERNAME + "&password=" + PASSWORD;

    Promise<InputStream> export(Integer id, String format);

    List<String> getSupportedFormats();

    Promise<List<ReportDto>> list();

    Promise<Boolean> exist(Integer id);
}
