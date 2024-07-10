package org.hankki.hankkiserver.api.report.service.response;

public record ReportServiceResponse(
        long count
) {
    public static ReportServiceResponse of(long count) {
        return new ReportServiceResponse(count);
    }
}
