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

    fun justDoSmth() {
        val reader = Reader()
        val astVisitorOverride = ASTVisitorOverride()
        FileSelector.setListOfPathsParam(DirReader.getAllFilesInResources())
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
            var dir = filepath.subSequence(filepath.indexOf("resources"), filepath.length)
            dir = dir.subSequence(0, dir.lastIndexOf("\\")).toString()
            //                    dir = dir.subSequence(0, dir.lastIndexOf("/")).toString()
            DirReader.makedir(dir)
            val data = reader.readFileAsLinesUsingBufferedReader(filepath)
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
            val extension = filepath.subSequence(filepath.lastIndexOf("."), filepath.length)
            translationUnit.accept(astVisitorOverride)
            TranslationUnitRegistry.createTranslationUnit()
            val builder = JsonBuilder()
            val fileToWrite =
                DirReader.createfile(
                    dir +
                        "\\" +
                        filepath
                            .subSequence(filepath.lastIndexOf("\\") + 1, filepath.length)
                            .toString() +
                        extension
                )
            //                    val fileToWrite = DirReader.createfile(dir + "/" +
            // filepath.subSequence(filepath.lastIndexOf("/") + 1, filepath.length).toString() +
            // extension)
            classParser.getDeclarationAndMethod(ASTVisitorOverride.getPrimaryBlock())
            fileToWrite.bufferedWriter().use { out ->
                out.write(builder.createJson(ASTVisitorOverride.getPrimaryBlock()))
            }
            println(builder.createJson(FunctionDefinitionRegistry.list))
            //                println(ExceptionHandler.mapOfProblemStatement)
            filepath = if (bool) {
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
}

