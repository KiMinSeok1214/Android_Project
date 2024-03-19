package com.example.project_last






data class Item(
    var id: Int, // Primary key
    var rest_name: String,
    var isvisit: Int,
    var isdelivery: Int,
    var date: String,
    var rest_star: Double,
    var rest_comment: String,
    var main_image_uri: String,
    var address: String,
    var url: String,
    var isfavor: Int,
    var menu_name: String,
    var menu_star: Double,
    var menu_image_uri: String,
    var menu_comment: String,
    var price: Int,
    var hashtag: String,
    var category1: String,
    var category2: String,
    var category3: String,
    var category4: String
) {
    var selected: Boolean = false
}