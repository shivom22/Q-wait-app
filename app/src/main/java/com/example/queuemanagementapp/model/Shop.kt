package com.example.queuemanagementapp.model

data class Shop(
    val Address: String,
    val ShopCounter: List<Int>,
    val __v: Int,
    val _id: String,
    val avgtime: List<Double>,
    val counter: Int,
    val countertime: List<Int>,
    val latti: Double,
    val long: Double,
    val name: String,
    val queue: List<Queue>
)