layout 'layouts/main.gsp',
    title: 'Evaluation',
    content: {
        String checkmark = 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg'
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
            div(class:'column3') {
                h3 id:'left-header', 'Left Side'
                h4 'Exact'
                div(id:'left-exact') { p { questions[0]?.exact ?: 'None provided.' } }
                h4 'Ideal'
                div(id:'left-ideal') { p { questions[0]?.ideal ?: 'None provided.' } }
            }

            div(class:'column-mid') {
                h3 'Rate This Answer'
                div(class:'custom-radios') {
                    div {
                        radios.each { radio ->
                            String id = radio.id
                            def params = [id:id, type:'radio', name:'color', value:radio.value]
                            if (radio.checked) {
                                params.checked = true
                            }
                            input(params) { }
                            label(for:id) {
                                //span {
                                //   img src:checkmark, alt:'Checkmark Icon', ''
                                //}
                                span radio.label
                            }

                            br()
                        }
                    }
                }
            }

            div(class:'column3') {
                h3 id: 'right-header', 'Right Side'
                h4 'Exact'
                div(id:'right-exact', '')
                h4 'Ideal'
                div(id:'right-ideal', '')
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
                    tr {
                        td 'Precision'
                        td {
                            select(id:'precision', name:'precision') {
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
                        td 'Recall'
                        td {
                            select(id:'recall', name:'recall') {
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