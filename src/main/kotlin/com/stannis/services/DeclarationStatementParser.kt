package com.stannis.services

import com.google.inject.Inject
import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionCall
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArrayDeclarator
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArrayModifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration

class DeclarationStatementParser {

    companion object{
        private lateinit var declarationStatementParser: DeclarationStatementParser

        fun getInstance(): DeclarationStatementParser{
            if(!::declarationStatementParser.isInitialized) {
                declarationStatementParser = DeclarationStatementParser()
            }
            return declarationStatementParser
        }
    }

    fun declStatement(simpleDeclaration: CPPASTSimpleDeclaration, statement: Statement?, modifier: String?) {
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
            FunctionCallsService.getInstance().getFunctionCall(data, decl)
            StatementMapper.addStatementToStatement(statement!!, decl)
        }
    }
}