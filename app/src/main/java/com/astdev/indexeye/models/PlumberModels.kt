package com.astdev.indexeye.models

class PlumberModels {
    lateinit var mail: String
    lateinit var name: String
    lateinit var passWrd: String
    lateinit var phone: String

    constructor()

    constructor(mail: String, name: String, passWrd: String, phone: String){
        this.mail = mail
        this.name = name
        this.passWrd = passWrd
        this.phone = phone
    }
}