package com.stannis.parser.reader.visitor

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method
import com.stannis.services.forDataModel.DeclarationService
import com.stannis.services.forDataModel.MethodService
import com.stannis.services.forDataModel.StatementService
import com.stannis.services.forDataModel.UnitService
import org.eclipse.cdt.core.dom.ast.*
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator
import org.eclipse.cdt.core.dom.ast.cpp.*
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ASTVisitorOverride: ASTVisitor() {

    private var unit = UnitService()
    private var method = MethodService()
    private var statement = StatementService()
    private var declaration = DeclarationService()

    private var declarationParameter = false

    fun getUnit(): ArrayList<Method> {
        return unit.getUnit()
    }

    override fun visit(classVirtSpecifier: ICPPASTClassVirtSpecifier?): Int {
        println("Found a ICPPASTClassVirtSpecifier" + classVirtSpecifier?.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(declaration: IASTDeclaration): Int {
        println("Found a declaration: " + declaration.rawSignature)
        method.reinit()
        method.addContent(declaration.rawSignature)
        unit.addMethod(method.getMethod())
        return PROCESS_CONTINUE
    }

    override fun visit(initializer: IASTInitializer): Int {
        println("Found a Initializer: " + initializer.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(translationUnit: IASTTranslationUnit): Int {
        println("Found a translationUnit: " + translationUnit.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(name: IASTName): Int {
        println("Found a IASTName: " + name.rawSignature)
        if (method.getMethod().name == null) {
            method.addMethodName(name.rawSignature)
        } else if (statement.getStatement().Token == null) {
            return PROCESS_CONTINUE
        } else {
            declaration.addDeclarationName(name.rawSignature)
        }
        return PROCESS_CONTINUE
    }

    override fun visit(parameterDeclaration: IASTParameterDeclaration): Int {
        println("Found a IASTParameterDeclaration: " + parameterDeclaration.rawSignature )
        declaration.reinit()
        declaration.addDeclarationName(parameterDeclaration.declarator.rawSignature)
        declaration.addDeclarationReturnType(parameterDeclaration.declSpecifier.rawSignature)
        method.addAntet(declaration.getDeclaration())
        return PROCESS_CONTINUE
    }

    override fun visit(declarator: IASTDeclarator): Int {
        println("Found an IASTDeclarator " + declarator.rawSignature)

        return PROCESS_CONTINUE
    }

    override fun visit(declarSpec: IASTDeclSpecifier): Int {
        println("Found an IASTDeclSpecifier: " + declarSpec.rawSignature)
        if (method.getMethod().returnType == null) {
             method.addReturnType(declarSpec.rawSignature)
         } else {
             declaration.addDeclarationReturnType(declarSpec.rawSignature)
             method.addDeclaration(declaration.getDeclaration())
         }

        return PROCESS_CONTINUE
    }

    override fun visit(iast: IASTArrayModifier): Int {
        println("Found an IASTArrayModifier: " + iast.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(ptrOper: IASTPointerOperator): Int {
        println("Found an IASTPointerOperator: " + ptrOper.rawSignature)
        declaration.declarationPointer()
        return PROCESS_CONTINUE
    }

    override fun visit(iastatr: IASTAttribute): Int {
        println("Found an IASTAttribute: " + iastatr.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(specifier :IASTAttributeSpecifier) : Int {
        println("Found an IASTAttributeSpecifier: " + specifier.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(token: IASTToken) : Int {
        println("Found an IASTToken: " + token.rawSignature)
        statement.addToken(token.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(token: IASTExpression) : Int {
        println("Found an IASTToken: " + token.rawSignature)
        return PROCESS_CONTINUE
    }

    fun refactorCPPASTEEqualsInitializer(equals: IASTInitializer?) {
        println(equals)
        if ( equals is CPPASTEqualsInitializer) {
            equals.children.get(0).rawSignature // ecuation x = 5;
        } else if(equals != null) {
            (equals.children.get(0) as CPPASTFunctionCallExpression).arguments // array of arguments
            (equals.children.get(0) as CPPASTFunctionCallExpression).functionNameExpression // expression name
        }
    }

    fun seeCPASTCompoundStatement(data: IASTStatement) {
        println("---------")
        println(data.rawSignature)
        if ( data is CPPASTDeclarationStatement ) {
            (data.declaration as CPPASTSimpleDeclaration).declSpecifier.rawSignature  // type
            (data.declaration as CPPASTSimpleDeclaration).declarators.iterator().next().name.rawSignature // name
            refactorCPPASTEEqualsInitializer((data.declaration as CPPASTSimpleDeclaration).declarators.get(0).initializer)
        }
        else if (data is CPPASTExpressionStatement)
            println("expr")
        else if (data is CPPASTIfStatement)
            println("ifStatement")
        println()
    }

    override fun visit(iastStatement: IASTStatement): Int {
        println("Found an IASTStatement: " + iastStatement.rawSignature)
        if ( iastStatement is CPPASTCompoundStatement ) { //  CPPASTExpressionStatement)
            iastStatement.statements.iterator()
                .forEachRemaining { data: IASTStatement -> seeCPASTCompoundStatement(data) }
        }
        return PROCESS_CONTINUE
    }

    override fun visit(iastTypeId: IASTTypeId): Int {
        println("Found an IASTTypeId: " + iastTypeId.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(iastEnumerator: IASTEnumerator): Int {
        println("Found an IASTTypeId: " + iastEnumerator.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(problem: IASTProblem): Int {
        println("Found an IASTTypeId: " + problem.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastBaseSpecifier: ICPPASTBaseSpecifier): Int {
        println("Found an IASTTypeId: " + icppastBaseSpecifier.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastNamespaceDefinition: ICPPASTNamespaceDefinition): Int {
        println("Found an IASTTypeId: " + icppastNamespaceDefinition.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastTemplateParameter: ICPPASTTemplateParameter): Int {
        println("Found an IASTTypeId: " + icppastTemplateParameter.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastCapture: ICPPASTCapture): Int {
        println("Found an IASTTypeId: " + icppastCapture.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icastDesignator: ICASTDesignator): Int {
        println("Found an IASTTypeId: " + icastDesignator.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastVirtSpecifier: ICPPASTVirtSpecifier): Int {
        println("Found an IASTTypeId: " + icppastVirtSpecifier.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastDecltypeSpecifier: ICPPASTDecltypeSpecifier): Int {
        println("Found an IASTTypeId: " + icppastDecltypeSpecifier.rawSignature)
        return PROCESS_CONTINUE
    }
}
