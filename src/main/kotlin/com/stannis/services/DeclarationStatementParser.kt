package com.stannis.services

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArrayDeclarator
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArrayModifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration

class DeclarationStatementParser {

    private val functionCallsService = FunctionCallsService()
    private val forBlockService = ForBlockService()
    private val methodService = MethodService()

    fun declStatement(simpleDeclaration: CPPASTSimpleDeclaration, method: Method?) {
        simpleDeclaration.declarators.iterator().forEachRemaining { data ->
            val decl = Declaration(
                data.name.rawSignature,
                simpleDeclaration.declSpecifier.rawSignature,
                data.pointerOperators.size == 1,
                null,
                if (data is CPPASTArrayModifier ) {
                    (data as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                        .rawSignature.toInt()
                } else { 0 }
            )
            functionCallsService.getFunctionCall(data, decl)
            methodService.addDeclaration(method!!, decl)
        }
    }
}