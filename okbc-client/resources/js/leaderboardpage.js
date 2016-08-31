function initLeaderboard() {
    executeGetAllUsersRequest(MAX_NUM_SHOWN_ON_LEADERBOARD, printLeaderboard);
}

function printLeaderboard(data) {
    console.log(data);
    if (data.hasOwnProperty('error')) {
        $('#leaderboard').html(Mustache.render($('#leaderboardErrorTemplate').html()));
        return;
    }
    data.shorten = function() {
        return function(text, render) {
            var value = render(text);
            var dotIndex = value.indexOf('.');
            return value.substring(0, dotIndex+4);
        }
    };
    $('#leaderboard').html(Mustache.render($('#leaderboardTemplate').html(), data));
}