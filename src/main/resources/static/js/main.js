$(document).ready(function() {
    console.log("Document ready.");
    window.onclick = function(event) {
        if (event.target == dialog) {
            $("#dialog").hide()
        }
    }
    update_answers()
    /*
    var id = $("#question").val();
    $.ajax({url:'/question?id=' + id}).then(function (data) {
        console.log("Doc ready AJAX data " + data);
        $('#left-header').html(data.leftHeader)
        $('#left-exact').html(data.exact);
        $('#left-ideal').html(data.ideal);
        $('#right-header').html(data.rightHeader)
        $('#right-exact').html(data.candidateExact)
        $('#right-ideal').html(data.candidateIdeal)
    });
    */
});

function update_answers() {
    console.log("Update answer event.");
    var id = $("#question").val();
    console.log("Question id " + id)
    $.ajax({url:'/question?id=' + id}).then(function (data) {
        console.log("Update AJAX returned data " + data);
        if (typeof data.rating != undefined) {
            $('#' + data.rating).prop('checked', true)
        }
        if (Math.floor(Math.random() * 2) == 0) {
            console.log('Rolled a 0')
            $('#left-header').html('Reference')
            $('#left-exact').html(data.exact);
            $('#left-ideal').html(data.ideal);
            $('#right-header').html('Candidate')
            $('#right-exact').html(data.candidateExact)
            $('#right-ideal').html(data.candidateIdeal)
        }
        else {
            console.log("Rolled a 1")
            $('#left-header').html('Candidate')
            $('#left-exact').html(data.candidateExact)
            $('#left-ideal').html(data.candidateIdeal)
            $('#right-header').html('Reference')
            $('#right-exact').html(data.exact);
            $('#right-ideal').html(data.ideal);
        }
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

