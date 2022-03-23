package com.stannis.parser.fileHandler

import com.stannis.callHierarchy.classParser
import com.stannis.function.ClassToDeclarationLinker
import com.stannis.function.CompositeTypeRegistry
import com.stannis.function.FunctionDefinitionRegistry
import com.stannis.function.TranslationUnitRegistry
import com.stannis.parser.json.JsonBuilder
import com.stannis.parser.visitor.ASTVisitorOverride
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage
import org.eclipse.cdt.core.index.IIndex
import org.eclipse.cdt.core.model.ILanguage
import org.eclipse.cdt.core.parser.*

class Parser {

    companion object {
        var bool = false
    }

    fun justDoSmth(absolutPath: String, astVisitorOverride: ASTVisitorOverride) {

        FileSelector.setListOfPathsParam(DirReader.getAllFilesInResources(absolutPath))
        var filepath = FileSelector.getHeaderClassFirst()
        while (filepath != "") {
            if (filepath.contains(".h")) {
                bool = true
                ASTVisitorOverride.setCheck(true)
            } else {
                bool = false
                ASTVisitorOverride.setCheck(false)
            }
            CompositeTypeRegistry.setPath(filepath)
            val dir = filepath.split(absolutPath)[1]
            dir.subSequence(1, dir.length)
            val pathToWrite = DirReader.makedir(OperatingSystem.getSeparator() + dir.subSequence(1, dir.length))
            val data = Reader.readFileAsLinesUsingBufferedReader(filepath)
            val translationUnit: IASTTranslationUnit = getIASTTranslationUnit(data.toCharArray())

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
            println("DATA::: $filepath")
            translationUnit.accept(astVisitorOverride)
            TranslationUnitRegistry.createTranslationUnit()
            val builder = JsonBuilder()
            val newPath = absolutPath.split(OperatingSystem.getSeparator())
            newPath.dropLast(1)
            val fileToWrite = DirReader.createfile("$pathToWrite$dir.json")
            classParser.getDeclarationAndMethod(ASTVisitorOverride.getPrimaryBlock())
            fileToWrite.bufferedWriter().use { out ->
                out.write(builder.createJson(ASTVisitorOverride.getPrimaryBlock()))
            }
            println(builder.createJson(FunctionDefinitionRegistry.list))
            filepath =
                if (bool) {
                    FileSelector.getCppFile()
                } else {
                    FileSelector.getHeaderClassFirst()
                }
        }
        ClassToDeclarationLinker.linkClassDeclarationsToDeclarator()
        println()
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

    private fun parseProject(filesPath: ArrayList<String>, astVisitorOverride: ASTVisitorOverride, absolutPath: String) {

        filesPath.iterator().forEachRemaining { filepath -> run {
            CompositeTypeRegistry.setPath(filepath)
            val dir = filepath.split(absolutPath)[1]
            dir.subSequence(1, dir.length)
            val pathToWrite = DirReader.makedir(OperatingSystem.getSeparator() + dir.subSequence(1, dir.length))
            val data = Reader.readFileAsLinesUsingBufferedReader(filepath)
            val translationUnit: IASTTranslationUnit = getIASTTranslationUnit(data.toCharArray())
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
            translationUnit.accept(astVisitorOverride)
            TranslationUnitRegistry.createTranslationUnit()

            val builder = JsonBuilder()
            val newPath = absolutPath.split(OperatingSystem.getSeparator())
            newPath.dropLast(1)
            val fileToWrite = DirReader.createfile("$pathToWrite$dir.json")

            fileToWrite.bufferedWriter().use { out ->
                out.write(builder.createJson(ASTVisitorOverride.getPrimaryBlock()))
            }
        } }
    }

    fun parseHeaderFiles(absolutPath: String, astVisitorOverride: ASTVisitorOverride) {
        var headerList = DirReader.getHeadersFilesFromProject(absolutPath)
        FileSelector.setListOfPathsParam(headerList)
        parseProject(headerList, astVisitorOverride, absolutPath)
    }
}

