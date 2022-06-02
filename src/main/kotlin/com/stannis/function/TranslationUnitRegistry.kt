package com.stannis.function

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement
import com.stannis.dataModel.complexStatementTypes.ClassOrHeader
import com.stannis.dataModel.complexStatementTypes.ComplexCompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.*

object TranslationUnitRegistry {

    lateinit var listOfDirectives: List<String>
    private lateinit var nameSpaceClass: HashMap<NameSpace, ComplexCompositeTypeSpecifier>

    fun createTranslationUnit(boolean: Boolean) {
        nameSpaceClass = HashMap()
        fixNameSpaceClassInheritance()
        val classOrHeader =
            ClassOrHeader(
                directives = listOfDirectives as ArrayList,
                classList = null,
                globalDeclaration = null,
                internDeclaration = null,
                functionCallsWithoutImplementation = null,
                linkageSpecification = null,
                methodsWithFunctionCalls = null,
                namespaces = null,
                externalMethods = null
            )
        classOrHeader.globalDeclaration = SimpleDeclarationRegistry.globalDeclaration
        classOrHeader.internDeclaration = SimpleDeclarationRegistry.internDeclaration
        classOrHeader.methodsWithFunctionCalls =
            FunctionDefinitionRegistry.listOfComplexFunctionCalls
        classOrHeader.functionCallsWithoutImplementation = FunctionDeclaratorRegistry.list
        classOrHeader.classList = CompositeTypeRegistry.list
        classOrHeader.linkageSpecification = ExternDefinitionRegistry.listOfExtern
        classOrHeader.namespaces = NameSpaceRegistry.listOfNameSpace
        classOrHeader.externalMethods = ExternalRegistry.listOfExternal
        ProjectVcxprojComplexRegistry.addFinalTranslation(classOrHeader, boolean)
    }

    private fun fixNameSpaceClassInheritance() {
        if (NameSpaceRegistry.listOfNameSpace != null && CompositeTypeRegistry.list != null) {
            NameSpaceRegistry.listOfNameSpace!!.forEach { nameSpace ->
                run {
                    CompositeTypeRegistry.list!!.forEach { classElement ->
                        run {
                            nameSpace.declarations!!.forEach { nameSpaceDeclaration ->
                                run {
                                    if ((nameSpaceDeclaration as SimpleDeclaration).declSpecifier
                                            is ElaboratedTypeSpecifier
                                    ) {
                                        if (classElement.our_class.name is QualifiedName) {
                                            if ((classElement.our_class.name as QualifiedName)
                                                    .getWrittenName() ==
                                                    (nameSpaceDeclaration.declSpecifier
                                                            as ElaboratedTypeSpecifier)
                                                        .getWrittenName()
                                            ) {
                                                nameSpaceClass[nameSpace] = classElement
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
        removeLinks()
    }

    private fun removeLinks() {
        var elementToDelete: SimpleDeclaration? = null
        nameSpaceClass.forEach { (name, composite) ->
            run {
                if (name.declarations != null) {
                    name.declarations!!.forEach { nameDecl ->
                        run {
                            if ((nameDecl as SimpleDeclaration).declSpecifier
                                    is ElaboratedTypeSpecifier
                            ) {
                                if ((nameDecl.declSpecifier as ElaboratedTypeSpecifier).name ==
                                        (composite.our_class.name as NameInterface).getWrittenName()
                                ) {
                                    elementToDelete = nameDecl
                                }
                            }
                        }
                    }
                }
                if (elementToDelete != null) {
                    name.declarations!!.remove(elementToDelete as Statement)
                    name.declarations!!.add(composite)
                }
            }
            CompositeTypeRegistry.list!!.remove(composite)
        }
    }

    fun clearAllData() {
        NameSpaceRegistry.listOfNameSpace = null
        SimpleDeclarationRegistry.internDeclaration = null
        SimpleDeclarationRegistry.globalDeclaration = null
        FunctionDefinitionRegistry.list = null
        FunctionDefinitionRegistry.listOfComplexFunctionCalls = null
        FunctionDeclaratorRegistry.list = null
        CompositeTypeRegistry.list = null
        ExternDefinitionRegistry.listOfExtern = null
    }
}
