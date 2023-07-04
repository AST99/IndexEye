package com.astdev.indexeye.models

class UsersModels {

    lateinit var mail: String
    lateinit var name: String
    lateinit var passWrd: String
    lateinit var phone: String
    lateinit var deviceId: String
    var type: String = ""

    constructor()

    constructor(mail: String, name: String, passWrd: String, phone: String, deviceId: String) {
        type = "particular"
        this.mail = mail
        this.name = name
        this.passWrd = passWrd
        this.phone = phone
        this.deviceId = deviceId
    }

    constructor(mail: String, name: String, passWrd: String, phone: String){
        type = "plumber"
        this.mail = mail
        this.name = name
        this.passWrd = passWrd
        this.phone = phone
    }

}