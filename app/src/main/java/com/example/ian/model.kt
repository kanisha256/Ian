package com.example.ian

class model {
    var name: String? = null
    var price: String? = null
    var date: String? = null
    var img: Int? = null


    constructor() {}
    constructor(name: String?, price: String?,date: String?,img: Int?) {
        this.name = name
        this.price = price
        this.date = date
        this.img = img
    }
}