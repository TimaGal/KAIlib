package com.example.kailibrary.UseCases

class PassValidator {
    fun execute(pass: String): ArrayList<Boolean>{
        var acceptList: ArrayList<Boolean> = arrayListOf()
        val p1 = Regex("[A-Z]{1,100}")
        //есть ли цифпы
        val p2 = Regex("[0-9]{1,100}")
        //есть ли другие симвлоы
        val p3 = Regex("[!@#$%&*]{1,100}")

        if(pass.length < 8){
            acceptList.add(false)
        }else{
            acceptList.add(true)
        }

        if(!p1.containsMatchIn(pass)){
            acceptList.add(false)
        }else{
            acceptList.add(true)
        }

        if(!p2.containsMatchIn(pass)){
            acceptList.add(false)
        }else{
            acceptList.add(true)
        }

        if(!p3.containsMatchIn(pass)){
            acceptList.add(false)
        }else{
            acceptList.add(true)
        }
        return acceptList
    }
}