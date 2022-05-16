package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.SimpleDeclSpecifier
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclSpecifier

object SimpleDeclSpecifierService {
    fun solveSimpleDeclSpecifieService(
        simplDecl: CPPASTSimpleDeclSpecifier,
        statement: Statement?
    ) {
        val simpleDeclaration = SimpleDeclSpecifier(declarationSpecifier = simplDecl.rawSignature, type = simplDecl.type, isSigned = simplDecl.isSigned, isUnsigned = simplDecl.isUnsigned, isShort = simplDecl.isShort, isLong = simplDecl.isLong, isLongLong = simplDecl.isLongLong, isComplex = simplDecl.isComplex, isImaginary = simplDecl.isImaginary, fDeclTypeExpression = null, isConstant = simplDecl.isConst, isExplicit = simplDecl.isExplicit, isFriend = simplDecl.isFriend, isInline = simplDecl.isInline, isRestrict = simplDecl.isRestrict, isThreadLocal = simplDecl.isThreadLocal, isVirtual = simplDecl.isVirtual, isVolatile = simplDecl.isVolatile)
        if(simplDecl.declTypeExpression != null) {
            val anonimStatement = AnonimStatement.getNewAnonimStatement()
            ASTNodeService.solveASTNode(simplDecl.declTypeExpression as ASTNode, anonimStatement)
            simpleDeclaration.fDeclTypeExpression = anonimStatement.statement
        }
        StatementMapper.addStatementToStatement(statement!!, simpleDeclaration)
    }
}
