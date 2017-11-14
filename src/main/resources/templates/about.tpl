layout 'layouts/main.gsp',
    title: 'CMU 11-791',
    content: {
        div(class:'content') {
            h1 'Manual Evaluation'
            h3 "Version $version"
            p '''This application is used to compare the outputs of the BioASQ assignment to outputs
            from both the baseline system and gold standard data.
            '''
        }
        div(class:'clear, center') {
            a(class:'button', href:'/', 'Home')
        }
    }