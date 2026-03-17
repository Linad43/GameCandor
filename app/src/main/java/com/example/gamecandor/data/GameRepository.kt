package com.example.gamecandor.data

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object GameRepository {
    private const val FILE_NAME = "games.json"
    private var saves:GameSaveList = GameSaveList()
    private val json = Json { prettyPrint = true }

    // Получить список всех игр
    fun getGames(context: Context): List<String> {
//        val saves = load(context)
        return saves.games.map { it.name }
    }

    // Создать новую игру
    fun createGame(context: Context, name: String) {
//        val saves = load(context)
        if (saves.games.none { it.name == name }) {
            saves.games.add(GameSave(name))
            save(context, saves)
        }
    }

    // Получить сыгранные карты конкретной игры
    fun getPlayedCards(context: Context, game: String): Set<Int> {
//        val saves = load(context)
        return saves.games.find { it.name == game }?.playedCards ?: emptySet()
    }

    // Отметить карту сыгранной
    fun markCardPlayed(context: Context, game: String, cardId: Int) {
//        val saves = load(context)
        val gameSave = saves.games.find { it.name == game }
        if (gameSave != null) {
            val updated = gameSave.playedCards.toMutableSet()
            updated.add(cardId)
            val index = saves.games.indexOf(gameSave)
            saves.games[index] = gameSave.copy(playedCards = updated)
            save(context, saves)
        }
    }

    // Вспомогательные функции для работы с файлом
    fun load(context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        saves = if (file.exists()) {
            val text = file.readText()
            json.decodeFromString(text)
        } else {
            GameSaveList()
        }
    }

    private fun save(context: Context, saves: GameSaveList) {
        val file = File(context.filesDir, FILE_NAME)
        file.writeText(json.encodeToString(saves))
    }

    fun deleteGame(context: Context, name: String) {
        saves.games.removeAll { it.name == name }
        save(context, saves)
    }
}