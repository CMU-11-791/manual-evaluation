layout 'layouts/main.groovy',
    title: 'Upload status',
    content: {
        h1 heading ?: 'Upload Status'
        if (message != null) {
            p message
        }
        else {
            p 'Unknown status'
        }
        div(class:'center') {
            if (href != null) {
                if (label == null) {
                    label = 'Continue'
                }
                a(class:'button', href:href, label)
            }
            else {
                a(class:'button', href: '/', 'Back')
            }
        }
    }