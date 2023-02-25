package com.example.win35

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.win35.model.Nba
import com.example.win35.model.NbaModel
import com.onesignal.OneSignal
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class QuizFragment : Fragment() {

    var listNbaPlayers = ArrayList<Nba>()
    var position = 0
    lateinit var nController: NavController
    var result = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button1: AppCompatButton = requireActivity().findViewById(R.id.btn_one)
        val button2: AppCompatButton = requireActivity().findViewById(R.id.btn_two)
        val button3: AppCompatButton = requireActivity().findViewById(R.id.btn_three)
        val button4: AppCompatButton = requireActivity().findViewById(R.id.btn_four)
        val buttonNext: AppCompatButton = requireActivity().findViewById(R.id.btn_next)
        val textViewQuestion = requireActivity().findViewById<TextView>(R.id.textView_question)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(requireContext())
        OneSignal.setAppId("714b9f14-381d-4fc4-a93c-28d480557381")
        buttonNext.isEnabled = false
        nController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        initialisationButtons(button1, button2, button3, button4, textViewQuestion, position, buttonNext)
        buttonNext.setOnClickListener {
            if (listNbaPlayers.size==position){
                val bundle = bundleOf("result" to result)
                nController.navigate(R.id.action_quizFragment_to_resultFragment, bundle)
            } else {
                initialisationButtons(button1, button2, button3, button4, textViewQuestion, position, buttonNext)
            }
        }

    }

    private fun initialisationButtons(
        button1: AppCompatButton?,
        button2: AppCompatButton?,
        button3: AppCompatButton?,
        button4: AppCompatButton?,
        textViewQuestion: TextView,
        _position: Int,
        buttonNext: AppCompatButton
    ) {
        listNbaPlayers.clear()
        position+=1
        button1?.setBackgroundResource(R.drawable.round_corner)
        button2?.setBackgroundResource(R.drawable.round_corner)
        button3?.setBackgroundResource(R.drawable.round_corner)
        button4?.setBackgroundResource(R.drawable.round_corner)
        button1?.isEnabled = true
        button2?.isEnabled = true
        button3?.isEnabled = true
        button4?.isEnabled = true
        buttonNext.isEnabled = false
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl("http://49.12.202.175/win35/")
            .build()
        val nbaApi = retrofitBuilder.create(ApiNBA::class.java)
        val data = nbaApi.getNbaPlayers()
        data.enqueue(object : Callback<NbaModel>{
            override fun onResponse(call: Call<NbaModel>, response: Response<NbaModel>) {
                listNbaPlayers.addAll(response.body()!!.nba)
                textViewQuestion.text = listNbaPlayers[_position].question
                button1?.text = listNbaPlayers[_position].ans1.name
                button2?.text = listNbaPlayers[_position].ans2.name
                button3?.text = listNbaPlayers[_position].ans3.name
                button4?.text = listNbaPlayers[_position].ans4.name
                button1?.setOnClickListener {
                    button1.isEnabled = false
                    button2?.isEnabled = false
                    button3?.isEnabled = false
                    button4?.isEnabled = false
                    buttonNext.isEnabled = true
                    if (listNbaPlayers[_position].ans1.result=="true"){
                        button1.setBackgroundResource(R.drawable.round_win)
                        result+=1
                    } else {
                        button1.setBackgroundResource(R.drawable.round_loss)
                    }
                }
                button2?.setOnClickListener {
                    button1?.isEnabled = false
                    button2.isEnabled = false
                    button3?.isEnabled = false
                    button4?.isEnabled = false
                    buttonNext.isEnabled = true
                    if (listNbaPlayers[_position].ans2.result=="true"){
                        button2.setBackgroundResource(R.drawable.round_win)
                        result+=1
                    } else {
                        button2.setBackgroundResource(R.drawable.round_loss)
                    }
                }
                button3?.setOnClickListener {
                    button1?.isEnabled = false
                    button2?.isEnabled = false
                    button3.isEnabled = false
                    button4?.isEnabled = false
                    buttonNext.isEnabled = true
                    if (listNbaPlayers[_position].ans3.result=="true"){
                        button3.setBackgroundResource(R.drawable.round_win)
                        result+=1
                    } else {
                        button3.setBackgroundResource(R.drawable.round_loss)
                    }
                }
                button4?.setOnClickListener {
                    button1?.isEnabled = false
                    button2?.isEnabled = false
                    button3?.isEnabled = false
                    button4.isEnabled = false
                    buttonNext.isEnabled = true
                    if (listNbaPlayers[_position].ans4.result=="true"){
                        button4.setBackgroundResource(R.drawable.round_win)
                        result+=1
                    } else {
                        button4.setBackgroundResource(R.drawable.round_loss)
                    }
                }
            }


            override fun onFailure(call: Call<NbaModel>, t: Throwable) {}
        })
    }
}