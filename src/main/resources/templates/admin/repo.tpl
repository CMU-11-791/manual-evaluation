layout 'layouts/main.gsp',
title: 'Repository',
content: {
    h1 "Datasets"
    if (error != null) {
        div(class:'alert', error)
    }
    if (message != null) {
        div(class:'info', message)
    }
    table {
        filelist.each { name ->
            tr {
                td name
                td {
                    a(class:'select', href:"/select?id=$name", 'Select')
                    a(id:'deleteBtn', class:'warn', href:'#', onclick:"delete_file('$name')", 'Delete')
                    /* button(id:'deleteBtn', onclick:"delete_file('$name')", 'Delete') */
                }
            }
        }
    }

    form(action:'/admin/upload', method:'post', enctype:'multipart/form-data') {
        input(type:'file', name:'file')
        input(type:'submit', class:'button', value:'Upload', '')
    }

    div(id:'dialog', class:'dialog') {
        div(class:'dialog-content') {
            div(class:'dialog-header') {
                span(class:'close', onclick:'close_dialog()', '&times;')
                h2 'Confirm'
            }
            div(class:'dialog-body') {
                p 'Are you sure you want to delete this dataset?'
                div(class:'center') {
                    button(onclick:'ok_button()', 'Yes')
                }
            }
        }
    }
}