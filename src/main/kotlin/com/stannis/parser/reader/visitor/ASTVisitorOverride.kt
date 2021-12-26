package com.stannis.parser.reader.visitor

import com.stannis.dataModel.*
import com.stannis.dataModel.Unit
import com.stannis.dataModel.statementTypes.TypedefStructure
import com.stannis.services.*
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.core.dom.ast.*
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator
import org.eclipse.cdt.core.dom.ast.cpp.*
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ASTVisitorOverride: ASTVisitor() {

    private var switch = false


    companion object{

        private var method = MethodService.createMethod()
        private var unit = UnitService.createUnit()
        fun getUnit() :Unit {
            return this.unit
        }
        fun getMethod() :Method {
            return this.method
        }
    }

    override fun visit(classVirtSpecifier: ICPPASTClassVirtSpecifier?): Int {
        println("Found a ICPPASTClassVirtSpecifier" + classVirtSpecifier?.rawSignature)
        return PROCESS_CONTINUE
    }

    private fun checkDecl(declaration: IASTDeclaration): Boolean {
        switch = false
        if(method.statements != null && method.statements!!.size > 0) {
            method.statements!!.iterator().forEachRemaining { elements ->
                run {
                    if (elements is TypedefStructure) {
                        if(declaration.parent is CPPASTCompositeTypeSpecifier && elements.name == (declaration.parent as CPPASTCompositeTypeSpecifier).name.rawSignature) {
                            switch = true
                        }
                    }
                }
            }
        }
        return switch
    }

    override fun visit(declaration: IASTDeclaration): Int {
        if(checkDecl(declaration)) {
            return PROCESS_CONTINUE
        }
        method = MethodService.createMethod()
        println("Found a declaration: " + declaration.rawSignature)

        ASTNodeService.solveASTNode(declaration as ASTNode, method)

//        when (declaration) {
//            is CPPASTFunctionDefinition -> { // ASTAttributeOwner TODO
//                FunctionDefinitionService.handleCPPASTFunctionDefinition(declaration, method)
//            }
//            is CPPASTSimpleDeclaration -> {
//                if(!SimpleDeclSpecifierService.solveDeclSpecifier(declaration, method, unit)) {
//                    return PROCESS_CONTINUE
//                }
//            }
//            is CPPASTProblemDeclaration -> {
//                println("Declaration problem")
//            }
//            is CPPASTLinkageSpecification -> {
//                println(declaration) //TODO
//            }
//            is CPPASTVisibilityLabel -> {
//                println(declaration) //TODO
//            }
//            is CPPASTTemplateDeclaration -> {
//                println(declaration) // TODO
//            }
//            is CPPASTStaticAssertionDeclaration -> {
//                println(declaration) //TODO
//            }
//            is CPPASTTemplateSpecialization -> {
//                println(declaration) //TODO
//            }
//            is CPPASTAliasDeclaration -> {
//                println(declaration) //TODO
//            }
//            is CPPASTUsingDirective -> {
//                println(declaration) //TODO
//            }
//            is CPPASTUsingDeclaration -> {
//                println(declaration) //TODO
//            }
//            is CPPASTNamespaceAlias -> {
//                println(declaration) //TODO
//            }
//            else -> { throw Exception() }
//        }
        if(method.declarations != null || method.statements != null || method.antet != null || method.methods !=null)
        UnitService.addNewMethod(unit, method)
        return PROCESS_CONTINUE
    }

    override fun visit(initializer: IASTInitializer): Int {
        println("Found a Initializer: " + initializer.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(translationUnit: IASTTranslationUnit): Int {
        println("Found a translationUnit: " + translationUnit.rawSignature)
        unit = UnitService.createUnit()
        return PROCESS_CONTINUE
    }

    override fun visit(name: IASTName): Int {
        println("Found a IASTName: " + name.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(parameterDeclaration: IASTParameterDeclaration): Int {
        println("Found a IASTParameterDeclaration: " + parameterDeclaration.rawSignature )
        return PROCESS_CONTINUE
    }

    override fun visit(declarator: IASTDeclarator): Int {
        println("Found an IASTDeclarator " + declarator.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(declarSpec: IASTDeclSpecifier): Int {
        println("Found an IASTDeclSpecifier: " + declarSpec.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(iast: IASTArrayModifier): Int {
        println("Found an IASTArrayModifier: " + iast.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(ptrOper: IASTPointerOperator): Int {
        println("Found an IASTPointerOperator: " + ptrOper.rawSignature)
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
        return PROCESS_CONTINUE
    }

    override fun visit(token: IASTExpression) : Int {
        println("Found an IASTToken: " + token.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(iastStatement: IASTStatement): Int {
        println("Found an IASTStatement: " + iastStatement.rawSignature)
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
