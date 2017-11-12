layout 'layouts/main.groovy',
    title: 'Baseline',
    content: {
        form(action:'/update', method:'post') {
            div {
                h1 'Baseline Comparison'
                label for:'question', 'Select a question'
                select(id:'question', name:'question', onchange:'update_answers(event)') {
                    questions.each { q ->
                        option value:q.id, q.body
                    }
                }
            }
            div(class:'column') {
                h3 'Reference Answer'
                h4 'Exact'
                div(id:'exact-answer') { p { questions[0].exact ?: 'None provided.' } }
                h4 'Ideal'
                div(id:'ideal-answer') { p { questions[0].ideal ?: 'None provided.' } }
            }
            div(class:'column') {
                h3 'Candidate Answer'
                h4 'Exact'
                div(id:'candidate-exact', '')
                h4 'Ideal'
                div(id:'candidate-ideal', '')

            }
            div(class:'clear', '')
            div(class:'center') {
                label for:'rating', 'Rate this answer'
                select(id:'rating', name:'rating') {
                    option value:'-1', 'The reference answer is better.'
                    option value:'0', selected:'selected', 'They are the same.'
                    option value:'1', 'The candidate answer is better.'
                }

                button(type:'submit', class:'button', 'Save')
                a(class:'button', href:'/', 'Home')

            }
        }
    }