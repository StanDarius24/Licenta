package com.stannis.callHierarchy

import com.stannis.dataModel.complexStatementTypes.RepositoryModel
import com.stannis.dataModel.complexStatementTypes.ClassOrHeader
import com.stannis.dataModel.complexStatementTypes.ClassOrHeaderWithPath
import com.stannis.parser.sln.VcxprojStructure

object ProjectVcxprojComplexRegistry {

    private lateinit var vcxprojStructure: VcxprojStructure
    private lateinit var filePath: String

    lateinit var parsedFiles: List<RepositoryModel>

    fun addFinalTranslation(classOrHeader: ClassOrHeader, boolean: Boolean) {
        if (!this::parsedFiles.isInitialized) {
            parsedFiles = ArrayList()
            val list = ArrayList<ClassOrHeaderWithPath>()
            list.add(ClassOrHeaderWithPath(classOrHeader = classOrHeader, path = filePath))
            (parsedFiles as ArrayList).add(RepositoryModel(listOfHeaderFiles = list, listOfCppFiles = ArrayList(), vcxprojStructure = vcxprojStructure))
        } else {
            var bool1 = false
            parsedFiles.iterator().forEachRemaining { complexFinalTranslation ->
                run {
                    if (complexFinalTranslation.vcxprojStructure.equals(vcxprojStructure)) {
                        val classOrHeaderWithPath = ClassOrHeaderWithPath(classOrHeader = classOrHeader, path = filePath)
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
                                complexFinalTranslation.listOfHeaderFiles.add(classOrHeaderWithPath)
                            } else {
                                complexFinalTranslation.listOfCppFiles!!.add(classOrHeaderWithPath)
                            }
                        }
                        bool1 = true
                    }
                }
            }
            if (!bool1) {
                val listx = ArrayList<ClassOrHeaderWithPath>()
                listx.add(ClassOrHeaderWithPath(classOrHeader = classOrHeader, path = filePath))
                (parsedFiles as ArrayList).add(
                    RepositoryModel(listOfHeaderFiles = listx, listOfCppFiles = ArrayList(), vcxprojStructure = vcxprojStructure)
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
