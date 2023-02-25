package com.example.win35

import com.example.win35.model.NbaModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET

interface ApiNBA {
    @GET("nbaPlayers.json")
    fun getNbaPlayers():Call<NbaModel>
    @GET("triviachallenge.json")
    fun getTriviaChallenge(): Observable<NbaModel>
}