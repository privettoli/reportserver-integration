package org.spend.reportserver.integration.controller;


import org.spend.reportserver.integration.service.ReportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

import static java.lang.String.format;
import static play.libs.F.Promise.pure;
import static play.libs.Json.toJson;

@Controller
public class ReportExportControllerImpl extends play.mvc.Controller implements ReportExportController {

    @Autowired
    private ReportExportService reportExportService;

    @Override
    public Result page() {
        return ok(org.spend.reportserver.integration.view.html.welcome.render());
    }

    @Override
    public Result supportedFormats() {
        return ok(toJson(reportExportService.getSupportedFormats()));
    }

    @Override
    public Promise<Result> export(final Integer id, final String exportFormat) {
        if (!reportExportService.getSupportedFormats().contains(exportFormat)) {
            flash(FLASH_KEY_MESSAGE, format("Format %s is not supported", exportFormat));
            return pure(redirect("/"));
        }

        if (!reportExportService.exist(id).get(15)) {
            flash(FLASH_KEY_MESSAGE, "Incorrect id");
            return pure(redirect("/"));
        }
        try {
            return reportExportService.export(id, exportFormat).map(Results::ok);
        } catch (RuntimeException e) {
            flash(FLASH_KEY_MESSAGE, "Service temporary unavailable");
            return pure(redirect("/"));
        }
    }

    @Override
    public Promise<Result> list() {
        return reportExportService.list()
                .map(Json::toJson)
                .map(Results::ok);
    }
}
