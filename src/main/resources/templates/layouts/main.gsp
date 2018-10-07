html {
    head {
        title(title)
        link rel:'stylesheet', type:'text/css', href:'/css/main.css'
        if (stylesheet) {
            link rel:'stylesheet', type:'text/css', href:stylesheet
        }
        if (stylesheets) {
            stylesheets.each { sheet ->
                link rel:'stylesheet', type:'text/css', href:sheet
            }
        }
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
                            li { a(href:'/goto/baseline', 'Baseline') }
                            li { a(href:'/goto/gold', 'Gold') }
                        }
                    }
                    li {
                        a(href:'#', 'Type')
                        ul {
                            li { a(href:'/goto/summary', 'Summary')}
                            li { a(href:'/goto/factoid', 'Factoid')}
                            li { a(href:'/goto/list', 'List')}
                            li { a(href:'/goto/yesno', 'Yes / No')}
                        }
                    }
                    li {
                        a(href:'#', 'View')
                        ul {
                            li { a(href:'/list', 'Show all' ) }
                            li { a href:'/raw', 'Raw (CSV) output'}
                            li { a href:'/evaluated', 'Evaluated' }
                            li { a href:'/remaining', 'Remaining' }
                        }
                    }
                    li {
                        a(href:'#', 'Admin')
                        ul {
                            li { a href:'/admin/repository', 'Datasets' }
                            li { a href:'/admin/session', 'Session Info' }
                            li { a href:'/admin/radios', 'Radio Buttons' }
                        }
                    }
                    li {
                        a(href:'/login?logout', 'Logout')
                    }
                }
            }
            div(class:'clear', '')
        }

        div(class:'content') {
            content()

            div(class:'copyright') {
                p 'Copyright 2018 Carnegie Mellon University'
            }
        }
    }
}
