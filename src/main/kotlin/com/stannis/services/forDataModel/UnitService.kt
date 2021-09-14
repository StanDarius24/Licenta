package com.stannis.services.forDataModel

import com.stannis.dataModel.Method

class UnitService {
    private var method = ArrayList<Method>()

    fun addMethod(method: Method) {
        this.method.add(method)
    }

    fun getUnit() : ArrayList<Method> {
        return this.method
    }
}