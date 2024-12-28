package dev.arch3rtemp.contactexchange.presentation.card

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dev.arch3rtemp.contactexchange.databinding.DeletePopupBinding
import dev.arch3rtemp.contactexchange.databinding.FragmentCardBinding
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.model.ContactType
import dev.arch3rtemp.core_ui.base.BaseFragment
import dev.arch3rtemp.core_ui.util.currentDeviceRealSize
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.min

class CardFragment :
    BaseFragment<CardEvent, CardEffect, CardState, FragmentCardBinding, CardViewModel>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardBinding
        get() = FragmentCardBinding::inflate

    override val viewModel by viewModel<CardViewModel>()
    private val args by navArgs<CardFragmentArgs>()

    override fun prepareView(savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() = with(binding) {
        clEdit.setOnClickListener { goToEditFragment() }
        clDelete.setOnClickListener { createDeleteDialog() }
    }

    private fun goToEditFragment() {
        findNavController()
            .navigate(CardFragmentDirections.actionCardFragmentToEditCardFragment(args.id))
    }

    private fun createDeleteDialog() {
        val dialogBinding = DeletePopupBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        dialogBinding.btnDelete.setOnClickListener {
            viewModel.setEvent(CardEvent.OnCardDelete(args.id))
            dialog.dismiss()
        }
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun renderState(state: CardState) {
        when (state.viewState) {
            ViewState.Idle -> viewModel.setEvent(CardEvent.OnCardLoad(args.id))
            ViewState.Loading -> showCardLoading()
            ViewState.Error -> showCardError()
            is ViewState.Success -> {
                val contact = state.viewState.data
                contact.let {
                    showCardSuccess(it.isMy)
                    setCardData(it)
                    generateQr(it)
                }
            }
        }
    }

    private fun setCardData(contact: Contact) = with(binding) {
        tvName.text = contact.name
        tvPosition.text = contact.position
        tvEmail.text = contact.email
        tvPhoneMobile.text = contact.phoneMobile
        tvPhoneOffice.text = contact.phoneOffice
        clCard.background.colorFilter = PorterDuffColorFilter(contact.color, PorterDuff.Mode.SRC_IN)
    }

    private fun generateQr(contact: Contact) {
        val (width, height) = requireActivity().windowManager.currentDeviceRealSize()
        var smallerDimension = min(width, height)
        smallerDimension = smallerDimension * 3 / 4

        val qrgEncoder =
            QRGEncoder(contact.toString(), null, QRGContents.Type.TEXT, smallerDimension)
        // Getting QR-Code as Bitmap
        val bitmap = qrgEncoder.getBitmap(0)
        // Setting Bitmap to ImageView
        binding.ivQr.setImageBitmap(bitmap)
    }

    override fun renderEffect(effect: CardEffect) {
        when (effect) {
            is CardEffect.ShowError -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
            }
            is CardEffect.AnimateDeletion -> {
                cardDeletionAnimation()
            }
        }
    }

    private fun showCardLoading() = with(binding) {
        progressCircularCard.visibility = View.VISIBLE
        clButtons.visibility = View.INVISIBLE
        ivCardError.visibility = View.INVISIBLE
    }

    private fun showCardSuccess(isMy: ContactType) = with(binding) {
        clButtons.visibility = View.VISIBLE
        progressCircularCard.visibility = View.INVISIBLE
        ivCardError.visibility = View.INVISIBLE
        if (isMy == ContactType.MY_CARD) {
            binding.clEdit.visibility = View.VISIBLE
        }
    }

    private fun showCardDeleteSuccess() = with(binding) {
        clButtons.visibility = View.INVISIBLE
        progressCircularCard.visibility = View.INVISIBLE
        ivCardError.visibility = View.INVISIBLE
        llDeleted.visibility = View.VISIBLE
    }

    private fun showCardError() = with(binding) {
        ivCardError.visibility = View.VISIBLE
        clButtons.visibility = View.INVISIBLE
        progressCircularCard.visibility = View.INVISIBLE
    }

    private fun cardDeletionAnimation() = with(binding) {
        val moveX = ObjectAnimator.ofFloat(clCard, View.X, clCard.x, clCard.x)
        val moveY = ObjectAnimator.ofFloat(clCard, View.Y, clCard.y, clCard.y + 700)

        val scaleX = ObjectAnimator.ofFloat(clCard, View.SCALE_X, 1f, 0.2f)
        val scaleY = ObjectAnimator.ofFloat(clCard, View.SCALE_Y, 1f, 0.2f)

        val alpha = ObjectAnimator.ofFloat(clCard, View.ALPHA, 1f, 0f)

        AnimatorSet().apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    showCardDeleteSuccess()
                    checkedSignAnimation()
                }
            })
            duration = 1000
            play(moveX).with(moveY).with(scaleX).with(scaleY).with(alpha)
            start()
        }
    }

    private fun checkedSignAnimation() = with(binding) {
        llDeleted.clipChildren = false
        llDeleted.clipToPadding = false
        val moveX = ObjectAnimator.ofFloat(llDeleted, View.TRANSLATION_X, 200.0f, 0.0f).apply {
            interpolator = DecelerateInterpolator()
            duration = 400
        }
        val scaleUpX = ObjectAnimator.ofFloat(ivChecked, View.SCALE_X, 1f, 1.1f).apply {
            duration = 300
            startDelay = 800
        }
        val scaleUpY = ObjectAnimator.ofFloat(ivChecked, View.SCALE_Y, 1f, 1.1f).apply {
            duration = 300
            startDelay = 800
        }
        val scaleDownX = ObjectAnimator.ofFloat(ivChecked, View.SCALE_X, 1.1f, 1.0f).apply {
            duration = 300
            startDelay = 1700
        }
        val scaleDownY = ObjectAnimator.ofFloat(ivChecked, View.SCALE_Y, 1.1f, 1.0f).apply {
            duration = 300
            startDelay = 1700
        }

        AnimatorSet().apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigateUp()
                    }, 1000)
                }
            })
            play(scaleUpX).with(scaleUpY).with(scaleDownX).with(scaleDownY).with(moveX)
            start()
        }
    }
}
