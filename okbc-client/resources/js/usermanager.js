/*
* User information is stored in the sessionStorage with the key 'user'.
*/
var userLoginDivId = '#userlogin';
var userLoginFrame;

function initUserManager() {
    userLoginFrame = $(userLoginDivId);
    // user already logged in.
    if (sessionStorage.getItem('user')) {
        var userData = JSON.parse(sessionStorage.getItem('user'));
        loadUserInfoFrame(userData);
        return;
    } else {
        loadUserLoginFrame();
        return;
    }
}

function executeLogin() {
    var username = $('#username').val();
    var password = $('#password').val();
    executeCheckUserLogin(username, password, queryUserInformation);
}

function executeLogout() {
    sessionStorage.removeItem('user');
    loadUserLoginFrame();
}

// TODO: refactor
function queryUserInformation(loginValidationData) {
    console.log(loginValidationData);
    if (loginValidationData.hasOwnProperty('failed')) {
        window.alert(loginValidationData['failed']);
        return;
    } else if (loginValidationData.hasOwnProperty('error')) {
        window.alert('Sorry. The server could not process your login request. This issue may take time to resolve.');
        return;
    } else if (loginValidationData.hasOwnProperty('success')) {
        var username = loginValidationData['username'];
        executeGetUserInfoRequest(username, loadUserInfoFrame);
        return;
    }
    window.alert('Unexpected server response occured. Please report this issue.');
}

function loadUserInfoFrame(userData) {
    console.log(userData);
    if (userData.hasOwnProperty('error')) {
        window.alert(userData['error']);
        return;
    }
    // Save userData.
    sessionStorage.setItem('user', JSON.stringify(userData));
    $(userLoginDivId).html(Mustache.render($('#userInfoTemplate').html(), userData));
}

function loadUserLoginFrame() {
    $(userLoginDivId).html(Mustache.render($('#userLoginTemplate').html()));
}

function getUserNameOrEmpty() {
    if (sessionStorage.getItem('user')) {
        var userData = JSON.parse(sessionStorage.getItem('user'));
        return userData['username'];
    } else {
        return '';
    }
}