package com.stannis.parser.error

import com.stannis.dataModel.PrimaryBlock
import com.stannis.dataModel.statementTypes.*
import org.eclipse.cdt.core.dom.ast.IASTDeclaration
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration

object MultipleDeclarationWhenComposite {
    var switch = false
    fun checkDecl(declaration: IASTDeclaration, primaryBlock: PrimaryBlock): Boolean {
        switch = false
        if (primaryBlock.statements != null && primaryBlock.statements!!.size > 0) {
            primaryBlock.statements!!.iterator().forEachRemaining { statement ->
                run {
                    if (statement is SimpleDeclaration) {
                        if (statement.declSpecifier is CompositeTypeSpecifier &&
                                (statement.declSpecifier as CompositeTypeSpecifier).declarations !=
                                    null
                        ) {
                            (statement.declSpecifier as CompositeTypeSpecifier).declarations!!
                                .iterator()
                                .forEachRemaining { decl ->
                                    run {
                                        if (decl is SimpleDeclaration && decl.declarators != null) {
                                            decl.declarators!!.iterator().forEachRemaining { declArr
                                                ->
                                                run {
                                                    if (declArr is Declarator) {
                                                        if (declaration is CPPASTSimpleDeclaration
                                                        ) {
                                                            if (declaration.declarators.size > 0) {
                                                                if (declArr.name ==
                                                                        declaration.declarators[0]
                                                                            .name
                                                                            .rawSignature
                                                                ) {
                                                                    switch = true
                                                                }
                                                            }
                                                        }
                                                    } else if (declArr is FunctionDeclarator) {
                                                        if (declArr.name is Name) {
                                                            if (declaration is
                                                                    CPPASTSimpleDeclaration
                                                            ) {
                                                                if (declaration.declarators.size > 0
                                                                ) {
                                                                    if ((declArr.name as Name)
                                                                            .name ==
                                                                            declaration.declarators[
                                                                                    0]
                                                                                .name
                                                                                .rawSignature
                                                                    ) {
                                                                        switch = true
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (decl is FunctionDefinition) {
                                            decl.declarator!!.iterator().forEachRemaining {
                                                declarator ->
                                                run {
                                                    if (declarator is FunctionDeclarator) {
                                                        if (declarator.name is Name) {
                                                            if (declaration is
                                                                    CPPASTFunctionDefinition
                                                            ) {
                                                                if ((declarator.name as Name)
                                                                        .name ==
                                                                        declaration
                                                                            .declarator
                                                                            .name
                                                                            .rawSignature
                                                                ) {
                                                                    switch = true
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                        }
                    } else if (statement is FunctionDefinition) {
                        statement.declarator!!.iterator().forEachRemaining { decl ->
                            run {
                                if (decl is FunctionDeclarator) {
                                    if (declaration is CPPASTFunctionDefinition) {
                                        if (decl.name is Name) {
                                            if ((decl.name as Name).name ==
                                                    declaration.declarator.name.rawSignature
                                            ) {
                                                switch = true
                                            }
                                        } else if (decl.name is QualifiedName) {
                                            if ((decl.name as QualifiedName).lastName is Name) {
                                                if (((decl.name as QualifiedName).lastName as Name)
                                                        .name ==
                                                        declaration.declarator.name.rawSignature
                                                ) {
                                                    switch = true
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return switch
    }
}
