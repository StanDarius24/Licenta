package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.FunctionCallWithDeclaration
import com.stannis.dataModel.statementTypes.*

object ClassToDeclarationLinker {

    fun linkClassDeclarationsToDeclarator() {
        FunctionDefinitionRegistry.listOfComplexFunctionCalls!!.iterator().forEachRemaining {
            complexDeclaration ->
            run {
                if (complexDeclaration.body != null) {
                    complexDeclaration.body!!.iterator().forEachRemaining { complexFunction ->
                        run {
                            CompositeTypeRegistry.list!!.iterator().forEachRemaining { complexClass
                                ->
                                run {
                                    if ((((complexFunction as FunctionCallWithDeclaration)
                                                    .declaration as
                                                    Declarator)
                                                .initialization as
                                                EqualsInitializer)
                                            .functionName is
                                            IdExpression
                                    ) {
                                        if ((complexClass.our_class.name as Name).name.equals(
                                                ((((complexFunction.declaration as Declarator)
                                                                .initialization as
                                                                EqualsInitializer)
                                                            .functionName as
                                                            IdExpression)
                                                        .expression as
                                                        Name)
                                                    .name
                                            )
                                        ) {
                                            complexFunction.complexClass = complexClass
                                        }
                                    } else if (((complexFunction.declaration as Declarator)
                                                .initialization as
                                                EqualsInitializer)
                                            .functionName is
                                            FieldReference
                                    ) {
                                        println()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
