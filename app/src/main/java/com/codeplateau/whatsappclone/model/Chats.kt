package com.codeplateau.whatsappclone.model

class Chats {

    var phone:String?=null
    var status:String?=null
    var username:String?=null
    var userId:String?=null
    var userImg:String? = null

    constructor(){}

    constructor(phone:String?, status:String?, username:String?, userId:String?, userImg:String){
        this.phone = phone
        this.status = status
        this.username = username
        this.userId = userId
        this.userImg = userImg
    }
}