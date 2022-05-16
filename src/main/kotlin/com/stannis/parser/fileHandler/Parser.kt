package com.stannis.parser.fileHandler

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.function.CompositeTypeRegistry
import com.stannis.function.TranslationUnitRegistry
import com.stannis.parser.json.JsonBuilder
import com.stannis.parser.sln.VcxprojParser
import com.stannis.parser.visitor.ASTVisitorOverride
import com.stannis.services.astNodes.NameSpaceService
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.core.dom.ast.IASTDeclaration
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage
import org.eclipse.cdt.core.index.IIndex
import org.eclipse.cdt.core.model.ILanguage
import org.eclipse.cdt.core.parser.*
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamespaceDefinition

class Parser {

    companion object {
        var bool = false
    }

    private fun getIASTTranslationUnit(code: CharArray): IASTTranslationUnit {
        val fc = FileContent.create("", code)
        val macroDefinitions: Map<String, String> = HashMap()
        val includeSearchPaths: Array<String> = Array(5) { "" }
        val si: IScannerInfo = ScannerInfo(macroDefinitions, includeSearchPaths)
        val ifcp: IncludeFileContentProvider = IncludeFileContentProvider.getEmptyFilesProvider()
        val idx: IIndex? = null
        val options: Int = ILanguage.OPTION_IS_SOURCE_UNIT
        val log: IParserLogService = DefaultLogService()
        return GPPLanguage.getDefault().getASTTranslationUnit(fc, si, ifcp, idx, options, log)
    }

    private fun parseProject(
        filesPath: ArrayList<String>,
        astVisitorOverride: ASTVisitorOverride,
        absolutPath: String,
        projectPath: String,
        listOf: List<String>,
        boolean: Boolean
    ) {
        DirReader.folder = projectPath
        filesPath.iterator().forEachRemaining { filepath ->
            run {
                if(filepath == "TestB.h") {
                    println()
                }
                ASTNodeService.modifier = "public"
                val fileCorectedPath = FileSelector.solvePath(absolutPath, filepath)
                CompositeTypeRegistry.setPath(fileCorectedPath)
                ProjectVcxprojComplexRegistry.setFilePath(fileCorectedPath)
                if (listOf.contains("parse")) {
                    DirReader.makedir(
                        absolutPath.split(projectPath)[1] +
                            OperatingSystem.getSeparator() +
                            filepath
                    )
                }
                if (Reader.checkIfFileExist(fileCorectedPath)) {
                    val data = Reader.readFileAsLinesUsingBufferedReader(fileCorectedPath)
                    val translationUnit: IASTTranslationUnit =
                        getIASTTranslationUnit(data.toCharArray())
                    astVisitorOverride.shouldVisitNames = true
                    astVisitorOverride.shouldVisitDeclarations = true
                    astVisitorOverride.shouldVisitInitializers = true
                    astVisitorOverride.shouldVisitParameterDeclarations = true
                    astVisitorOverride.shouldVisitDeclarators = true
                    astVisitorOverride.shouldVisitDeclSpecifiers = true
                    astVisitorOverride.shouldVisitArrayModifiers = true
                    astVisitorOverride.shouldVisitPointerOperators = true
                    astVisitorOverride.shouldVisitAttributes = true
                    astVisitorOverride.shouldVisitTokens = true
                    astVisitorOverride.shouldVisitExpressions = true
                    astVisitorOverride.shouldVisitStatements = true
                    astVisitorOverride.shouldVisitTypeIds = true
                    astVisitorOverride.shouldVisitEnumerators = true
                    astVisitorOverride.shouldVisitTranslationUnit = true
                    astVisitorOverride.shouldVisitProblems = true
                    astVisitorOverride.shouldVisitDesignators = true
                    astVisitorOverride.shouldVisitBaseSpecifiers = true
                    astVisitorOverride.shouldVisitNamespaces = true
                    astVisitorOverride.shouldVisitTemplateParameters = true
                    astVisitorOverride.shouldVisitCaptures = true
                    astVisitorOverride.shouldVisitVirtSpecifiers = true
                    astVisitorOverride.shouldVisitDecltypeSpecifiers = true
                    astVisitorOverride.includeInactiveNodes = true
                    astVisitorOverride.shouldVisitAmbiguousNodes = true
                    astVisitorOverride.shouldVisitImplicitNames = true
                    astVisitorOverride.shouldVisitImplicitNameAlternates = true
                    astVisitorOverride.shouldVisitImplicitDestructorNames = true
                    checkForNamespace(translationUnit.declarations)
                    TranslationUnitRegistry.listOfDirectives =
                        translationUnit.includeDirectives.map { element -> element.toString() }
                    translationUnit.accept(astVisitorOverride)
                    TranslationUnitRegistry.createTranslationUnit(boolean) //here
                    TranslationUnitRegistry.clearAllData()
                    if (listOf.contains("parse")) {
                        val newPath = absolutPath.split(OperatingSystem.getSeparator())
                        newPath.dropLast(1)
                        val dawdsa = absolutPath.split(projectPath)[1]
                        val fileToWrite =
                            DirReader.createfile(
                                dawdsa + OperatingSystem.getSeparator() + "$filepath.json",
                                null
                            )
                        fileToWrite?.bufferedWriter()?.use { out ->
                            out.write(JsonBuilder.createJson(ASTVisitorOverride.getPrimaryBlock()))
                        }
                    }
                } else {
                    println()
                }
            }
        }
    }

