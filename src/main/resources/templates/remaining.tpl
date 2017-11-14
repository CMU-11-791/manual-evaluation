layout 'layouts/main.gsp',
title:'Questions Remaining',
content: {
    h1 'Questions Remaining'
    p 'These are the questions that you have not evaluated yet.'
    table(class:'grid') {
        data.each { question ->
            tr {
                td { a href:"${link}?id=${question.id}", question.id }
                td question.body
            }
        }
    }
    div(class:'clear')
    div(class:'center') {
        a(class:'button', href:link, 'Back')
    }
}