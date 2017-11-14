layout 'layouts/main.gsp',
    title: 'CMU 11-791',
    content: {
        div(class:'content') {
            h1 '500 Internal Server Error'
            p 'Well, this is awkward. Please report this error and the exact set of steps needed to reproduce the error.'
            p 'Thanks'
        }
        div(class:'clear', '')
        div(class:'center') {
            if (link != null) {
                a(class:'button', href:link, 'Back')
            }
            else {
                a(class:'button', href:'/', 'Home')
            }
        }
    }
