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

    public static String extractCategory(Request req) throws IllegalArgumentException {
        String category = req.queryParams("category");
        if (category == null) {
            throw new IllegalArgumentException("No param 'category' found.");
        }
        category = category.replace('_', ' ');
        return category;
    }

    public static String extractLabel(Request req) throws IllegalArgumentException {
        String label = req.queryParams("label");
        if (label == null || !label.matches("^[a-zA-Z0-9]*$")) {
            throw new IllegalArgumentException("No param 'label' found or contains illegal characters.");
        }
        return label;
    }

    public static int extractId(Request req) throws IllegalArgumentException {
        String idParam = req.queryParams("id");
        if (idParam == null) {
            throw new IllegalArgumentException("No param 'id' found.");
        }
        int id;
        // Two options for ids: Wikidata item format or numeric.
        if (idParam.matches("^Q\\d+$")) {
            id = Integer.parseInt(idParam.substring(1));
        } else if (idParam.matches("^\\d+$")) {
            id = Integer.parseInt(idParam);
        } else {
            throw new IllegalArgumentException("Param 'id' is neither a Wikidata ID nor a numeric ID.");
        }
        return id;
    }
}
