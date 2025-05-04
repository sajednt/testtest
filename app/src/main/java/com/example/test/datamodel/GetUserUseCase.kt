package com.example.test.datamodel

import com.example.test.R
import kotlin.random.Random

class GetUserUseCase {

    operator fun invoke(): User {
        return User(
            id = "1",
            name = "نازنین رضایی",
            rank = 5,
            image = R.drawable.image_user,
            team = generateMockMembers()
        )
    }

    private fun generateMockMembers(count: Int = 20, indentLevel: Int = 1): List<Member> {
        val firstNames = listOf(
            "John",
            "Jane",
            "Mike",
            "Emily",
            "Alex",
            "Sophia",
            "Liam",
            "Olivia",
            "Noah",
            "Ava"
        )
        val lastNames = listOf(
            "Doe",
            "Smith",
            "Brown",
            "White",
            "Green",
            "Black",
            "Taylor",
            "King",
            "Hill",
            "Baker"
        )

        return List(count) { index ->
            val id = index + 1
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val username = "${firstName}_${lastName}${Random.nextInt(100)}".lowercase()

            Member(
                id = id,
                username = username,
                firstName = firstName,
                lastName = lastName,
                rank = Random.nextInt(0, 6),
                team = List(Random.nextInt(0, 10)) { subIndex ->
                    Member(
                        id = (count + id * 10 + subIndex),
                        username = "${firstName.lowercase()}_sub${subIndex}",
                        firstName = firstNames.random(),
                        lastName = lastNames.random(),
                        rank = Random.nextInt(0, 6),
                        team = if (indentLevel < 4) {
                            generateMockMembers(
                                Random.nextInt(0, 6),
                                indentLevel + 1
                            )
                        } else {
                            emptyList()
                        }
                    )
                }
            )
        }
    }
}