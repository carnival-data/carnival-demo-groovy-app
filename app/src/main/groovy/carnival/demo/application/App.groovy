package carnival.demo.application


import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource

import carnival.core.graph.Core
import carnival.core.graph.CoreGraph
import carnival.core.graph.CoreGraphTinker

import carnival.demo.library.Library
import carnival.demo.library.LibVine
import carnival.demo.library.LibMethods


class App {

    CoreGraph coreGraph

    public App() {
        coreGraph = CoreGraphTinker.create()
        coreGraph.withTraversal { Graph graph, GraphTraversalSource g ->
            coreGraph.initializeGremlinGraph(
                graph, g, 
                this.getClass().getPackage().getName()
            )
        }
    }


    public void buildGraph() {
        def libVine = new LibVine()
        def libMethods = new LibMethods(libVine)
        
        def appVine = new AppVine()
        def appMethods = new AppMethods(appVine)

        coreGraph.withTraversal { Graph graph, GraphTraversalSource g ->
            libMethods.method('LoadPeople').call(graph, g)
            appMethods.method('LoadPets').call(graph, g)
            appMethods.method('AssignPets').call(graph, g)
            g.V().each { println "${it.label()}" }
        }
    }
    
    String getGreeting() {
        def library = new Library()
        return library.greeting()
    }



    static void main(String[] args) {
        def app = new App()
        app.buildGraph()
    }
}
