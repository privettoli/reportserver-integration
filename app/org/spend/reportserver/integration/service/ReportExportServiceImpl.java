package org.spend.reportserver.integration.service;

import org.spend.reportserver.integration.dto.ReportDto;
import org.spend.reportserver.integration.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.libs.F.Promise;
import play.libs.ws.WSResponse;

import java.io.InputStream;
import java.util.List;

import static java.lang.String.format;
import static play.libs.F.Promise.promise;
import static play.libs.ws.WS.url;

@Service
public class ReportExportServiceImpl implements ReportExportService {
    private ReportRepository reportRepository;

    @Autowired
    public ReportExportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Promise<InputStream> export(Integer id, String format) {
        if (format.equals("xlsx")) {
            format = "excel";
        }
        return url(format(EXPORT_URL_PATTERN, id, format)).get().map(WSResponse::getBodyAsStream);
    }

    @Override
    public Promise<List<ReportDto>> list() {
        return reportRepository.getAll();
    }

    @Override
    public Promise<Boolean> exist(Integer id) {
        return reportRepository.exist(id);
    }

    @Override
    public List<String> getSupportedFormats() {
        return supportedFormats;
    }
}
