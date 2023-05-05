package com.example.flowsbasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch() {
            producer().collect {
                Log.d("Flow Values 1", it.toString())
            }
        }

        GlobalScope.launch() {
            val result = producer()
            delay(2500)
            result.collect {
                Log.d("Flow Values 2", it.toString())
            }
        }
    }

    private fun producer() : Flow<Int> {
        val mutableSharedFlow = MutableSharedFlow<Int>(2)
        GlobalScope.launch {
            val list = listOf(1, 2, 3, 4)
            list.forEach {
                mutableSharedFlow.emit(it)
                delay(1000)
            }
        }
        return mutableSharedFlow
    }

}