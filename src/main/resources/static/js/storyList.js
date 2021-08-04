/*
closeModal();

$('#addStoryButton').on('click', function () {
    $('#addStory').show();
});

function closeModal() {
    $('#addStory').hide();
}*/

$('#saveStory').on('click', function () {
    let data = $('#storyUrl').val();
    console.log(data);
    $.ajax({
        type: "POST",
        url: "/stories",
        contentType: "text/plain",
        data: data,
        success: success
        // dataType: "text"
    });
    // $.post('/stories', data, success());
});

function success() {
    location.reload();
}

function deleteStory(id) {
    $.ajax({
        type: "DELETE",
        url: "/stories/" + id,
        success: success
    })
}