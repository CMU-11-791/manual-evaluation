layout 'layouts/main.groovy',
    title: 'CMU 11-791',
    content: {
        div(class:'content') {
            h1 '500 Internal Server Error'
            p 'Well, this is awkward. Please report this error and the exact set of steps needed to reproduce the error.'
            p 'Thanks'
        }
        div(class:'clear', '')
        div(class:'center') {
            a(class:'button', href:'/', 'Home')
            a(class:'button', href:'/login', 'Login')
        }
    }
