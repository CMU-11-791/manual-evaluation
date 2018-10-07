layout 'layouts/main.gsp',
    title: "Evaluation",
    content: {
        h1 'Evaluation'
        table(class:'grid') {
            tr {
                th 'ID'
                th 'Evaluator'
                th 'Dataset'
                th 'Question'
                th 'Reference'
                th 'Type'
                //th 'Readability'
                //th 'Repetition'
                //th 'Precision'
                //th 'Recall'
                th 'Rating'
            }
            data.each { row ->
                tr {
                    td row.id
                    td row.evaluator
                    td row.dataset
                    td row.question
                    td row.reference
                    td row.type
                    //td row.readability
                    //td row.repetition
                    //td row.precision
                    //td row.recall
                    td row.rating
                }
            }
        }
        div(class:'clear', '')
        div(class:'center') {
            if (link != null) {
                a(class:'button', href:link, 'Back')
            }
            else {
                a(class:'button', href:'/', 'Home')
            }
        }
    }

