package com.stannis.services

import com.google.inject.Inject
import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArrayDeclarator
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArrayModifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration

class DeclarationStatementParser {

    private val functionCallsService = FunctionCallsService()
    private val methodService = MethodService()

    fun declStatement(simpleDeclaration: CPPASTSimpleDeclaration, method: Method? , modifier: String?) {
        simpleDeclaration.declarators.iterator().forEachRemaining { data ->
            val decl = Declaration(
                data.name.rawSignature,
                simpleDeclaration.declSpecifier.rawSignature,
                data.pointerOperators.size == 1,
                null,
                if (data is CPPASTArrayModifier ) {
                    (data as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                        .rawSignature.toInt()
                } else { 0 },
                modifier
            )
            functionCallsService.getFunctionCall(data, decl)
            methodService.addDeclaration(method!!, decl)
        }
    }
}