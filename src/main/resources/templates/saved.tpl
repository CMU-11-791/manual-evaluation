layout 'layouts/main.groovy',
    title: 'CMU 11-791',
    content: {
        div(class:'content') {
            h1 'Saved'
            p 'Note: This page is for development and debugging purposes only and will be removed in the production version.'
            table(class:'grid') {
                tr {
                    td 'User'
                    td data.evaluator
                }
                tr {
                    td 'Question'
                    td data.question
                }
                tr {
                    td 'Reference'
                    td data.reference
                }
                tr {
                    td 'Type'
                    td data.type
                }
                tr {
                    td 'Readability'
                    td data.readability
                }
                tr {
                    td 'Repetition'
                    td data.repetition
                }
            }
        }
        div(class:'clear, center') {
            a(class:'button', href:link, 'Return')
        }
    }