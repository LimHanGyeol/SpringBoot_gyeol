$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) { // e는 클릭이 발생한 이벤트 정보
    e.preventDefault();
    console.log("click!!");

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError() {
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);

    $(".answer-write textarea").val("");
}

//$("a.link-delete-article").click(deleteAnswer);   // 예제의 Delete 코드 그러나 등록한 댓글을 바로 삭제할 수 없었음.
$(document).on('click', '.link-delete-article', deleteAnswer);  // 영상 댓글에 달린 위의 코드 사용시 등록한 글을 바로 지울 수 있음

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");
    console.log("url : " + url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error");
        },
        success: function (data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest("article").remove();
            } else {
                alert(data.errorMessage);
            }
        }
    });
}


String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};