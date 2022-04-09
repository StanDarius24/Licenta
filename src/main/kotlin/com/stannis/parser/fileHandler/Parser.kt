package com.stannis.parser.fileHandler

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.function.CompositeTypeRegistry
import com.stannis.function.TranslationUnitRegistry
import com.stannis.parser.json.JsonBuilder
import com.stannis.parser.sln.VcxprojParser
import com.stannis.parser.visitor.ASTVisitorOverride
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage
import org.eclipse.cdt.core.index.IIndex
import org.eclipse.cdt.core.model.ILanguage
import org.eclipse.cdt.core.parser.*

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
        projectPath: String
    ) {
        DirReader.folder = projectPath
        filesPath.iterator().forEachRemaining { filepath ->
            run {
                ASTNodeService.modifier = "public"
                val fileCorectedPath = FileSelector.solvePath(absolutPath, filepath)
                if(fileCorectedPath == "/home/stan/Desktop/Licenta/src/main/resources/project64-develop/Source/3rdParty/discord-rpc/backoff.h") {
                    println()
                }
                CompositeTypeRegistry.setPath(
                    fileCorectedPath
                )
                ProjectVcxprojComplexRegistry.setFilePath(
                    fileCorectedPath
                )
                DirReader.makedir(
                    absolutPath.split(projectPath)[1] + OperatingSystem.getSeparator() + filepath
                )
                if (Reader.checkIfFileExist(fileCorectedPath)
                ) {
                    val data =
                        Reader.readFileAsLinesUsingBufferedReader(
                            fileCorectedPath
                        )
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
                    TranslationUnitRegistry.listOfDirectives =
                        translationUnit.includeDirectives.map { element -> element.toString() }
                    translationUnit.accept(astVisitorOverride)
                    TranslationUnitRegistry.createTranslationUnit()
                    TranslationUnitRegistry.clearAllData()
                    val builder = JsonBuilder()
                    val newPath = absolutPath.split(OperatingSystem.getSeparator())
                    newPath.dropLast(1)
                    val dawdsa = absolutPath.split(projectPath)[1]
                    val fileToWrite =
                        DirReader.createfile(
                            dawdsa + OperatingSystem.getSeparator() + "$filepath.json"
                        )
//                    fileToWrite.bufferedWriter().use { out ->
//                        out.write(builder.createJson(ASTVisitorOverride.getPrimaryBlock()))
//                    }
                } else {
                    println()
                }
            }
        }
        println()
    }

    private fun parseFiles(
        listOfFiles: ArrayList<String>?,
        absoluteProjectPath: String,
        astVisitorOverride: ASTVisitorOverride,
        projectPath: String
    ) {
        parseProject(listOfFiles!!, astVisitorOverride, absoluteProjectPath, projectPath)
    }

    fun lookUpForVcxProjAndParseHeaderFiles(
        astVisitorOverride: ASTVisitorOverride,
        projectPath: String
    ) {
        VcxprojParser.mapOfData.iterator().forEachRemaining { element ->
            run {
                println()
                element.value.iterator().forEachRemaining { valueElement ->
                    run {
                        ProjectVcxprojComplexRegistry.setVcxProj(valueElement)
                        if (valueElement.listofIncludedModules != null) {
                            println()
                        }
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
                                projectPath
                            )
                        }
                    }
                }
            }
        }
        println()
        //        VcxprojParser.mapOfData.filter { element -> element.value.size == 1 }.filter {
        // lastElem -> lastElem.value[0].listofIncludedModules.size == 0 } // Primitive functions
    }
}

