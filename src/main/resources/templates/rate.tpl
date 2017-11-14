layout 'layouts/main.gsp',
    title: 'Evaluation',
    content: {
        form(action:'/save', method:'post') {
            div {
                h1 heading

                label for:'question', 'Select a question'
                select(id:'question', name:'question', onchange:'update_answers(event)') {
                    questions.each { q ->
                        if (selected == q.id) {
                            option value:q.id, selected:'selected', q.body
                        }
                        else {
                            option value:q.id, q.body
                        }
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
            div {
                h2 'Rate this answer'
                table {
                    tr {
                        td class:'first-column', 'Readability'
                        td {
                            select(id:'readability', name:'readability') {
                                options.each { opt ->
                                    if (opt.selected) {
                                        option value:opt.value, selected:'selected', opt.label
                                    }
                                    else {
                                        option value:opt.value, opt.label
                                    }
                                }
                            }
                        }
                    }
                    tr {
                        td 'Repetition'
                        td {
                            select(id:'repetition', name:'repetition') {
                                options.each { opt ->
                                    if (opt.selected) {
                                        option value:opt.value, selected:'selected', opt.label
                                    }
                                    else {
                                        option value:opt.value, opt.label
                                    }
                                }
                            }
                        }
                    }

                }
                input(type:'hidden', name:'type', value:type, '')
                input(type:'hidden', name:'reference', value:reference, '')
                div(class:'center') {
                    button(type:'submit', class:'button', 'Save')
                    a(class:'button', href:'/', 'Restart')
                }

            }
        }
    }