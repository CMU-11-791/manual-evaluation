html {
    head {
        title(title)
        link rel:'stylesheet', type:'text/css', href:'/css/main.css'
        script src:'/js/jquery.min.js', ''
        script src:'/js/main.js', ''
    }
    body {
        div(class:'header') {
            h1 'CMU 11-791'
            h2 'Manual Evaluation'
        }

        div(class:'content') {
            content()
            div(class:'copyright') {
                p 'Copyright 2018 Carnegie Mellon University'
            }
        }
    }
}