    private fun checkForNamespace(declarations: Array<IASTDeclaration>?) {
        declarations?.forEach { declaration -> run {
            if (declaration is CPPASTNamespaceDefinition) {
                NameSpaceService.solveNameSpace(declaration, true, null)
            }
        }}
    }

    private fun parseFiles(
        listOfFiles: ArrayList<String>?,
        absoluteProjectPath: String,
        astVisitorOverride: ASTVisitorOverride,
        projectPath: String,
        listOf: List<String>,
        boolean: Boolean
    ) {
        parseProject(listOfFiles!!, astVisitorOverride, absoluteProjectPath, projectPath, listOf, boolean)
    }

    fun lookUpForVcxProjAndParseHeaderFiles(
        astVisitorOverride: ASTVisitorOverride,
        projectPath: String,
        listOf: List<String>
    ) {
        ProjectVcxprojComplexRegistry.parsedFiles = ArrayList()
        VcxprojParser.mapOfData.iterator().forEachRemaining { element ->
            run {
                element.value.iterator().forEachRemaining { valueElement ->
                    run {
                        ProjectVcxprojComplexRegistry.setVcxProj(valueElement)
                        val absolutProjectPath =
                            valueElement
                                .path
                                .subSequence(
                                    0,
                                    valueElement.path.lastIndexOf(OperatingSystem.getSeparator())
                                )
                                .toString()
                        if (valueElement.listOfHeaderFiles != null) {
                            parseFiles(
                                valueElement.listOfHeaderFiles as ArrayList<String>?,
                                absolutProjectPath,
                                astVisitorOverride,
                                projectPath,
                                listOf,
                                true
                            )
                        }
                    }
                }
            }
        }
    }

    fun parseCppFiles(astVisitorOverride: ASTVisitorOverride, projectPath: String, listOf: List<String>) {
        VcxprojParser.mapOfData.forEach { (slnStructure, vcxprojStructures) -> run {
            vcxprojStructures.forEach { element -> run {
                ProjectVcxprojComplexRegistry.setVcxProj(element)
                val absolutProjectPath =
                    element
                        .path
                        .subSequence(
                            0,
                            element.path.lastIndexOf(OperatingSystem.getSeparator())
                        )
                        .toString()
                if (element.listOfCppFiles != null) {
                    parseFiles(
                        element.listOfCppFiles as ArrayList<String>?,
                        absolutProjectPath,
                        astVisitorOverride,
                        projectPath,
                        listOf,
                        false
                    )
                }
            } }
        } }
    }
}

