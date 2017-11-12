html {
    head {
        title(title)
        link rel:'stylesheet', type:'text/css', href:'/css/main.css'
        script(src:'/js/jquery.min.js', '')
        script(src:'/js/main.js', '')
    }
    body {
        div(class:'header') {
            h1 'CMU 11-791'
            h2 'Manual Evaluation'
            nav {
                ul {
                    li {
                        a(href:'#', 'Reference')
                        ul {
                            li { a(href:'#', 'Baseline') }
                            li { a(href:'#', 'Gold') }
                        }
                    }
                    li {
                        a(href:'#', 'Type')
                        ul {
                            li { a(href:'#', 'Summary')}
                            li { a(href:'#', 'Factoid')}
                            li { a(href:'#', 'List')}
                            li { a(href:'#', 'Yes / No')}
                        }
                    }
                    li {
                        a(href:'#', 'View')
                        ul {
                            li { a(href:'/list', 'List' ) }
                            li { a href:'/raw', 'Raw output'}
                        }
                    }
                    li {
                        a(href:'/login?logout', 'Logout')
                    }
                }
            }
            div(class:'clear', '')
            //div(class:'logout') {
            //    a(href:'/login?logout', 'Logout')
            //}
        }

        div(class:'content') {
            if (content != null) {
                content()
            }
            else {
                h1 'Error'
                p 'Why is Spring Boot calling this with no content!'
            }
            div(class:'copyright') {
                p 'Copyright 2017 Carnegie Mellon University'
            }
        }
    }
}
