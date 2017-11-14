html {
    head {
        title(title)
        link rel:'stylesheet', type:'text/css', href:'/css/main.css'
//        link rel:'stylesheet', type:'text/css', href:'webjars/bootstrap/css/bootstrap.min.css'
        script(src:'/js/jquery.min.js', '')
//        script(src:'/webjars/bootstrap/js/bootstrap.min.js', '')
        //script(src:'webjars/jquery/js/jquery.min.js', '')
        script(src:'/js/main.js', '')
    }
    body {
        div(class:'header') {
            h1 'CMU 11-791'
            h2 'Manual Evaluation'
        }

        div(class:'content') {
            content()
            div(class:'copyright') {
                p 'Copyright 2017 Carnegie Mellon University'
            }
        }
    }
}
