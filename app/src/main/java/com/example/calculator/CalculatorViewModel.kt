package com.example.calculator





import android.media.audiofx.Equalizer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel(){

    private val _equationText = MutableLiveData<String>("")
    val equationText : LiveData<String> get()= _equationText


    private val _resultText = MutableLiveData<String>("0")
    val resultText : LiveData<String>get()= _resultText

fun onButtonClick(btn:String){
    Log.i("Clicked Button",btn)

    _equationText.value?.let {
        if (btn == "AC"){
            _equationText.value = ""
            _resultText.value = "0"
        }

        if (btn == "C"){
            if (it.isNotEmpty()){
                _equationText.value = it.substring(0,it.length-1)
            }
            return
        }
        if(btn == "="){
            _equationText.value = _resultText.value
            return
        }
        _equationText.value = it+btn

        try {
        _resultText.value = calculateResult(_equationText.value.toString())
        }catch (_:Exception){}

//        Log.i("Equation",_equationText.value.toString())
    }
}
fun calculateResult(equation:String):String{
    val context:Context = Context.enter()
    context.optimizationLevel= -1
    val scriptable: Scriptable = context.initStandardObjects()
     var finalResult = context.evaluateString(scriptable,equation,"Javascript",1,null).toString()
    if (finalResult .endsWith(".0")){
        finalResult = finalResult.replace(".0","")
    }
    return  finalResult
}

}