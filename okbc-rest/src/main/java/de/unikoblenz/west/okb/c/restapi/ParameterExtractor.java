package de.unikoblenz.west.okb.c.restapi;

import spark.Request;

/**
 * Created by Alex on 20.07.2016.
 */
public class ParameterExtractor {

    public static int extractLimit(Request req, int defaultLimit) {
        int limit = defaultLimit;
        String limitParam = req.queryParams("limit");
        if (limitParam != null && limitParam.matches("^\\d+$")) {
            limit = Integer.parseInt(limitParam);
        }
        return limit;
    }

    public static String extractCategory(Request req) {
        String category = req.queryParams("category");
        if (category == null) {
            category = "";
        }
        category = category.replace('_', ' ');
        return category;
    }

    public static String extractLabel(Request req) {
        String label = req.queryParams("label");
        if (label == null || !label.matches("^[a-zA-Z0-9]*$")) {
            label = "";
        }
        return label;
    }

    public static int extractItemId(Request req) throws IllegalArgumentException {
        /* TODO: implement */
        return -1;
    }
}
