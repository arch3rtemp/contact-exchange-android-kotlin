package com.sweeftdigital.contactsexchange.presentation.main.create

import android.animation.ValueAnimator
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sweeftdigital.contactsexchange.R
import com.sweeftdigital.contactsexchange.databinding.FragmentCardCreateBinding
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.util.Constants.MY_CARD
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CreateCardFragment : Fragment() {
    private var _binding: FragmentCardCreateBinding? = null
    private val binding get() = _binding!!

    private val createCardViewModel: CreateCardViewModel by viewModel()

    private var currentColor: Int = R.color.light_navy
    private lateinit var cardBackground: Drawable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initCard()
        handleEvents()
        initObservers()
    }

    private fun initCard() {
        with(binding) {
            tvColorLightNavy.setBackgroundResource(R.drawable.shape_selected_card_color_light_navy_bg)
            cardBackground = clCreate.background
        }
    }

    private fun handleEvents() {
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
                createCardViewModel.saveCard(contact)
            }
        }
    }

    private fun initObservers() {
        createCardViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.success -> {
                    findNavController().navigateUp()
                }
                it.error -> {
                    Toast.makeText(requireContext(), "Fill all fields!", Toast.LENGTH_SHORT).show()
                }
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
}