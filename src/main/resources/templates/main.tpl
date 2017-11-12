layout 'layouts/login.groovy',
    title: 'CMU 11-791',
    content: {
        form(action:'/upload', method:'post', enctype:'multipart/form-data') {
            h1 'Data Upload'
            table {
                tr {
                    td class:'first-column', 'Select a file'
                    td { input(type:'file', name:'file') }
                }
                tr {
                    td 'Reference Type'
                    td {
                        select(id:'ref', name:'ref') {
                            option selected:true, value:'baseline', 'Baseline'
                            option value:'gold', 'Gold standard'
                        }
                    }
                }
                tr {
                    td 'Question Type'
                    td {
                        select(id:'type', name:'type') {
                            option value:'summary', 'Summary'
                            option value:'factoid', 'Factoid'
                            option value:'list', 'List'
                            option value:'yesno'm 'Yes/No'
                        }
                    }
                }
                tr {
                    td(colspan:'2') {
                        input(type:'submit', class:'button', value:'Upload', '')
                        a(class:'button', href: '/', 'Cancel')
                    }
                }
            }
        }
    }