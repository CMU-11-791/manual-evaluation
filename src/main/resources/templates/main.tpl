layout 'layouts/login.gsp',
title: 'CMU 11-791',
content: {
    form(action:'/select', method:'post') {
        h1 'Dataset Selection'
        table {
            tr {
                td 'Select a dataset'
                td {
                    select(id:'file',name:'file') {
                        filelist.each {
                            option value:it, it
                        }
                    }
                }
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
        }
        div(class:'center') {
            input(type:'submit', class:'button', value:'Start')
            a(class:'button', href:'/admin/repository', 'Manage Datasets')
        }
    }
}
