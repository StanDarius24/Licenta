package com.stannis.services

import com.stannis.dataModel.*
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTParameterDeclaration
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object FunctionDefinitionService  {

    private var declaration = Declaration(null, null, null, null, 0, null)

    private fun getTypes(deecl: ICPPASTParameterDeclaration, listOfDeclaration: ArrayList<Declaration>){
            declaration = Declaration(
                deecl.declarator.name.rawSignature,
                deecl.declSpecifier.rawSignature,
                deecl.declarator.pointerOperators.size == 1,
                null,
                if (deecl is CPPASTArrayModifier) {
                    (deecl as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                        .rawSignature.toInt()
                } else { 0 },
                null
            )
            listOfDeclaration.add(declaration)
    }

    fun getParametersDeclarationArray(params: Array<ICPPASTParameterDeclaration>): ArrayList<Declaration> {
        val listOfDeclaration = ArrayList<Declaration>()
        params.iterator().forEachRemaining { param: ICPPASTParameterDeclaration -> getTypes(param, listOfDeclaration)}
        return listOfDeclaration
    }

}