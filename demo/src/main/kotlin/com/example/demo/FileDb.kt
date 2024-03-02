package com.example.demo

import java.sql.DriverManager

object FileDb {
    fun create() {
        val createTable = """
            CREATE TABLE IF NOT EXISTS Files (
                name VARCHAR(255) PRIMARY KEY,
                type VARCHAR(255),
                data VARCHAR(1000)
            )
        """.trimIndent()

        DriverManager.getConnection("jdbc:sqlite:storage.db").use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(createTable)
            }
        }
    }

    fun insert(file: File) {


        DriverManager.getConnection("jdbc:sqlite:storage.db").use { connection ->
            connection.prepareStatement(
                "INSERT INTO Files" +
                        "(name, type, data) VALUES (?, ?, ?)"
            ).use { preparedStatement ->
                preparedStatement.setString(1, file.name)
                preparedStatement.setString(2, file.type)
                preparedStatement.setString(3, file.data)
                preparedStatement.executeUpdate()

            }
        }
    }

    fun get(name: String) : File? {
        DriverManager.getConnection("jdbc:sqlite:storage.db").use { connection ->
            connection.prepareStatement(
                "SELECT * FROM Files WHERE name= ?"
            ).use { preparedStatement ->
                preparedStatement.setString(1, name)
                val res = preparedStatement.executeQuery()
                if (res.next()) {
                    return File(res.getString("name"), res.getString("type"), res.getString("data"))
                } else {
                    return null
                }
            }
        }
    }
}