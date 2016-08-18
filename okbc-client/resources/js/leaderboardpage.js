function initLeaderboard() {
    executeGetAllUsersRequest(MAX_NUM_SHOWN_ON_LEADERBOARD, printLeaderboard);
}

function printLeaderboard(data) {
    if (data.hasOwnProperty('error')) {
        $('#leaderboard').html(Mustache.render($('#leaderboardErrorTemplate').html()));
    }
    $('#leaderboard').html(Mustache.render($('#leaderboardTemplate').html(), data));
}