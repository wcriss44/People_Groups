function getUsers(allUsers) {
    for (var i in allUsers) {
        var elem = $("<a>");
        elem.attr("href", "/user/" + allUser[i].id);
        elem.text(allUsers[i].users);
        $("#userList").append(elem);
        var elem2 = $("<br>");
        $("#userList").append(elem2);
    }
}
$.get("/user", getUsers);