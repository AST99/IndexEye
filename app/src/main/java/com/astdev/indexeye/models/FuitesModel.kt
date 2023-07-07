package com.astdev.indexeye.models

class FuitesModel {
    lateinit var nomPrenom: String
    lateinit var phone: String
    lateinit var atHome: String
    lateinit var description: String
    lateinit var quartier: String
    lateinit var position: String

    constructor() {}
    constructor(position: String, atHome: String, description: String,
                nomPrenom: String, phone: String, quartier: String) {
        this.position = position
        this.atHome = atHome
        this.description = description
        this.nomPrenom = nomPrenom
        this.phone = phone
        this.quartier = quartier
    }

}