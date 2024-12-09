package ch.admin.wbf.isceco.query.request;

public record AdjacentCursoredPageRequest(String[] cursor, int pageNumber, int pageSize) {
}
