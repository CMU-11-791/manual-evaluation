layout 'layouts/main.gsp',
    title: 'Testing',
    content: {
        h1 'Normal'
        table{
            tr {
                th 'Name'
                th 'Value'
            }
            tr {
                td 'One'
                td '1'
            }
            tr {
                td 'Two'
                td '2'
            }
            tr {
                td 'Three'
                td '3'
            }
        }
        h1 'Grid'
        table(class:'grid') {
            tr {
                th 'Name'
                th 'Value'
            }
            tr {
                td 'One'
                td '1'
            }
            tr {
                td 'Two'
                td '2'
            }
            tr {
                td 'Three'
                td '3'
            }
        }
    }