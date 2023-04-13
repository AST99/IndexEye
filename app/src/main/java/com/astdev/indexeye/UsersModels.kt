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

    constructor() {}
    constructor(mail: String, passWrd: String) {
        this.mail = mail
        this.passWrd = passWrd
    }

    constructor(mail: String, name: String, passWrd: String) {
        this.mail = mail
        this.name = name
        this.passWrd = passWrd
    }

}