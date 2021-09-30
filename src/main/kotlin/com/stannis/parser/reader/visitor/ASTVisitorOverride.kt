package com.stannis.parser.reader.visitor

import com.stannis.dataModel.*
import com.stannis.dataModel.Unit
import com.stannis.dataModel.statementTypes.*
import com.stannis.services.MethodService
import com.stannis.services.UnitService
import org.eclipse.cdt.core.dom.ast.*
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator
import org.eclipse.cdt.core.dom.ast.cpp.*
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

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
            println("simpl Declaration")
            declaration.declSpecifier.rawSignature // return type
            declaration.declarators // array of Declarators
            // much more like int x = function(smth...)
        }
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
            .iterator().forEachRemaining { data: IASTStatement -> seeCPASTCompoundStatement(data, method) }
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

    private fun declarationStatementForArgumentType(data: Array<IASTInitializerClause>?, statement: Statement?) {
        data!!.iterator().forEachRemaining {
                datax: IASTInitializerClause ->
            run {
                when (datax) {
                    is CPPASTLiteralExpression -> {
                        statement!!.add(datax.rawSignature)
                    }
                    is CPPASTFunctionCallExpression -> {
                        val list = ArrayList<String>()
                        datax.arguments.iterator().forEachRemaining { data: IASTInitializerClause -> list.add(data.rawSignature) }
                        val func = FunctionCall(
                            null,
                            datax.functionNameExpression.rawSignature,
                                list, null
                            )
                        (statement as FunctionCall).add(func)
                    }
                    is CPPASTIdExpression -> {
                        statement!!.add(datax.name.rawSignature)
                    }
                }
            }
        }
    }

    private fun getArgumentsType(functionCall: CPPASTFunctionCallExpression, statement: Statement?) {
        declarationStatementForArgumentType(functionCall.arguments, statement)

    }

    private fun getFunctionArguments(functionCallExpression: CPPASTFunctionCallExpression, statement: Statement?) {
            println(functionCallExpression.rawSignature)

        val functionCall = FunctionCall(null, functionCallExpression.functionNameExpression.rawSignature, null, null)
        getArgumentsType(functionCallExpression, functionCall)
        statement!!.add(functionCall)
        (functionCallExpression.functionNameExpression as CPPASTIdExpression).name.rawSignature //// function name
        functionCallExpression.arguments // array of arguments
        functionCallExpression.evaluation
    }

    private fun handleOperands(binaryExpression: IASTExpression, statement: Statement?) {
        if((binaryExpression is CPPASTIdExpression) || (binaryExpression is CPPASTLiteralExpression) || (binaryExpression is CPPASTUnaryExpression)) {
            println(binaryExpression.rawSignature)
            statement!!.add(binaryExpression.rawSignature)
        } else if(binaryExpression is CPPASTFunctionCallExpression) {
            getFunctionArguments(binaryExpression, statement)
        }
    }

     private fun getOperands(binaryExpression: CPPASTBinaryExpression, statement: Statement?) {
        while((binaryExpression.operand1 !is CPPASTIdExpression) || (binaryExpression.operand1 !is CPPASTLiteralExpression)) {
            if(binaryExpression.operand1 is CPPASTBinaryExpression) {
                getOperands(binaryExpression.operand1 as CPPASTBinaryExpression, statement)
            }
            break
        }
        while((binaryExpression.operand2 !is CPPASTIdExpression) || (binaryExpression.operand2 !is CPPASTLiteralExpression)) {
            if(binaryExpression.operand2 is CPPASTBinaryExpression) {
                getOperands(binaryExpression.operand2 as CPPASTBinaryExpression, statement)
            }
            break
        }
         handleOperands(binaryExpression.operand1, statement)
         handleOperands(binaryExpression.operand2, statement)
    }

    private fun declStatement(simpleDeclaration: CPPASTSimpleDeclaration, method: Method?) {
        simpleDeclaration.declarators.iterator().forEachRemaining { data ->
            val decl = Declaration(data.name.rawSignature, simpleDeclaration.declSpecifier.rawSignature , data.pointerOperators.size == 1, null)
            getFunctionCall(data, decl)
            methodService.addDeclaration(method!!, decl)
        }
    }

    private fun getFunctionCall(data: IASTDeclarator?, decl: Declaration) {
        if( data != null) {
            if( data.initializer != null) {
                data.initializer.children.iterator().forEachRemaining { datax: IASTNode ->
                    run {
                        when (datax) {
                            is CPPASTFunctionCallExpression -> {
                                val funcCall = FunctionCall(
                                    null,
                                    datax.functionNameExpression.rawSignature,
                                    null,
                                    null
                                )
                                declarationStatementForArgumentType(datax.arguments, funcCall)
                                decl.function = funcCall
                            }
                        }
                    }
                }
            }
        }
    }

    private fun seeCPASTCompoundStatement(data: IASTStatement, method: Method?) {
        println("---------")
        println(data.rawSignature)
        when (data) {
            is CPPASTDeclarationStatement -> {
                declStatement(data.declaration as CPPASTSimpleDeclaration, method)
            }
            is CPPASTExpressionStatement -> {
                println("expr")
                when (data.expression) {
                    is CPPASTBinaryExpression -> {
                        val initialization = Initialization(data.expression.children[0].rawSignature, null, null, null)
                        getOperands(data.expression as CPPASTBinaryExpression, initialization) // new statement structure
                        if(initialization.value!!.size > 1) {
                            initialization.value!!.remove(initialization.value!![initialization.value!!.size - 1])
                        }
                        methodService.addStatement(method!!, initialization)
                    }
                    is CPPASTFunctionCallExpression -> {
                        val functcall = FunctionCall( null,
                            ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTIdExpression).name.rawSignature,
                            null,null
                        )
                        getArgumentsType(data.expression as CPPASTFunctionCallExpression, functcall)
                        methodService.addStatement(method!!, functcall)
                    }
                    is CPPASTUnaryExpression -> {
                        println(data.rawSignature)
                    }
                }
            }
            is CPPASTIfStatement -> {
                println("ifStatement")
                val ifT = If(null, null, null, null, null)
                methodService.addStatement(method!!, ifT)
                getOperands(data.conditionExpression as CPPASTBinaryExpression, ifT)
                val ifBlock = methodService.createMethod()
                ifT.addIfBlock(ifBlock)
                val elseBlock = methodService.createMethod()
                ifT.addElseBlock(elseBlock)
                seeCPASTCompoundStatement(data.thenClause, ifBlock)
                seeCPASTCompoundStatement(data.elseClause, elseBlock)
            }
            is CPPASTWhileStatement -> {
                println("while")
                val whileT = While(null, null, null, null)
                methodService.addStatement(method!!, whileT)
                val methodChild = Method(null, null, null, null)
                whileT.addblock(methodChild)
                getOperands(data.condition as CPPASTBinaryExpression, whileT)
                seeCPASTCompoundStatement(data.body, methodChild)
            }
            is CPPASTProblemStatement -> {
                println("problStatement")
            }
            is CPPASTCompoundStatement -> {
                println("cmpStat")
                data.statements.iterator()
                    .forEachRemaining { dataStatement: IASTStatement -> seeCPASTCompoundStatement(dataStatement, method) }
            }
            is CPPASTReturnStatement -> {
                 val returnT = Return(null, null)
                if( data.returnValue is CPPASTLiteralExpression || data.returnValue is CPPASTIdExpression || data.returnValue is CPPASTUnaryExpression) {
                    returnT.add(data.returnValue.rawSignature)
                    println(data.returnValue.rawSignature)
                } else {
                    getOperands(data.returnValue as CPPASTBinaryExpression, returnT)
                }
                methodService.addStatement(method!!, returnT)
            }
            is CPPASTForStatement -> {
                print(data.rawSignature)
                ((data.initializerStatement as CPPASTDeclarationStatement).declaration as CPPASTSimpleDeclaration).declSpecifier.rawSignature // int
                ((data.initializerStatement as CPPASTDeclarationStatement).declaration as CPPASTSimpleDeclaration).declarators // array of declarations = calculator(4) +dasda etc
                data.conditionDeclaration //????
                data.conditionExpression // declarations i < dadsa || dasdw test(x)
                data.iterationExpression // i = i + dadsawdsa
                (data.body as CPPASTCompoundStatement).statements // body
                getForStatement(data, method!!)
            }
        }
        println()
    }

    private fun getForStatement(data: CPPASTForStatement, method: Method?) {
        //                var forT = For(
//                    data.initializerStatement.rawSignature,
//                )
        data.initializerStatement // declaration Statement int x = 0;
        data.conditionExpression // conditia i<5 || calc();
        data.conditionDeclaration // declaration in condition
        data.iterationExpression // iteratii Binary expr / IdExpr etc..
        data.body // compound statement
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
