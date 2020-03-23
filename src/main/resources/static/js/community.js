function postComment() {
    var questionId = $("#questionId").val();
    var commentContent = $("#commentContent").val();

    comment2target(questionId, commentContent, 1);
}

function postReply(e) {
    var commentId = e.name;
    var replyContent = $("#replyContent" + commentId).val();

    comment2target(commentId, replyContent, 2);
}

function comment2target(id, content, type) {
    if (!content) {
        alert("回复不能为空!");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        //序列化
        data: JSON.stringify({
            parentId: id,
            content: content,
            type: type,
        }),
        success: function (response) {
            if (response.code == "200") {
                if (type == 1) {
                    $(".comments").load("/question/comment/" + id);
                    $("#commentSection").hide();
                    $("html,body").animate({scrollTop: $(".row").offset().top}, 350)
                } else {
                    window.location.reload();
                }
            } else {
                if (response.code == "2003") {
                    if (window.confirm(response.message)) {
                        // window.open("https://github.com/login/oauth/authorize?client_id=f51d72d04928cbffcfac&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.open("https://github.com/login/oauth/authorize?client_id=f51d72d04928cbffcfac&redirect_uri=http://47.103.223.240/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message)
                }
            }
        },
        contentType: "application/json",
    });
}

function getReply(e) {
    var id = e.name;
    var collapseReplyId = "#collapseReply" + id;
    if ($(collapseReplyId).attr('class') == "collapse in") {
        $(collapseReplyId).attr("class", "collapse");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment/" + id,
        success: function (response) {
            if (response.code == "200") {
                $(".reply" + id).empty();
                // var items = [];
                var media = [];
                $.each(response.data, function (key, val) {
                    // items.push(
                    //     "<li class='media'>                                                                " +
                    //     "<div class='media-left'>                                                          " +
                    //     "<img class='media-object' src='" + val.user.avatarUrl + "' alt='...'>           " +
                    //     "</div>                                                                            " +
                    //     "<div class='media-body'>                                                          " +
                    //
                    //     "<h6 class='media-heading'>                                                  " +
                    //     "<span>" + +"</span>" +
                    //     "<span class='pull-right'><a href=''>回复</a></span>                               " +
                    //     "</h6>                                                                             " +
                    //     "<h6 class='media-heading'>" +
                    //     "<span class='pull-left'>" + val.content + "</span>" +
                    //     "<span class='pull-right text-color-999'>" + format(val.gmtCreate) + "</span>" +
                    //     "</h6>                          " +
                    //     "</div>                                                                            " +
                    //     "</li>" +
                    //     "<hr>"
                    // );
                    var mediaElement = $("<div/>", {
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-left",
                        "src": val.user.avatarUrl,
                    }));

                    var mediaComment = $("<div/>", {
                        "class": "media-body",
                    }).append($("<h6/>", {
                        "class": "media-heading",
                    }).append($("<span/>", {
                        html: val.user.name,
                    }), $("<span/>", {
                        "class": "pull-right",
                    }).append($("<a/>", {
                        "href": "",
                        // html: "回复",
                    }))), ($("<h6/>", {}).append($("<span/>", {
                        "class": "pull-left",
                        html: val.content,
                    }), $("<span/>", {
                        "class": "pull-right",
                        html: format(val.gmtCreate)
                    }))));

                    var li = $("<li/>", {
                        "class": "media",
                    }).append(mediaElement, mediaComment);

                    var hr = $("<hr/>");
                    media = $("<div/>").append(li, hr);
                    $(".reply" + id).append(media);
                });

                if ($(collapseReplyId).attr('class') == "collapse") {
                    $(collapseReplyId).attr("class", "collapse in");
                }
                // if ($(collapseReplyId).attr('class') == "collapsing") {
                //     $(collapseReplyId).attr("class", "collapse in");
                // }
                // $(".reply" + id).append(items.join(""));
            } else {
                if (response.code == "2003") {
                    if (window.confirm(response.message)) {
                        // window.open("https://github.com/login/oauth/authorize?client_id=f51d72d04928cbffcfac&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.open("https://github.com/login/oauth/authorize?client_id=f51d72d04928cbffcfac&redirect_uri=http://47.103.223.240/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message)
                }
            }
        },
        contentType: "application/json",
    });
}


function add0(m) {
    return m < 10 ? '0' + m : m
}

function format(timestamp) {
    //timestamp是整数，否则要parseInt转换,不会出现少个0的情况

    var time = new Date(timestamp);
    var year = time.getFullYear();
    var month = time.getMonth() + 1;
    var date = time.getDate();
    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();
    return year + '-' + add0(month) + '-' + add0(date) + ' ' + add0(hours) + ':' + add0(minutes) + ':' + add0(seconds);
}


function show() {
    let tag = $(".dropdown-menu");
    let dropDown = $(".dropdown");
    if (tag.is('.show')){
        tag.removeClass('show');
    } else {
        tag.addClass('show');
    }
    $(document).on('click', function(e){
        if(!dropDown.is(e.target) && dropDown.has(e.target).length === 0){
            tag.removeClass('show');
        }
    });
}

function addTag(e) {
    $(e).clone(false).removeAttr("onclick","addTag").attr("onclick","removeTag(this)").appendTo('#tagList').append("&times;");
    splitTag();
}

function removeTag(e) {
    $(e).remove();
}

function splitTag() {
    let tagArray = new Array();
    $("#tagList").each(function () {
        const reg = new RegExp('×',"g");
        tagArray.push($(this).text().replace(reg,","));
    });
    let tags = tagArray.join('');
    $('#tag').attr('value',tags);
}

