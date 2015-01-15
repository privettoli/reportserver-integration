package org.spend.reportserver.integration.controller;

import play.libs.F.Promise;
import play.mvc.Result;

/**
 * Main controller with two actions: export report and list all reports
 */
public interface ReportExportController {
    String FLASH_KEY_MESSAGE = "message";

    /**
     * Get main page
     */
    Result page();

    /**
     * Get all supported formats
     */
    Result supportedFormats();

    /**
     * Export report by id
     *
     * @param id           identifier of report; get it via
     *                     {@link org.spend.reportserver.integration.controller.ReportExportController#list()} method
     * @param exportFormat one of possible formats; to get list of them, use
     *                     {@link ReportExportController#supportedFormats()} method
     * @return input stream of webservice to transfer data by chunks
     */
    Promise<Result> export(Integer id, String exportFormat);

    /**
     * List list of all reports
     *
     * @return input stream of webservice to transfer data by chunks
     */
    Promise<Result> list();
}
