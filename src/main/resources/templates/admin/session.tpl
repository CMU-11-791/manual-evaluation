layout 'layouts/main.gsp',
title: 'Session',
content: {
    h1 'General Information'
    p "The repository is located at $path"

    h1 'User'
    table(class:'grid') {
        tr {
            td 'Name'
            td username
        }
        tr {
            td 'Roles'
            td roles.join(' ')
        }
    }

    h1 'Current Session'
    table(class:'grid') {
        tr {
            th 'Name'
            th 'Value'
        }
        tr {
            td 'Reference'
            td reference
        }
        tr {
            td 'Type'
            td type
        }
        tr {
            td 'Dataset'
            td dataset
        }
        tr {
            td 'Index'
            td index
        }
    }

    h1 'Questions'
    table(class:'grid') {
        questions.each { q ->
            tr {
                td q.id
                td q.body
            }
        }
    }
}