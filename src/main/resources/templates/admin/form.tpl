layout 'layouts/main.groovy',
    title: 'Forms',
    content: {
        h1 'Form'
        form(action:'/admin/form/save', method:'post') {
            table {
                tr {
                    td 'Name'
                    td { input(type:'text', id:'textid', name:'text', placeHolder:'Name', '') }
                }
                tr {
                    td 'Really?'
                    td {
                        select(id:'selectid', name:'select') {
                            option value:'yes', 'Yes'
                            option value:'no', 'No'
                        }
                    }
                }
            }
        }
    }