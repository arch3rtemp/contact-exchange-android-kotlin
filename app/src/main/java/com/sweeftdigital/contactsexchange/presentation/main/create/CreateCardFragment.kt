package com.sweeftdigital.contactsexchange.presentation.main.create

import android.animation.ValueAnimator
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.sweeftdigital.contactsexchange.R
import com.sweeftdigital.contactsexchange.databinding.FragmentCardCreateBinding
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.presentation.base.BaseFragment
import com.sweeftdigital.contactsexchange.util.Constants.MY_CARD
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCardFragment :
    BaseFragment<CreateCardEvent, CreateCardEffect, CreateCardState, FragmentCardCreateBinding, CreateCardViewModel>() {


    override val viewModel: CreateCardViewModel by viewModel()
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardCreateBinding
        get() = FragmentCardCreateBinding::inflate

    private var currentColor: Int = R.color.light_navy
    private lateinit var cardBackground: Drawable

    override fun renderState(state: CreateCardState) {
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {
        initCard()
        setListeners()
    }

    private fun initCard() {
        with(binding) {
            tvColorLightNavy.setBackgroundResource(R.drawable.shape_selected_card_color_light_navy_bg)
            cardBackground = clCreate.background
        }
    }

    private fun setListeners() {
        with(binding) {
            tvColorLightNavy.setOnClickListener {
                defaultColorsView()
                tvColorLightNavy.setBackgroundResource(R.drawable.shape_selected_card_color_light_navy_bg)
                setBackgroundColorWithAnimation(currentColor, R.color.light_navy)
            }
            tvColorAquaMarine.setOnClickListener {
                defaultColorsView()
                tvColorAquaMarine.setBackgroundResource(R.drawable.shape_selected_card_color_aqua_marine_bg)
                setBackgroundColorWithAnimation(currentColor, R.color.aqua_marine)
            }
            tvColorUglyYellow.setOnClickListener {
                defaultColorsView()
                tvColorUglyYellow.setBackgroundResource(R.drawable.shape_selected_card_color_ugly_yellow_bg)
                setBackgroundColorWithAnimation(currentColor, R.color.ugly_yellow)
            }
            tvColorShamrockGreen.setOnClickListener {
                defaultColorsView()
                tvColorShamrockGreen.setBackgroundResource(R.drawable.shape_selected_card_color_shamrock_green_bg)
                setBackgroundColorWithAnimation(currentColor, R.color.shamrock_green)
            }
            tvColorBlackThree.setOnClickListener {
                defaultColorsView()
                tvColorBlackThree.setBackgroundResource(R.drawable.shape_selected_card_color_black_bg)
                setBackgroundColorWithAnimation(currentColor, R.color.black_three)
            }
            tvColorPumpkin.setOnClickListener {
                defaultColorsView()
                tvColorPumpkin.setBackgroundResource(R.drawable.shape_selected_card_color_pumpkin_bg)
                setBackgroundColorWithAnimation(currentColor, R.color.pumpkin)
            }
            tvColorDarkishPurple.setOnClickListener {
                defaultColorsView()
                tvColorDarkishPurple.setBackgroundResource(R.drawable.shape_selected_card_color_darkish_purple_bg)
                setBackgroundColorWithAnimation(currentColor, R.color.darkish_purple)
            }
            btnCreate.setOnClickListener {
                val contact = getDataFromFields()
                viewModel.setEvent(CreateCardEvent.OnCreateButtonPressed(contact))
            }
        }
    }

    private fun setBackgroundColorWithAnimation(@ColorRes start: Int, @ColorRes end: Int) {
        val startColor = getColor(start)
        val endColor = getColor(end)
        val valueAnimator = ValueAnimator.ofArgb(startColor, endColor)
        valueAnimator.duration = 200
        valueAnimator.addUpdateListener {
            val animatedColor = it.animatedValue.toString().toInt()
            cardBackground.colorFilter =
                PorterDuffColorFilter(animatedColor, PorterDuff.Mode.SRC_IN)
        }
        currentColor = end
        valueAnimator.start()
    }

    private fun getColor(@ColorRes resourceColor: Int): Int {
        return ContextCompat.getColor(requireContext(), resourceColor)
    }

    private fun defaultColorsView() {
        with(binding) {
            tvColorLightNavy.setBackgroundResource(R.drawable.shape_default_card_color_light_navy_bg)
            tvColorAquaMarine.setBackgroundResource(R.drawable.shape_default_card_color_aqua_marine_bg)
            tvColorUglyYellow.setBackgroundResource(R.drawable.shape_default_card_color_ugly_yellow_bg)
            tvColorShamrockGreen.setBackgroundResource(R.drawable.shape_default_card_color_shamrock_green_bg)
            tvColorBlackThree.setBackgroundResource(R.drawable.shape_default_card_color_black_bg)
            tvColorPumpkin.setBackgroundResource(R.drawable.shape_default_card_color_pumpkin_bg)
            tvColorDarkishPurple.setBackgroundResource(R.drawable.shape_default_card_color_darkish_purple_bg)
        }
    }

    private fun getDataFromFields(): Contact {
        return Contact(
            0,
            binding.etFullName.text.toString(),
            binding.etCompany.text.toString(),
            binding.etPosition.text.toString(),
            binding.etEmail.text.toString(),
            binding.etTel.text.toString(),
            binding.etTelOffice.text.toString(),
            Date(),
            getColor(currentColor),
            MY_CARD
        )
    }

    override fun renderEffect(effect: CreateCardEffect) {
        when (effect) {
            is CreateCardEffect.Error -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
            }
            CreateCardEffect.Success -> findNavController().navigateUp()
        }
    }
}