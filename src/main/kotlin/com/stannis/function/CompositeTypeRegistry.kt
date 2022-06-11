package com.stannis.function

import com.stannis.dataModel.DeclarationParent
import com.stannis.dataModel.complexStatementTypes.ComplexCompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.*
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLinkageSpecification
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamespaceDefinition
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTranslationUnit

object CompositeTypeRegistry {

    var list: ArrayList<ComplexCompositeTypeSpecifier>? = null
    lateinit var filepath: String

    fun addCompositeTypeSpecifier(
        cppastCompositeTypeSpecifier: CPPASTCompositeTypeSpecifier,
        node: CompositeTypeSpecifier
    ) {
        if (list == null) {
            list = ArrayList()
        }
        var parent: ASTNode = cppastCompositeTypeSpecifier
        while (
            parent !is CPPASTTranslationUnit &&
                parent !is CPPASTLinkageSpecification &&
                parent !is CPPASTNamespaceDefinition
        ) {
            parent = parent.parent as ASTNode
        }
        if (parent is CPPASTTranslationUnit) {
            val datax =
                (parent.allPreprocessorStatements).map { library ->
                    Name(name = library.rawSignature)
                }

            val classToAdd =
                ComplexCompositeTypeSpecifier(
                    our_class = keepImportantType(node),
                    path = this.filepath,
                    library = datax
                )
            if (list != null && !list!!.contains(classToAdd)) {
                list!!.add(classToAdd)
            }
        }
    }

    private fun keepImportantType(node: CompositeTypeSpecifier): CompositeTypeSpecifier {
        if (node.declarations != null) {
            node.declarations!!.forEach { declaration -> run { locateDesireTypes(declaration) } }
        }
        return node
    }

    private fun locateDesireTypes(declaration: DeclarationParent) {
        println()
    }

    fun setPath(filepath: String) {
        this.filepath = filepath
    }

    fun solveFunction(declaration: CompositeTypeSpecifier, element: FunctionDefinition): Boolean {
        var elementToReplace: SimpleDeclaration? = null
        declaration.declarations!!.forEach { declarationClass ->
            run {
                if (
                    declarationClass is SimpleDeclaration &&
                        declarationClass.declarators?.get(0) is FunctionDeclarator
                ) {
                    if (
                        (declarationClass.declarators?.get(0) as FunctionDeclarator).name ==
                            (element.declarator!![0].name as QualifiedName).lastName
                    ) {
                        if (
                            (element.declarator!![0].name as QualifiedName)
                                .qualifier!!
                                .contains(declaration.name)
                        ) {
                            elementToReplace = declarationClass
                        }
                    }
                }
            }
        }
        if (elementToReplace != null) {
            declaration.declarations!!.remove(elementToReplace!!)
            declaration.declarations!!.add(element)
            return true
        }
        return false
    }

    fun checkInClass(element: FunctionDefinition) {
        if (list != null) {
            list!!.forEach { classElement ->
                run { solveFunction(classElement.our_class, element) }
            }
        }
    }
}
