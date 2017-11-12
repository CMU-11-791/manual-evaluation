layout 'layouts/main.groovy',
    title: 'CMU 11-791',
    content: {
        div(class:'column') {
            h3 'Baseline Evaluation'
            ol {
                li {
                    a(href:'/baseline/summary', 'Summary')
                }
                li {
                    a(href:'/baseline/factoid', 'Factoid')
                }
                li {
                    a(href:'/baseline/yesno', 'Yes/No')
                }
                li {
                    a(href:'/baseline/list', 'List')
                }
            }
        }
        div(class:'column') {
            h3 'Gold Standard Evaluation'
            ol {
                li {
                    a(href:'/gold/summary', 'Summary')
                }
                li {
                    a(href:'/gold/factoid', 'Factoid')
                }
                li {
                    a(href:'/gold/yesno', 'Yes/No')
                }
                li {
                    a(href:'/gold/list', 'List')
                }
            }
        }
        /*
        form(action:'/update', method:'post') {
            div {
                h1 'Question'
                select(id:'question', name:'question', onchange:'update_answers(event)') {
                    questions.each { q ->
                        option value:q.id, q.body
                    }
                }
            }
            div(class:'column') {
                h1 'Exact Answer'
                div(id:'exact-answer') { p { questions[0].exact ?: 'None provided.' } }
                h1 'Ideal Answer'
                div(id:'ideal-answer') { p { questions[0].ideal ?: 'None provided.' } }
            }
            div(class:'column') {
                h1 'Candidate Answer'
                div(id:'candidate-answer', '')
            }
            div(class:'clear, center') {
                div(class:'slidercontainer') {
                    input(type:'range', min:'1', max:'5', value:'3', class:'slider', id:'score-slider', onchange:'update_score(this.value)')
                    span(id:'score-display', '3')

                }
                button(type:'submit', class:'button', 'Record')
            }
        }
        */
    }