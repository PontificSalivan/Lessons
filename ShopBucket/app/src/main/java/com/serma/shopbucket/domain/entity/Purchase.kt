package com.serma.shopbucket.domain.entity

data class Purchase(
    val name: String = "",
    val progress: Int = 0,
    val total: Int = 0,
    val shoppingList: MutableList<Shopping> = mutableListOf()
)
