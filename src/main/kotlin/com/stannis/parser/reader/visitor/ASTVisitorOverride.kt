package com.stannis.parser.reader.visitor

import com.stannis.dataModel.*
import com.stannis.dataModel.Unit
import com.stannis.services.*
import org.eclipse.cdt.core.dom.ast.*
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator
import org.eclipse.cdt.core.dom.ast.cpp.*
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.*
 //TODO REFACTORIIING
class ASTVisitorOverride: ASTVisitor() {

    private var unit = Unit(null, null)
    private var method = Method(null, null, null, null)
    private var declaration = Declaration(null, null, null, null)


    private val unitService = UnitService()
    private val methodService = MethodService()

    fun getUnit() :Unit {
        return this.unit
    }

    override fun visit(classVirtSpecifier: ICPPASTClassVirtSpecifier?): Int {
        println("Found a ICPPASTClassVirtSpecifier" + classVirtSpecifier?.rawSignature)
        return PROCESS_CONTINUE
    }

    private fun getTypes(deecl: ICPPASTParameterDeclaration, listOfDeclaration: ArrayList<Declaration>){
        if(deecl is CPPASTParameterDeclaration) {
            declaration = Declaration(deecl.declarator.name.rawSignature, deecl.declSpecifier.rawSignature, deecl.declarator.pointerOperators.size == 1, null)
            listOfDeclaration.add(declaration)
        }
    }

    private fun getParametersDeclarationArray(params: Array<ICPPASTParameterDeclaration>): ArrayList<Declaration>{
        val listOfDeclaration = ArrayList<Declaration>()
        params.iterator().forEachRemaining { param: ICPPASTParameterDeclaration -> getTypes(param, listOfDeclaration)}
        return listOfDeclaration
    }


    override fun visit(declaration: IASTDeclaration): Int {
        method = methodService.createMethod()
        println("Found a declaration: " + declaration.rawSignature)
        if(declaration is CPPASTFunctionDefinition) {
            handleCPPASTFunctionDefinition(declaration, method)
        } else if (declaration is CPPASTSimpleDeclaration) {
            // simple Declaration class Animal{...}
            println("class") // CPASTCompositeTypeSpecifier -> CLASS
            // declSpecifier it s just a string in C
            if(declaration.declSpecifier is CPPASTSimpleDeclSpecifier) {
                declaration.declarators.iterator().forEachRemaining { data ->
                    val decl = Declaration(data.name.rawSignature, declaration.declSpecifier.rawSignature , data.pointerOperators.size == 1, null)
                    methodService.addDeclaration(method, decl)
                }

                println("simpl Declaration")
                declaration.declSpecifier.rawSignature // return type
                declaration.declarators // array of Declarators
                // much more like int x = function(smth...)
            } else {
                println("C++ class")
            }
        }
        if(method.declarations != null || method.statements != null || method.antet != null || method.methods !=null)
        unitService.addNewMethod(unit, method)
        return PROCESS_CONTINUE
    }

    private fun handleCPPASTFunctionDefinition(declaration: CPPASTFunctionDefinition, method: Method) {
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

    override fun visit(initializer: IASTInitializer): Int {
        println("Found a Initializer: " + initializer.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(translationUnit: IASTTranslationUnit): Int {
        println("Found a translationUnit: " + translationUnit.rawSignature)
        unit = unitService.createUnit()
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
//        if ( iastStatement is CPPASTCompoundStatement ) { //  CPPASTExpressionStatement)
//            iastStatement.statements.iterator()
//                .forEachRemaining { data: IASTStatement -> seeCPASTCompoundStatement(data, null) }
//        }
        // var x = EvalFunctionCall()
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
