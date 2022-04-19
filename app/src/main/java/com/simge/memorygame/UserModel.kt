package com.simge.memorygame

class UserModel{
    var id: Int =0
    var username: String = ""
    var score: String =""
    var time: String =""
    constructor(username:String, score: String, time:String){
        this.username = username
        this.score = score
        this.time = time
    }
    constructor(){

    }
}