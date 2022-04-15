package com.stannis.linker

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry

object DeclarationLinkToClassService {
    fun link() {
        ProjectVcxprojComplexRegistry.parsedList.forEach { complexFinalTranslation ->
            run {
                complexFinalTranslation.listOfTranslation.forEach { translationWithPath ->
                    run {
                        println()
                    }
                }
            }
        }
    }
}
