package com.astdev.indexeye

class UsersModels {
    private var mail: String = ""
        // getter
        get() = field

        // setter
        set(value) {
            field = value
        }

    private var name: String = ""
        // getter
        get() = field

        // setter
        set(value) {
            field = value
        }

    private var passWrd: String = ""
        // getter
        get() = field

        // setter
        set(value) {
            field = value
        }

    private var phone: String = ""
        get() = field
        set(value) {
            field = value
        }

    private var deviceId: String = ""
        get() = field
        set(value) {
            field = value
        }

    constructor(mail: String, name: String, passWrd: String, phone: String) {
        this.mail = mail
        this.name = name
        this.passWrd = passWrd
        this.phone = phone
    }

    constructor(mail: String, name: String, passWrd: String, phone: String, deviceId: String) {
        this.mail = mail
        this.name = name
        this.passWrd = passWrd
        this.phone = phone
        this.deviceId = deviceId
    }

}