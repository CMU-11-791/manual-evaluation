layout 'layouts/main.gsp',
    title: 'CMU 11-791',
    content: {
        div(class:'content') {
            h1 '403 Access Denied'
            p 'You do not have permission to view this resource.'
        }
        div(class:'clear, center') {
            if (link != null) {
                a(class:'button', href:link, 'Back')
            }
            else {
                a(class:'button', href:'/', 'Home')
            }
        }
    }
