layout 'layouts/main.groovy',
    title: 'CMU 11-791',
    content: {
        div(class:'content') {
            h1 'Success'
            p 'Data was posted.'
        }
        div(class:'clear, center') {
            a(class:'button', href:'/', 'Return')
        }
    }