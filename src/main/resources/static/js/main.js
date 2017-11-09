$(document).ready(function() {
    console.log("Document ready.");
    var id = $("#question").val();
    $.ajax({url:'/question?id=' + id}).then(function (data) {
        console.log("AJAX returned data" + data);
        $('#exact-answer').html(data.exact);
        $('#ideal-answer').html(data.ideal);
        $('#candidate-answer').html(data.candidate)
    });
});

function update_answers() {
    console.log("Update answer event.");
    var id = $("#question").val();
    $.ajax({url:'/question?id=' + id}).then(function (data) {
        console.log("AJAX returned data" + data);
        $('#exact-answer').html(data.exact);
        $('#ideal-answer').html(data.ideal);
        $('#candidate-answer').html(data.candidate);
    });

}

function update_score(score) {
    console.log("Updating score to " + score);
    $('#score-display').html("<b>" + score + "</b>");
}