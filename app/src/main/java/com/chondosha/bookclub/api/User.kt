package com.chondosha.bookclub.api

data class User(
    val id: String,
    val email: String,
    val username: String,
    val groupList: List<Group>,
    val friendList: List<User>
)