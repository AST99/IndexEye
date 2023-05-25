package com.astdev.indexeye

class UsersModels {
    var mail: String = ""

        // getter
        get() = field

        // setter
        set(value) {
            field = value
        }

    var name: String = ""
        // getter
        get() = field

        // setter
        set(value) {
            field = value
        }

    var passWrd: String = ""
        // getter
        get() = field

        // setter
        set(value) {
            field = value
        }

    var phone: String = ""
        get() = field
        set(value) {
            field = value
        }

    var deviceId: String = ""
        get() = field
        set(value) {
            field = value
        }

    constructor() {}
    constructor(mail: String, passWrd: String) {
        this.mail = mail
        this.passWrd = passWrd
    }

    constructor(mail: String, name: String, passWrd: String, phone: String, deviceId: String) {
        this.mail = mail
        this.name = name
        this.passWrd = passWrd
        this.phone = phone
        this.deviceId = deviceId
    }

}