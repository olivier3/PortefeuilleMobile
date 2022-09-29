package cstjean.mobile.portefeuille

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import cstjean.mobile.portefeuille.creditcard.CreditCard
import cstjean.mobile.portefeuille.databinding.FragmentCreditCardBinding
import kotlinx.coroutines.launch
import java.util.*

class CreditCardFragment : Fragment() {
    private var _binding: FragmentCreditCardBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private val args: CreditCardFragmentArgs by navArgs()

    private val creditCardViewModel: CreditCardViewModel by viewModels {
        CreditCardViewModelFactory(args.creditCardId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreditCardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            cardOwner.doOnTextChanged { text, _, _, _ ->
                creditCardViewModel.updateCreditCard { oldCreditCard ->
                    oldCreditCard.copy(nom = text.toString())
                }
            }

            cardNumbers.doOnTextChanged { text, _, _, _ ->
                creditCardViewModel.updateCreditCard { oldCreditCard ->
                    oldCreditCard.copy( cardNumbers = text.toString())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                creditCardViewModel.creditCard.collect { creditCard ->
                    creditCard?.let { updateUi(it) }
                    creditCard?.let { qrCodeGenerator(it)}
                }
            }
        }
    }

    fun qrCodeGenerator(creditCard: CreditCard) {

        val gson = Gson()

        val content = CreditCardData(
            creditCard.nom,
            creditCard.cardNumbers,
            creditCard.expDate
        )

        val jsonContent: String = gson.toJson(content)


        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(jsonContent, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }

        binding.qrCode.setImageBitmap(bitmap)
    }

    private fun updateUi(creditCard: CreditCard) {
        binding.apply {
            // Pour éviter une loop infinie avec le update
            if (cardOwner.text.toString() != creditCard.nom) {
                cardOwner.setText(creditCard.nom)
            }

            if (cardNumbers.text.toString() != creditCard.cardNumbers.toString()) {
                cardNumbers.setText(creditCard.cardNumbers.toString())
            }

            if (cardExpirationDate.text.toString() != creditCard.expDate.toString()) {
                cardExpirationDate.setText(creditCard.expDate.toString())
            }
         }
    }
}