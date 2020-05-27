package com.jwoo.callinternet.models

class Recipe() {
    var Title: String? = null
    var Link: String? = null
    var Ingredients: String? = null
    var Thumbnail: String? = null

    constructor(title: String, link: String, ingredients: String, thumbnail: String) : this() {
        Title = title
        Link = link
        Ingredients = ingredients
        Thumbnail = thumbnail
    }
}