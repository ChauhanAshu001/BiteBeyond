package com.nativenomad.bitebeyond.presentation.aiModel

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import javax.inject.Inject
import org.json.JSONObject

@HiltViewModel
class AiViewModel@Inject constructor(
    private val application: Application
):ViewModel() {

    private val _prepTime= MutableStateFlow("")
    val prepTime=_prepTime.asStateFlow()

    private val _cookTime= MutableStateFlow("")
    val cookTime=_cookTime.asStateFlow()

    private val _totalTime= MutableStateFlow("")
    val totalTime=_totalTime.asStateFlow()

    private val _cuisine= MutableStateFlow("")
    val cuisine=_cuisine.asStateFlow()

    private val _course= MutableStateFlow("")
    val course=_course.asStateFlow()

    private val _diet= MutableStateFlow("")
    val diet=_diet.asStateFlow()

    private val _recipeName=MutableStateFlow("")
    val recipeName=_recipeName.asStateFlow()

    private val _uiState=MutableStateFlow<AiScreenEvents>(AiScreenEvents.Nothing)
    val uiState=_uiState.asStateFlow()

    fun setPrepTime(prepTime: String){
        _prepTime.value=prepTime
    }
    fun setCookTime(cookTime: String){
        _cookTime.value=cookTime
    }
    fun setTotalTime(totalTime: String){
        _totalTime.value=totalTime
    }

    fun setCuisine(cuisine: String){
        _cuisine.value=cuisine
    }
    fun setCourse(course: String){
        _course.value=course
    }

    fun setDiet(diet: String){
        _diet.value=diet
    }


    val tfLiteModel = FileUtil.loadMappedFile(application, "food_suggester_model.tflite")
    val interpreter = Interpreter(tfLiteModel)

    val featureCols = JSONArray(
        application.assets.open("feature_columns.json").bufferedReader().use { it.readText() }
    )  //this is a json object

    val labelMap = JSONObject(
        application.assets.open("label_map.json").bufferedReader().use { it.readText() }
    )  //this is also a json object

    fun runModel()  {
        val featureList = mutableListOf<String>()
        for (i in 0 until featureCols.length()) {
            featureList.add(featureCols.getString(i))
        }

        val inputMap = mutableMapOf<String, Float?>()

        val prep = prepTime.value.toFloatOrNull()
        val cook = cookTime.value.toFloatOrNull()
        val total = totalTime.value.toFloatOrNull()
        val cuisine = cuisine.value
        val course = course.value
        val diet = diet.value

        if (prep == null || cook == null || total == null ||
            cuisine.isBlank() || course.isBlank() || diet.isBlank()) {
            _uiState.value=AiScreenEvents.Error
            return
        }

        // Numeric
        inputMap["PrepTimeInMins"] = prep
        inputMap["CookTimeInMins"] = cook
        inputMap["TotalTimeInMins"] = total



        // Categorical - one-hot
        inputMap["Cuisine_$cuisine"] = 1f
        inputMap["Course_$course"] = 1f
        inputMap["Diet_$diet"] = 1f

        // Constructing final input float array in correct order
        val input = featureList.map { featureName ->
            inputMap[featureName] ?: 0f
        }.toFloatArray()

        // Preparing input/output buffers
        val inputBuffer = arrayOf(input)
        val outputBuffer = Array(1) { FloatArray(labelMap.length()) }

        // Running inference
        interpreter.run(inputBuffer, outputBuffer)

        // Getting the predicted index (argmax)
        val scores = outputBuffer[0]
        var maxValue = Float.MIN_VALUE
        var resultIndex = -1

        for (i in scores.indices) {
            if (scores[i] > maxValue) {
                maxValue = scores[i]
                resultIndex = i
            }
        }

        // Getting label from labelMap
        val recipeName = labelMap.getString(resultIndex.toString())

        _recipeName.value= recipeName
    }




}