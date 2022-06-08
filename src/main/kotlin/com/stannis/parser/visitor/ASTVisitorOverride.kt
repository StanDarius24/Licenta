package com.stannis.parser.visitor

import com.stannis.dataModel.PrimaryBlock
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.parser.error.MultipleDeclarationWhenComposite
import com.stannis.services.cppastService.ASTNodeService
import mu.KotlinLogging
import org.eclipse.cdt.core.dom.ast.*
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator
import org.eclipse.cdt.core.dom.ast.cpp.*
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration

class ASTVisitorOverride : ASTVisitor() {



    val logger = KotlinLogging.logger {}
    companion object {
        private var primaryBlock = PrimaryBlock(null)
        fun getPrimaryBlock(): PrimaryBlock {
            return primaryBlock
        }
    }

    override fun visit(classVirtSpecifier: ICPPASTClassVirtSpecifier?): Int {
        logger.info { "Found a ICPPASTClassVirtSpecifier" + classVirtSpecifier?.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(declaration: IASTDeclaration): Int {
        if ((declaration is CPPASTFunctionDefinition || declaration is CPPASTSimpleDeclaration)) {
            if (MultipleDeclarationWhenComposite.checkDecl(declaration, primaryBlock)) {
                return PROCESS_CONTINUE
            }
        }
        logger.info { "Found a declaration: " + declaration.rawSignature }
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(declaration as ASTNode, anonimStatement)
        if (anonimStatement.statement != null) {
            primaryBlock.addStatement(anonimStatement.statement as Statement)
        }
        return PROCESS_CONTINUE
    }

    override fun visit(initializer: IASTInitializer): Int {
        logger.info { "Found a Initializer: " + initializer.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(translationUnit: IASTTranslationUnit): Int {
        logger.info { "Found a translationUnit: " + translationUnit.rawSignature }
        primaryBlock = PrimaryBlock(null)
        return PROCESS_CONTINUE
    }
    override fun visit(name: IASTName): Int {
        logger.info { "Found a IASTName: " + name.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(parameterDeclaration: IASTParameterDeclaration): Int {
        logger.info { "Found a IASTParameterDeclaration: " + parameterDeclaration.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(declarator: IASTDeclarator): Int {
        logger.info { "Found an IASTDeclarator " + declarator.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(declarSpec: IASTDeclSpecifier): Int {
        logger.info { "Found an IASTDeclSpecifier: " + declarSpec.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(iast: IASTArrayModifier): Int {
        logger.info { "Found an IASTArrayModifier: " + iast.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(ptrOper: IASTPointerOperator): Int {
        logger.info { "Found an IASTPointerOperator: " + ptrOper.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(iastatr: IASTAttribute): Int {
        logger.info { "Found an IASTAttribute: " + iastatr.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(specifier: IASTAttributeSpecifier): Int {
        logger.info { "Found an IASTAttributeSpecifier: " + specifier.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(token: IASTToken): Int {
        logger.info { "Found an IASTToken: " + token.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(token: IASTExpression): Int {
        logger.info { "Found an IASTToken: " + token.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(iastStatement: IASTStatement): Int {
        logger.info { "Found an IASTStatement: " + iastStatement.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(iastTypeId: IASTTypeId): Int {
        logger.info { "Found an IASTTypeId:"  + iastTypeId.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(iastEnumerator: IASTEnumerator): Int {
        logger.info { "Found an IASTTypeId: " + iastEnumerator.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(problem: IASTProblem): Int {
        logger.info { "Found an IASTTypeId: " + problem.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(icppastBaseSpecifier: ICPPASTBaseSpecifier): Int {
        logger.info { "Found an IASTTypeId: " + icppastBaseSpecifier.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(icppastNamespaceDefinition: ICPPASTNamespaceDefinition): Int {
        logger.info { "Found an IASTTypeId: " + icppastNamespaceDefinition.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(icppastTemplateParameter: ICPPASTTemplateParameter): Int {
        logger.info { "Found an IASTTypeId: " + icppastTemplateParameter.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(icppastCapture: ICPPASTCapture): Int {
        logger.info { "Found an IASTTypeId: " + icppastCapture.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(icastDesignator: ICASTDesignator): Int {
        logger.info { "Found an IASTTypeId: " + icastDesignator.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(icppastVirtSpecifier: ICPPASTVirtSpecifier): Int {
        logger.info { "Found an IASTTypeId: " + icppastVirtSpecifier.rawSignature }
        return PROCESS_CONTINUE
    }

    override fun visit(icppastDecltypeSpecifier: ICPPASTDecltypeSpecifier): Int {
        logger.info { "Found an IASTTypeId: " + icppastDecltypeSpecifier.rawSignature }
        return PROCESS_CONTINUE
    }
}
