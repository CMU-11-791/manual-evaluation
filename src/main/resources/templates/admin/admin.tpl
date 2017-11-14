layout 'layouts/main.gsp',
    title: 'CMU 11-791',
    content: {
        div(class:'content') {
            h1 'Admin'
            p 'Some day you will be able to perform administration tasks on this page.'
        }
        div(class:'clear, center') {
            a(class:'button', href:'/', 'Home')
        }
    }