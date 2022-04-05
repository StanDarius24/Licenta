package com.stannis.callHierarchy

import com.stannis.dataModel.complexStatementTypes.ComplexFinalTranslation
import com.stannis.dataModel.complexStatementTypes.FinalTranslation
import com.stannis.dataModel.complexStatementTypes.TranslationWithPath
import com.stannis.parser.sln.VcxprojStructure

object ProjectVcxprojComplexRegistry {

    private lateinit var vcxprojStructure: VcxprojStructure
    private lateinit var filePath: String

    lateinit var parsedList: List<ComplexFinalTranslation>

    fun addFinalTranslation(finalTranslation: FinalTranslation) {
        if (!this::parsedList.isInitialized) {
            parsedList = ArrayList()
            val list = ArrayList<TranslationWithPath>()
            list.add(TranslationWithPath(finalTranslation, filePath))
            (parsedList as ArrayList).add(
                ComplexFinalTranslation(
                    list,
                    vcxprojStructure
                )
            )
        } else {
            var bool1 = false
            parsedList.iterator().forEachRemaining { complexFinalTranslation ->
                run {
                    if (complexFinalTranslation.vcxprojStructure.equals(vcxprojStructure)) {
                        complexFinalTranslation.listOfTranslation.add(
                            TranslationWithPath(
                                finalTranslation,
                                filePath
                            )
                        )
                        bool1 = true
                    }
                }
            }
            if (!bool1) {
                (parsedList as ArrayList).add(
                    ComplexFinalTranslation(
                        listOf(
                            TranslationWithPath(
                                finalTranslation,
                                filePath
                            )
                        ) as ArrayList<TranslationWithPath>, vcxprojStructure
                    )
                )
            }
        }
    }

    fun setVcxProj(valueElement: VcxprojStructure) {
        vcxprojStructure = valueElement
    }

    fun setFilePath(s: String) {
        filePath = s
    }

}