package com.example.gamecandor.data

import android.content.Context
import com.example.gamecandor.model.Card
import com.example.gamecandor.model.Category
import org.json.JSONObject

object JsonLoader {
    fun loadCards(context: Context): List<Card> {
        val jsonString =
            context.assets.open("cards.json")
                .bufferedReader()
                .use { it.readText() }
        val json = JSONObject(jsonString)
        val cardsArray = json.getJSONArray("cards")
        val cards = mutableListOf<Card>()

        for (i in 0 until cardsArray.length()) {
            val obj = cardsArray.getJSONObject(i)
            val id = obj.getInt("id")
            val category =
                Category.valueOf(obj.getString("category"))
            val questionsArray = obj.getJSONArray("questions")
            val questions = mutableListOf<Int>()
            for (j in 0 until questionsArray.length()) {
                questions.add(questionsArray.getInt(j))
            }
            cards.add(
                Card(
                    id,
                    category,
                    questions
                )
            )
        }

        return cards
//        return context.assets.open("cards.json").bufferedReader().use { it.readText() }
    }
}