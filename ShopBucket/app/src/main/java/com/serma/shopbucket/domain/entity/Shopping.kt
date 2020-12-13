package com.serma.shopbucket.domain.entity

import java.io.Serializable

data class Shopping(val name: String, val count: Int = 0, val price: Int = 0) :
    Serializable
