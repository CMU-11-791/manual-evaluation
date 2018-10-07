layout 'layouts/main.gsp',
title: "Evaluation",
stylesheet: 'pretty-checkbox.min.css',
content: {
    h1 'Testing Radio Buttons'
    div {
        div(class:'pretty p-default') {
            input type:'radio', name:'rate', ''
            div(class:'state') {
               label 'Good'
            }
        }
        div(class:'pretty p-default') {
            input type:'radio', name:'rate', ''
            div(class:'state') {
               label 'Bad'
            }
        }
    }
}
