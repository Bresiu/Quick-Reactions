package com.example.quickreactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReactionViewModel : ViewModel() {
    private val _reactions = MutableLiveData<List<Triple<String, Int, Boolean>>>()
    val reactions: LiveData<List<Triple<String, Int, Boolean>>> = _reactions

    init {
        parseReactionsCount()
        parseOwnReactions()
        sendUpdate()
    }

    fun addOwnReaction(emojiCode: String) {
        if (ownReactions.contains(emojiCode)) {
            ownReactions.remove(emojiCode)
            reactionsCount[emojiCode]?.let {
                if (it == 1) {
                    reactionsCount.remove(emojiCode)
                } else {
                    reactionsCount[emojiCode] = it.dec()
                }
            }
        } else {
            ownReactions.add(emojiCode)
            reactionsCount[emojiCode] = reactionsCount[emojiCode]?.inc() ?: 1
        }
        sendUpdate()
    }

    fun getSupportedEmojis() = serverSupportedEmojis

    private fun sendUpdate() {
        fun List<Triple<String, Int, Boolean>>.sort() =
            sortedByDescending { (_, _, isOwn) -> isOwn }.sortedByDescending { (_, count, _) -> count }
        _reactions.postValue(
            reactionsCount.map { Triple(it.key, it.value, ownReactions.contains(it.key)) }.sort()
        )
    }

    private fun parseOwnReactions() {
        ownReactions.addAll(serverReactions.asSequence().filter { it.second == ownUser }
            .map { it.first })
    }

    private fun parseReactionsCount() {
        serverReactions.forEach {
            reactionsCount[it.first] = reactionsCount.getOrDefault(it.first, 0).inc()
        }
    }

    companion object {
        private const val ownUser = "Michal"
        private val serverSupportedEmojis = setOf(
            "1F601",
            "1F44D",
            "1F44E",
            "1F44F",
            "1F440",
            "2705",
            "274C",
            "1F680",
            "1F620",
            "2764"
        )
        private val serverReactions = mutableListOf(
            "1F601" to "Michal",
            "1F601" to "Anna",
            "1F601" to "Jan",
            "2764" to "Michal",
            "2764" to "Mooncake",
            "1F44D" to "Gary",
            "1F680" to "Anna",
            "274C" to "Monika",
            "1F620" to "Ola",
        )
        private val reactionsCount = mutableMapOf<String, Int>()
        private val ownReactions = mutableSetOf<String>()
    }
}