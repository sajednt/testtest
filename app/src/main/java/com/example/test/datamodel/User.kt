package com.example.test.datamodel

import androidx.annotation.DrawableRes

data class User(
    val id: String,
    val name: String,
    val rank: Int,
    @DrawableRes val image: Int,
    val team: List<Member> = emptyList()
)