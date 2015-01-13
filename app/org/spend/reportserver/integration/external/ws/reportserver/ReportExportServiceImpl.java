package org.spend.reportserver.integration.external.ws.reportserver;

import org.springframework.stereotype.Service;
import play.libs.F.Promise;
import play.libs.ws.WSResponse;

import java.io.InputStream;
import java.util.List;

import static java.lang.String.format;
import static play.libs.ws.WS.url;

@Service
public class ReportExportServiceImpl implements ReportExportService {
    @Override
    public Promise<InputStream> export(Integer id, String format) {
        return url(format(EXPORT_PATTERN, id, format))
                .get().map(WSResponse::getBodyAsStream);
    }

    @Override
    public List<String> getSupportedFormats() {
        return supportedFormats;
    }
}
