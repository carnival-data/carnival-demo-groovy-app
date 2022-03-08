package carnival.demo.application



import carnival.graph.VertexDefinition
import carnival.graph.PropertyDefinition
import carnival.graph.EdgeDefinition
import carnival.core.graph.Core

import carnival.demo.library.LibModel


class AppModel {

    @VertexDefinition
    static enum VX {
        PET,
    }

    
    @EdgeDefinition
    static enum EX {
        OWNS(
            domain:[LibModel.VX.PERSON],
            range:[VX.PET]
        ),
        IS_CALLED(
            domain:[VX.PET],
            range:[LibModel.VX.NAME]
        )
    }

}