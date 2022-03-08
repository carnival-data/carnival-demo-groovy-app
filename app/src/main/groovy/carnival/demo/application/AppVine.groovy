package carnival.demo.application


import groovy.transform.ToString

import carnival.util.MappedDataTable
import carnival.core.vine.Vine
import carnival.core.vine.MappedDataTableVineMethod
import carnival.core.vine.CacheMode



class AppVine implements Vine {

    class Pets extends MappedDataTableVineMethod {
        
        MappedDataTable fetch(Map args) {
            def mdt = createDataTable(idFieldName:'ID')

            // this is where code would go to perform the query against the
            // data source using a Java or Groovy library.  here, we will just
            // add test data to the output MappedDataTable manually.

            mdt.dataAdd(id:'1', first:'Jean', last:'Gabin')
            mdt.dataAdd(id:'2', first:'Peppe')
            mdt
        }

    }

}