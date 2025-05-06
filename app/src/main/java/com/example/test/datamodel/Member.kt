package com.example.test.datamodel

data class Member(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val rank: Int,
    var isSelected: Boolean,
    val team: List<Member> = emptyList(),
)