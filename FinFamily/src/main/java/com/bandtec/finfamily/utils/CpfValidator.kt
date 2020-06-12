package com.bandtec.finfamily.utils

object CpfValidator{

    fun isCPF(cpf: String): Boolean {

        var calc: Int
        var num = 10
        var sum = 0

        if (cpf == "00000000000"
            || cpf == "11111111111"
            || cpf == "22222222222"
            || cpf == "33333333333"
            || cpf == "44444444444"
            || cpf == "55555555555"
            || cpf == "66666666666"
            || cpf == "77777777777"
            || cpf == "88888888888"
            || cpf == "99999999999"
            || cpf.length != 11
        ) return false
        for(x in 0..8) {
            calc = cpf[x].toString().toInt() * num
            sum += calc
            --num
        }
        var rest = sum % 11
        var test = 11 - rest
        if(test > 9) test = 0
        if(test != cpf[9].toString().toInt()) return false
        num = 11
        sum = 0
        for(x in 0..9) {
            calc = cpf[x].toString().toInt() * num
            sum += calc
            --num
        }
        rest = sum % 11
        test = 11 - rest
        if(test > 9) test = 0
        if(test != cpf[10].toString().toInt()) return false
        return true
    }
}