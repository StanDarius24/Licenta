package com.stannis.services

import com.stannis.dataModel.Class
import com.stannis.dataModel.Method
import com.stannis.dataModel.Unit

class UnitService {

    companion object{
        private lateinit var unitService: UnitService

        fun getInstance(): UnitService {
            if(!::unitService.isInitialized) {
                unitService = UnitService()
            }
            return unitService
        }
    }

    fun createUnit(): Unit {
        return Unit(null, null, null)
    }

    fun addNewMethod(unit: Unit, method: Method) {
        if( unit.methods == null) {
            unit.methods = ArrayList()
        }
        unit.methods!!.add(method)
    }

    fun addClass(unit: Unit, cls: Class) {
        unit.clas = cls
    }
}