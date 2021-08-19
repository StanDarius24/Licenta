package com.stannis.parser.reader

import com.sun.xml.internal.fastinfoset.util.StringArray
import org.eclipse.cdt.core.dom.ast.ASTVisitor
import org.eclipse.cdt.core.dom.ast.ASTVisitor.PROCESS_CONTINUE
import org.eclipse.cdt.core.dom.ast.IASTDeclaration
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage
import org.eclipse.cdt.core.index.IIndex
import org.eclipse.cdt.core.model.ILanguage
import org.eclipse.cdt.core.parser.*

class Parser {
    fun test() {
        val reader = Reader()
        val data = reader.readFileAsLinesUsingBufferedReader("C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\main.cpp")
        var translationUnit: IASTTranslationUnit = getIASTTranslationUnit(data.toCharArray())

        val visitor: ASTVisitor = ASTVisitor() {
           @Override
           fun visit(declaration: IASTDeclaration): Int {
               println("Found a declaration: " + declaration.rawSignature)
               return PROCESS_CONTINUE
           }
        }
    }


    fun getIASTTranslationUnit(code: CharArray) :IASTTranslationUnit {
        val fc = FileContent.create("", code)
        var macroDefinitions :Map<String, String> = HashMap()
        var includeSearchPaths: Array<String> = Array(5) {""}
        val si: IScannerInfo = ScannerInfo(macroDefinitions, includeSearchPaths)
        val ifcp: IncludeFileContentProvider = IncludeFileContentProvider.getEmptyFilesProvider()
        val idx: IIndex? = null
        val options:Int = ILanguage.OPTION_IS_SOURCE_UNIT
        val log: IParserLogService = DefaultLogService()
        return GPPLanguage.getDefault().getASTTranslationUnit(fc, si, ifcp, idx, options, log)
    }
}

