layout 'layouts/login.gsp',
    title: 'CMU 11-791',
    content: {
        div(class:'content') {
            form(action:'/login', method:'post') {
                fieldset {
                    div(class:'column middle') {
                        img src:'/images/logo.png', ''
                    }
                    div(class:'column') {
                        h1 'Please sign in'
                        if (invalid) {
                            div(class:'alert', 'Invalid username or password.')
                        }
                        if (logout) {
                            div(class:'info', 'You have been logged out.')
                        }
                        div(class:'form-group') {
                            input(type:'text', name: 'username', id:'username', class:'form-control input-lg', placeholder:'Username', required:'true', '')
                        }
                        div(class:'form-group') {
                            input(type:'password', name: 'password', id:'password', class:'form-control input-lg', placeholder:'Password', required:'true', '')
                        }
                        div(class:'row') {
                            div(class:'col-xs-6 col-sm-6 col-md-6') {
                                input(type:'submit', class:'btn btl-lg btn-primary btn-block', value:'Sign In', '')
                            }
                            div(class:'col-xs-6 col-sm-6 col-md-6') {
                            }
                        }
                    }
                }
            }
        }
    }