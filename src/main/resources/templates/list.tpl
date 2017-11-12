layout 'layouts/main.groovy',
    title: "Evaluation",
    content: {
        h1 'Evaluation'
        table(class:grid) {
            tr {
                th 'ID'
                th 'Evaluator'
                th 'Question'
                th 'Reference'
                th 'Type'
                th 'Readability'
                th 'Repetition'
            }
            data.each { d ->
                tr {
                    td d.id
                    td d.evaluator
                    td d.question
                    td d.reference
                    td d.type
                    td d.readability
                    td d.repetition
                }
            }
        }
        if (link != null) {
            a(class:'button', href:link, 'Back')
        }
    }

