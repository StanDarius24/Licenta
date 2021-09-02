package com.stannis.parser.reader

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage
import org.eclipse.cdt.core.index.IIndex
import org.eclipse.cdt.core.model.ILanguage
import org.eclipse.cdt.core.parser.*

class Parser {
    fun test() {
        val reader = Reader()
        val data = reader.readFileAsLinesUsingBufferedReader("C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\main.cpp")
        val translationUnit: IASTTranslationUnit = getIASTTranslationUnit(data.toCharArray())

        val astVisitorOverride = ASTVisitorOverride()
        astVisitorOverride.shouldVisitDeclarations = true
        translationUnit.accept(astVisitorOverride)
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

