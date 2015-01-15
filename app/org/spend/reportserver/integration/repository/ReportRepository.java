package org.spend.reportserver.integration.repository;

import org.spend.reportserver.integration.dto.ReportDto;
import play.libs.F;
import play.libs.F.Promise;

import java.util.List;

public interface ReportRepository {
    Promise<List<ReportDto>> getAll();
    Promise<Boolean> exist(Integer id);
}
