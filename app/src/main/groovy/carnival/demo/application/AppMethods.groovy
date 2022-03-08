package carnival.demo.application



import groovy.util.logging.Slf4j
import groovy.transform.ToString

import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import carnival.core.graph.GraphMethods
import carnival.core.graph.GraphMethod

import carnival.demo.library.LibModel



@Slf4j 
class AppMethods implements GraphMethods { 


    AppVine appVine
    Random rand = new Random()


    /** */
    public AppMethods(AppVine appVine) {
        this.appVine = appVine
    }


    /** 
     * Randomly assign pets to owners
     */
    class AssignPets extends GraphMethod {

        void execute(Graph graph, GraphTraversalSource g) {
            def personVs = g.V().isa(LibModel.VX.PERSON).toList()
            def personListSize = personVs.size()
            
            g.V().isa(AppModel.VX.PET).each { petV ->
                def ownerV = personVs.get(rand.nextInt(personListSize))

                log.trace "petV: ${petV}"
                log.trace "ownerV: ${ownerV}"
                AppModel.EX.OWNS.instance().from(ownerV).to(petV).ensure(g)
            }
        }

    }


    /** */
    class LoadPets extends GraphMethod {

        void execute(Graph graph, GraphTraversalSource g) {

            def mdt = appVine
                .method('Pets')
                .call()
            .result

            mdt.data.values().each { rec ->
                log.trace "rec: ${rec}"

                def first = rec.FIRST?.trim()
                def last = rec.LAST?.trim()

                if (!(first || last)) return

                def petV = AppModel.VX.PET.instance().create(graph)
                def nameV = LibModel.VX.NAME.instance().withNonNullProperties(
                    LibModel.PX.FIRST, first,
                    LibModel.PX.LAST, last
                ).ensure(graph, g)
                AppModel.EX.IS_CALLED.instance().from(petV).to(nameV).create()
            }

        }

    }


}