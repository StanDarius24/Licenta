package com.stannis.callHierarchy

import com.stannis.dataModel.complexStatementTypes.ComplexFinalTranslation
import com.stannis.dataModel.complexStatementTypes.FinalTranslation
import com.stannis.dataModel.complexStatementTypes.TranslationWithPath
import com.stannis.parser.sln.VcxprojStructure

object ProjectVcxprojComplexRegistry {

    private lateinit var vcxprojStructure: VcxprojStructure
    private lateinit var filePath: String

    lateinit var parsedFiles: List<ComplexFinalTranslation>

    fun addFinalTranslation(finalTranslation: FinalTranslation, boolean: Boolean) {
        if (!this::parsedFiles.isInitialized) {
            parsedFiles = ArrayList()
            val list = ArrayList<TranslationWithPath>()
            list.add(TranslationWithPath(finalTranslation, filePath))
            (parsedFiles as ArrayList).add(ComplexFinalTranslation(list, ArrayList(), vcxprojStructure))
        } else {
            var bool1 = false
            parsedFiles.iterator().forEachRemaining { complexFinalTranslation ->
                run {
                    if (complexFinalTranslation.vcxprojStructure.equals(vcxprojStructure)) {
                        val translationWithPath = TranslationWithPath(finalTranslation, filePath)
                        var bool2 = false
                        complexFinalTranslation.listOfHeaderFiles!!.iterator().forEachRemaining {
                            translationWParent ->
                            run {
                                if (translationWParent.path == filePath) {
                                    bool2 = true
                                }
                            }
                        }
                        if (!bool2) {
                            if (boolean) {
                                complexFinalTranslation.listOfHeaderFiles.add(translationWithPath)
                            } else {
                                complexFinalTranslation.listOfCppFiles!!.add(translationWithPath)
                            }
                        }
                        bool1 = true
                    }
                }
            }
            if (!bool1) {
                val listx = ArrayList<TranslationWithPath>()
                listx.add(TranslationWithPath(finalTranslation, filePath))
                (parsedFiles as ArrayList).add(
                    ComplexFinalTranslation(listx, ArrayList(), vcxprojStructure)
                )
            }
        }
        println()
    }

    fun setVcxProj(valueElement: VcxprojStructure) {
        vcxprojStructure = valueElement
    }

    fun setFilePath(s: String) {
        filePath = s
    }
}
