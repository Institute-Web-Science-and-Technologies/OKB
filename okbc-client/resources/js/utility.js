function arrangeClaimsByRank(claims) {
    // this is basically some primitive version of bucketsort.
    // 4 buckets: preferred, normal, deprecated, unranked
    var result = {
        'preferred': [],
        'normal': [],
        'deprecated': [],
        'unranked': []
    };
    for (var i = 0; i < claims.length; i++) {
        if (claims[i].hasOwnProperty('ranking')) {
            var ranking = claims[i]['ranking'].trim().toLowerCase();
            if (ranking == 'preferred') {
                result.preferred.push(claims[i]);
            } else if (ranking == 'normal') {
                result.normal.push(claims[i]);
            } else if (ranking == 'deprecated') {
                result.deprecated.push(claims[i]);
            } else {
                result.unranked.push(claims[i]);
            }
        } else {
            result.unranked.push(claims[i]);
        }
    }
    return result;
}