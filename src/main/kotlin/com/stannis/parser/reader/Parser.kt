package com.stannis.parser.reader

import com.stannis.json.JsonBuilder
import com.stannis.parser.reader.visitor.ASTVisitorOverride
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage
import org.eclipse.cdt.core.index.IIndex
import org.eclipse.cdt.core.model.ILanguage
import org.eclipse.cdt.core.parser.*

class Parser {
    fun test() {
        val reader = Reader()
        val filesname = DirReader.getAllFilesInResources()
        filesname.iterator().forEachRemaining { filepath ->
            run {
                    var dir = filepath.subSequence(filepath.indexOf("resources"), filepath.length)
                    dir = dir.subSequence(0, dir.lastIndexOf("\\")).toString()
                    DirReader.makedir(dir)
                    val data = reader.readFileAsLinesUsingBufferedReader(filepath)
                    val translationUnit: IASTTranslationUnit = getIASTTranslationUnit(data.toCharArray())

                    val astVisitorOverride = ASTVisitorOverride()
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
                    val builder = JsonBuilder()
                    val fileToWrite = DirReader.createfile(dir + "\\" + filepath.subSequence(filepath.lastIndexOf("\\") + 1, filepath.length).toString())
                    fileToWrite.bufferedWriter().use { out ->
                        out.write(builder.createJson(ASTVisitorOverride.getUnit()))
                    }
                    println(builder.createJson(ASTVisitorOverride.getUnit()))
            }
        }
    }


    private fun getIASTTranslationUnit(code: CharArray) :IASTTranslationUnit {
        val fc = FileContent.create("", code)
        val macroDefinitions :Map<String, String> = HashMap()
        val includeSearchPaths: Array<String> = Array(5) {""}
        val si: IScannerInfo = ScannerInfo(macroDefinitions, includeSearchPaths)
        val ifcp: IncludeFileContentProvider = IncludeFileContentProvider.getEmptyFilesProvider()
        val idx: IIndex? = null
        val options:Int = ILanguage.OPTION_IS_SOURCE_UNIT
        val log: IParserLogService = DefaultLogService()
        return GPPLanguage.getDefault().getASTTranslationUnit(fc, si, ifcp, idx, options, log)
    }
}

