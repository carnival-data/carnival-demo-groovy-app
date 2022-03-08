package carnival.demo.application


import spock.lang.Specification
import carnival.demo.library.LibModel


class AppTest extends Specification {

    def "build graph"() {
        def numPersonVs
        def numPetVs
        def numOwnedPetVs
        def numPetOwnerVs

        setup:
        def app = new App()

        when:
        app.coreGraph.withTraversal { graph, g ->
            numPersonVs = g.V().isa(LibModel.VX.PERSON).count().next()
            numPetVs = g.V().isa(AppModel.VX.PET).count().next()
            numOwnedPetVs = g.V()
                .isa(LibModel.VX.PERSON)
                .out(AppModel.EX.OWNS)
                .isa(AppModel.VX.PET)
            .count().next()
            numPetOwnerVs = g.V()
                .isa(AppModel.VX.PET)
                .in(AppModel.EX.OWNS)
                .isa(LibModel.VX.PERSON)
            .count().next()
        }
        
        then:
        numPersonVs == 0
        numPetVs == 0
        numPetOwnerVs == 0
        numOwnedPetVs == 0

        when:
        app.buildGraph()

        app.coreGraph.withTraversal { graph, g ->
            numPersonVs = g.V().isa(LibModel.VX.PERSON).count().next()
            numPetVs = g.V().isa(AppModel.VX.PET).count().next()
            numOwnedPetVs = g.V()
                .isa(LibModel.VX.PERSON)
                .out(AppModel.EX.OWNS)
                .isa(AppModel.VX.PET)
            .count().next()
            numPetOwnerVs = g.V()
                .isa(AppModel.VX.PET)
                .in(AppModel.EX.OWNS)
                .isa(LibModel.VX.PERSON)
            .count().next()
        }

        then:
        numPersonVs == 2
        numPetVs == 2
        numPetOwnerVs > 0
        numOwnedPetVs == 2
    }


    def "application has a greeting"() {
        setup:
        def app = new App()

        when:
        def result = app.greeting

        then:
        result != null
    }
}
