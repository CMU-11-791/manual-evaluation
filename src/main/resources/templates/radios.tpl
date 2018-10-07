layout 'layouts/main.gsp',
title: "Evaluation",
stylesheets: ['/css/pretty-checkbox.min.css', '/css/icons.css'],
content: {
    h1 'Testing Radio Buttons'
    div {
        [Good:'p-success', Meh:'p-warning', Bad:'p-danger'].each { text, style ->
            div(class:'pretty p-plain p-icon p-toggle') {
                input type:'radio', name:'rate', ''
                div(class:"state p-on") {
                    span class:"icon mdi mdi-checkbox-marked-circle $style" ,''
                    label text
                }
                div(class:'state p-off $style') {
                    span class:"icon mdi mdi-checkbox-blank-circle" $style ,''
                    label text
                }
            }
            br()

        }
        /*
        div(class:'pretty p-plain p-icon p-toggle') {
            input type:'radio', name:'rate', ''
            div(class:'state p-on p-success') {
                span class:'icon mdi mdi-checkbox-marked-circle p-success', ''
                label 'Good'
            }
            div(class:'state p-off p-success') {
                span class:'icon mdi mdi-checkbox-blank-circle p-success', ''
                label 'Good'
            }
        }
        br()
        div(class:'pretty p-plain p-icon p-toggle p-warning') {
            input type:'radio', name:'rate', ''
            div(class:'state p-on') {
                span class:'icon mdi mdi-checkbox-marked-circle', ''
                label 'Meh'
            }
            div(class:'state p-off') {
                span class:'icon mdi mdi-checkbox-blank-circle', ''
                label 'Meh'
            }
        }
        br()
        div(class:'pretty p-plain p-icon p-toggle') {
            input type:'radio', name:'rate', ''
            div(class:'state p-on p-danger') {
                span class:'icon mdi mdi-checkbox-marked-circle', ''
                label 'Bad'
            }
            div(class:'state p-off p-danger') {
                span class:'icon mdi mdi-checkbox-blank-circle', ''
                label 'Bad'
            }
        }
        */
    }
}
