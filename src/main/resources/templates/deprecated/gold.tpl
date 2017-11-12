layout 'layouts/main.groovy',
    title: 'CMU 11-791',
    content: {
        form(action:'/update', method:'post') {
            div {
                h1 'Gold Standard'
                select(id:'question', name:'question', onchange:'update_answers(event)') {
                    questions.each { q ->
                        option value:q.id, q.body
                    }
                }
            }
            div(class:'column') {
                h3 'Exact Answer'
                div(id:'exact-answer') { p { questions[0].exact ?: 'None provided.' } }
                h3 'Ideal Answer'
                div(id:'ideal-answer') { p { questions[0].ideal ?: 'None provided.' } }
            }
            div(class:'column') {
                h3 'Candidate Answer'
                div(id:'candidate-answer', '')
            }
            div(class:'clear', '')
            div(class:'center') {
                h3 'Rate this answer'
                p 'Assign a score to the candidate answer based on how it compares to the gold standard answer.'
                select(id:'rating', name:'rating') {
                    option value:'1', '1 - Very bad'
                    option value:'2', '2'
                    option value:'3', selected:'selected', '3'
                    option value:'4', '4'
                    option value:'5', '5 - Very good'
                }

                button(type:'submit', class:'button', 'Save')
                a(class:'button', href:'/', 'Home')
            }
        }
    }