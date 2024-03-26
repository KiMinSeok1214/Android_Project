package com.example.project_last

data class Restaurent(
    var rest_name:String,
    var recent_star:Float,
    var comment:String,
    var image_uri:String,
    var isdelivery: Int,
    var isvisit: Int,
    var isfavor: Int
)

data class Diary(
    var date:String,
    var rest_star:Float,
    var rest_comment: String,
    var hashtag: ArrayList<String>,
    var isdelivery: Int,
    var isvisit: Int,
    var menuList: ArrayList<String>
)
