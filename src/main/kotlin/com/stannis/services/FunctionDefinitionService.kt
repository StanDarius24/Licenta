package com.stannis.services

import com.stannis.dataModel.Antet
import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTParameterDeclaration
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class FunctionDefinitionService {

    private val methodService = MethodService()
    private var declaration = Declaration(null, null, null, null, 0)

    private fun getTypes(deecl: ICPPASTParameterDeclaration, listOfDeclaration: ArrayList<Declaration>){
            declaration = Declaration(
                deecl.declarator.name.rawSignature,
                deecl.declSpecifier.rawSignature,
                deecl.declarator.pointerOperators.size == 1,
                null,
                if (deecl is CPPASTArrayModifier) {
                    (deecl as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                        .rawSignature.toInt()
                } else { 0 }
            )
            listOfDeclaration.add(declaration)
    }

    fun handleCPPASTFunctionDefinition(declaration: CPPASTFunctionDefinition, method: Method) {
        methodService.setAntet(method,
            Antet(
                declaration.declSpecifier.rawSignature,
                declaration.declarator.name.rawSignature,
                getParametersDeclarationArray((declaration.declarator as CPPASTFunctionDeclarator).parameters)
            )
        )
        (declaration.body as CPPASTCompoundStatement).statements
            .iterator().forEachRemaining { data: IASTStatement -> CoreParserClass.seeCPASTCompoundStatement(data, method) }
        // CPPASTCompoundStatement array
        // WhileStatement, ExpressionStatement, ProblemStatement, Declaration, IfStatement, etc...
    }

    private fun getParametersDeclarationArray(params: Array<ICPPASTParameterDeclaration>): ArrayList<Declaration>{
        val listOfDeclaration = ArrayList<Declaration>()
        params.iterator().forEachRemaining { param: ICPPASTParameterDeclaration -> getTypes(param, listOfDeclaration)}
        return listOfDeclaration
    }

}