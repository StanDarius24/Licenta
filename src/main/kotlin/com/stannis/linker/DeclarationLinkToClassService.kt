package com.stannis.linker

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry

object DeclarationLinkToClassService {
    fun link() {
        ProjectVcxprojComplexRegistry.parsedFiles.forEach { complexFinalTranslation ->
            run {
                complexFinalTranslation.listOfHeaderFiles!!.forEach { translationWithPath ->
                    run {
                        println()
                    }
                }
            }
        }
    }
}
