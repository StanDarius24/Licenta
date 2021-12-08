package com.stannis.services

import com.google.inject.Inject
import com.stannis.dataModel.Antet
import com.stannis.dataModel.Class
import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTParameterDeclaration
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class FunctionDefinitionService  {

    companion object{
        private lateinit var functionDefinitionService: FunctionDefinitionService

        fun getInstance(): FunctionDefinitionService{
            if(!::functionDefinitionService.isInitialized) {
                functionDefinitionService = FunctionDefinitionService()
            }
            return functionDefinitionService
        }
    }

    private val methodService = MethodService()
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

    fun handleCPPASTFunctionDefinition(declaration: CPPASTFunctionDefinition, statement: Statement?) {
        if(statement is Method) {
            methodService.setAntet(
                statement,
                Antet(
                    declaration.declSpecifier.rawSignature,
                    declaration.declarator.name.rawSignature,
                    getParametersDeclarationArray((declaration.declarator as CPPASTFunctionDeclarator).parameters)
                )
            )
            if (declaration.body != null) {
                if (declaration.body is CPPASTCompoundStatement) {
                    if ((declaration.body as CPPASTCompoundStatement).statements != null) {
                        (declaration.body as CPPASTCompoundStatement).statements
                            .iterator()
                            .forEachRemaining { data: IASTStatement ->
                                CoreParserClass.seeCPASTCompoundStatement(
                                    data,
                                    statement
                                )
                            }
                    }
                } else {
                    throw Exception()
                }
            }
        } else if (statement is Class) {
            println(statement)
        } else {
            throw Exception()
        }
    }

    fun getParametersDeclarationArray(params: Array<ICPPASTParameterDeclaration>): ArrayList<Declaration> {
        val listOfDeclaration = ArrayList<Declaration>()
        params.iterator().forEachRemaining { param: ICPPASTParameterDeclaration -> getTypes(param, listOfDeclaration)}
        return listOfDeclaration
    }

}