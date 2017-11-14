$(document).ready(function() {
    console.log("Document ready.");
    window.onclick = function(event) {
        if (event.target == dialog) {
            $("#dialog").hide()
        }
    }

    var id = $("#question").val();
    $.ajax({url:'/question?id=' + id}).then(function (data) {
        console.log("AJAX returned data" + data);
        $('#exact-answer').html(data.exact);
        $('#ideal-answer').html(data.ideal);
        $('#candidate-exact').html(data.candidateExact)
        $('#candidate-ideal').html(data.candidateIdeal)
    });
});

function update_answers() {
    console.log("Update answer event.");
    var id = $("#question").val();
    $.ajax({url:'/question?id=' + id}).then(function (data) {
        console.log("AJAX returned data" + data);
        $('#exact-answer').html(data.exact);
        $('#ideal-answer').html(data.ideal);
        $('#candidate-exact').html(data.candidateExact)
        $('#candidate-ideal').html(data.candidateIdeal)
    });

}

function update_score(score) {
    console.log("Updating score to " + score);
    $('#score-display').html("<b>" + score + "</b>");
}

var filename = '';

function close_dialog() {
    console.log("Closing the dialog");
    $("#dialog").hide();
}

function ok_button() {
    console.log("User approved the deletion.")
    close_dialog();
    window.location = '/admin/delete?id=' + filename;
}

function delete_file(name) {
    /* dialog.style.display = 'block'; */
    console.log("The user clicked on the delete button.")
    $('#dialog').show()
    filename = name;
}

