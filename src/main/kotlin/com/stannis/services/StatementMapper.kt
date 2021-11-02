package com.stannis.services

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*

class StatementMapper {
    companion object {
        fun addNameDependingOnType(stats: Statement, name: String) {
            when (stats) {
                is While -> {
                    stats.add(name)
                }
                is TypedefStructure -> {
                    stats.add(name)
                }
                is Initialization -> {
                    stats.add(name)
                }
                is FunctionCall -> {
                    stats.add(name)
                }
                is CPPMethodCall -> {
                    stats.add(name)
                }
            }
        }

        fun addFunctionCallDependingOnType(stats: Statement, fc: FunctionCall) {
            when (stats) {
                is CPPMethodCall -> {
                    stats.add(fc)
                }
                is FunctionCall -> {
                    stats.add(fc)
                }
                is If -> {
                    stats.add(fc)
                }
                is Initialization -> {
                    stats.add(fc)
                }
                is Return -> {
                    stats.add(fc)
                }
                is While -> {
                    stats.add(fc)
                }
            }
        }
    }
}