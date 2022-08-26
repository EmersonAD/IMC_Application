package com.souzaemerson.imcapplication.ui.home.viewmodel

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souzaemerson.imcapplication.R
import com.souzaemerson.imcapplication.core.State
import com.souzaemerson.imcapplication.data.util.*
import com.souzaemerson.imcapplication.repository.ImcRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ImcRepository
) : ViewModel() {

    private val _imcBodyTypeResult = MutableLiveData<State<String>>()
    val imcBodyTypeResult: LiveData<State<String>> get() = _imcBodyTypeResult

    private val _heightFieldErrorResId = MutableLiveData<Int?>()
    val heightFieldErrorResId: LiveData<Int?> = _heightFieldErrorResId

    private val _weightFieldErrorResId = MutableLiveData<Int?>()
    val weightFieldErrorResId: LiveData<Int?> = _weightFieldErrorResId

    fun calculateImcBodyType(height: String, weight: String) {
        viewModelScope.launch {
            _heightFieldErrorResId.value = getErrorStringResIdEmptyHeight(height)
            _weightFieldErrorResId.value = getErrorStringResIdEmptyWeight(weight)

            try {
                if (height.isNotEmpty() && weight.isNotEmpty()) {
                    repository.calculateImc(height.toDouble(), weight.toDouble()).let { bodyType ->
                        _imcBodyTypeResult.value = State.success(bodyType)
                    }
                }
            } catch (e: Exception) {
                _imcBodyTypeResult.value = State.error(e)
            }
        }
    }

    fun setImageBodyType(body: String, text: AppCompatTextView, image: AppCompatImageView) {
        text.text = body

            when(body) {
                UNDER_WEIGHT_BODY_TYPE -> image.setImageResource(R.drawable.magro)
                NORMAL_BODY_TYPE -> image.setImageResource(R.drawable.normal)
                OVER_WEIGHT_BODY_TYPE -> image.setImageResource(R.drawable.sobrepeso)
                OBESITY_ONE_BODY_TYPE -> image.setImageResource(R.drawable.obsidadeg1)
                OBESITY_TWO_BODY_TYPE -> image.setImageResource(R.drawable.obesidadeg2)
                OBESITY_THREE_BODY_TYPE -> image.setImageResource(R.drawable.obesidadeg3)
                else -> ERROR_TO_CALCULATE_BODY_TYPE
            }
    }

    private fun getErrorStringResIdEmptyHeight(value: String): Int? =
        if (value.isEmpty()) R.string.empty_field_height else null

    private fun getErrorStringResIdEmptyWeight(value: String): Int? =
        if (value.isEmpty()) R.string.empty_field_weight else null
}