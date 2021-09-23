package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.Unit

class UnitService {
    fun createUnit(): Unit {
        return Unit(null, null)
    }

    fun addNewMethod(unit: Unit, method: Method) {
        if( unit.methods == null) {
            unit.methods = ArrayList()
        }
        unit.methods!!.add(method)
    }
}