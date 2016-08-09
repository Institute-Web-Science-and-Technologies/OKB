function loadUser() {
    var request = createWikidataGetUserDataRequest(handleUserDataRequest);
    executeApiRequest(request);
}

function handleUserDataRequest(data) {
    var userInfo = data['query']['userinfo'];
    // Check if user is not logged in.
    if (userInfo.hasOwnProperty('anon')) { // not logged in.
    } else { // is logged in.
        var username = userinfo['name'];
        executeGetUserInfoRequest(username, function(data){loadUserInfoFrame(userInfo, data);});
    }
}

function loadUserInfoFrame(wdUserInfo, okbUserInfo) {
}