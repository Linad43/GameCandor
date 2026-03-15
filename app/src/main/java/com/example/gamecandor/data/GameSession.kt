package com.example.gamecandor.data

import android.content.Context

object GameSession{
    var currentGame: String = ""
    fun saveCurrentGame(context: Context) {
        // Здесь мы можем вызвать GameRepository.save, но так как markCardPlayed уже сохраняет,
        // нужно убедиться, что последняя сессия записана. Для примера:
        val played = GameRepository.getPlayedCards(context, currentGame)
        GameRepository.markCardPlayed(context, currentGame, -1) // фиктивный вызов, только для save
    }
}