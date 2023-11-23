package com.example.Yantrik.Model

class User {
    var username:String?=null
    var email:String?=null
    var password:String?=null


    constructor()

    constructor(username: String?, email: String?, password: String?) {
        this.username = username
        this.email = email
        this.password = password
    }

    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }

}