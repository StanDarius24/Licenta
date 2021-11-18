package com.stannis.parser.reader.visitor

import com.stannis.dataModel.*
import com.stannis.dataModel.Unit
import com.stannis.dataModel.statementTypes.TypedefStructure
import com.stannis.services.*
import org.eclipse.cdt.core.dom.ast.*
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator
import org.eclipse.cdt.core.dom.ast.cpp.*
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ASTVisitorOverride: ASTVisitor() {

    private var switch = false
    private val unitService = UnitService()
    private val methodService = MethodService()
    private val classService = ClassService()
    private val funcDefService = FunctionDefinitionService()

    companion object{

        private var method = Method(null, null, null, null, null)
        private var unit = Unit(null, null, null)

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

    override fun visit(declaration: IASTDeclaration): Int {
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
        if(switch) {
            switch = false
            return PROCESS_CONTINUE
        }
        method = methodService.createMethod()
        println("Found a declaration: " + declaration.rawSignature)
        if(declaration is CPPASTFunctionDefinition) {
            funcDefService.handleCPPASTFunctionDefinition(declaration, method)
        } else if (declaration is CPPASTSimpleDeclaration) {
            // simple Declaration class Animal{...}
            println("class") // CPASTCompositeTypeSpecifier -> CLASS
            // declSpecifier it s just a string in C
            when (declaration.declSpecifier) {
                is CPPASTSimpleDeclSpecifier -> {
                    declaration.declarators.iterator().forEachRemaining { data ->
                        val typedefSt  =TypedefStructure(null, null, null)
                        methodService.addStatement(method, typedefSt)
                        if(data is CPPASTFunctionDeclarator) {
                            data.parameters.iterator().forEachRemaining { parametersx ->
                                val declaratorTT = Declaration(
                                    if(parametersx.declarator.nestedDeclarator != null) {
                                        parametersx.declarator.nestedDeclarator.rawSignature
                                    } else {
                                        parametersx.declarator.name.rawSignature
                                },
                                    parametersx.declSpecifier.rawSignature
                                    , parametersx.declarator.pointerOperators.size == 1, null
                                ,
                                    if (parametersx is CPPASTArrayModifier ) {
                                        (parametersx as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                                            .rawSignature.toInt()
                                    } else { 0 }
                                , null
                                )
                                typedefSt.add(declaratorTT)
                            }
                        }
                        val decl = Declaration(
                            if(data.nestedDeclarator != null) {
                            data.nestedDeclarator.rawSignature
                        } else {
                            data.name.rawSignature
                        },
                            declaration.declSpecifier.rawSignature,
                            data.pointerOperators.size == 1,
                            null,
                            if (data is CPPASTArrayModifier ) {
                                (data as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                                    .rawSignature.toInt()
                            } else { 0 },
                            null
                            )
                        typedefSt.initialization = decl
                    }

                    println("simpl Declaration")
                    declaration.declSpecifier.rawSignature // return type
                    declaration.declarators // array of Declarators
                    // much more like int x = function(smth...)
                }
                is CPPASTCompositeTypeSpecifier -> {
                    (declaration.declSpecifier as CPPASTCompositeTypeSpecifier).storageClass // fkey 1 struct fkey 3 class
                    if((declaration.declSpecifier as CPPASTCompositeTypeSpecifier).key == 3) {
                        val classDeclaration = Class((declaration.declSpecifier as CPPASTCompositeTypeSpecifier).name.rawSignature, null, null)
                        unitService.addClass(unit, classDeclaration)
                        (declaration.declSpecifier as CPPASTCompositeTypeSpecifier).members // array of members
                        classService.parseDecl(classDeclaration, (declaration.declSpecifier as CPPASTCompositeTypeSpecifier))
                        return PROCESS_CONTINUE
                        println("CLASS C++")
                    }
                    (declaration.declSpecifier as CPPASTCompositeTypeSpecifier).members
                    val typedefT = TypedefStructure((declaration.declSpecifier as CPPASTCompositeTypeSpecifier).name.rawSignature, null, null)
                    if(declaration.declarators.isNotEmpty()) {
                        declaration.declarators.iterator().forEachRemaining {
                            declrS ->
                            run {
                                val declAfterTypedef = Declaration(
                                    if(declrS.nestedDeclarator != null) {
                                        declrS.nestedDeclarator.rawSignature
                                    } else {
                                        declrS.name.rawSignature
                                    },
                                    ((declrS.parent as CPPASTSimpleDeclaration).declSpecifier as CPPASTCompositeTypeSpecifier).name.rawSignature,
                                    null,
                                    null,
                                    if (declrS is CPPASTArrayModifier ) {
                                        (declrS as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                                            .rawSignature.toInt()
                                    } else { 0 },
                                    null
                                )
                                methodService.addDeclaration(method, declAfterTypedef)
                            }
                        }

                    }
                    methodService.addStatement(method, typedefT)
                    declaration.declSpecifier.children.iterator().forEachRemaining {
                        data ->
                        run {
                            if (data is CPPASTSimpleDeclaration) {
                                println(data.rawSignature)
                                data.declarators.iterator().forEachRemaining {
                                    datax ->
                                    run {
                                        typedefT.add(
                                            Declaration(
                                                datax.name.rawSignature,
                                                data.declSpecifier.rawSignature,
                                                datax.pointerOperators.size == 1,
                                                null,
                                                if (datax is CPPASTArrayModifier ) {
                                                        (datax as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                                                            .rawSignature.toInt()
                                                } else { 0 },
                                                null
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                else -> {
                    println("Apel de functie data.ceva()")
                }
            }
        }
        if(method.declarations != null || method.statements != null || method.antet != null || method.methods !=null)
        unitService.addNewMethod(unit, method)
        return PROCESS_CONTINUE
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
