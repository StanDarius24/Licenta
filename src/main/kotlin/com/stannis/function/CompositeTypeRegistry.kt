package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.ComplexCompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.Name
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
        while (parent !is CPPASTTranslationUnit && parent !is CPPASTLinkageSpecification && parent !is CPPASTNamespaceDefinition) {
            parent = parent.parent as ASTNode
        }
        if (parent is CPPASTTranslationUnit) {
            val datax =
                (parent.allPreprocessorStatements).map { library ->
                    Name(name = library.rawSignature)
                }
            val classToAdd =
                ComplexCompositeTypeSpecifier(
                    our_class = node,
                    path = this.filepath,
                    library = datax
                )
            if (list != null && !list!!.contains(classToAdd)) {
                list!!.add(classToAdd)
            }
        }
    }

    fun setPath(filepath: String) {
        this.filepath = filepath
    }
}
