layout 'layouts/main.groovy',
    title: 'CMU 11-791',
    content: {
        div(class:'content') {
            h1 '403 Access Denied'
            p 'You do not have permission to view this resource.'
        }
        div(class:'clear, center') {
            a(class:'button', href:'/', 'Home')
            a(class:'button', href:'/login', 'Login')
        }
    }
