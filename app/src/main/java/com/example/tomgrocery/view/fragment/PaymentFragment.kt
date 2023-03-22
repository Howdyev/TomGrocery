package com.example.tomgrocery.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.FragmentPaymentBinding
import com.example.tomgrocery.model.local.AppDatabase
import com.example.tomgrocery.model.local.dao.PaymentDao
import com.example.tomgrocery.model.local.entity.Payment
import com.example.tomgrocery.viewmodel.CheckoutViewModel

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var appDB: AppDatabase
    private lateinit var paymentDao: PaymentDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Payment"

        initDBConnection()
        initViews()

    }

    private fun initViews() {
        val payment = getSavedPayment()
        binding.inputNameOnCard.setText(payment.nameOnCard)
        if(payment.cvv > 0) {
            binding.inputCvv.setText(payment.cvv.toString())
        }
        binding.inputExpiresOn.setText(payment.expDate)
        binding.inputCardNumber.setText(payment.cardNumber)
        binding.payLl.setOnClickListener {
            submitPayment()
        }
    }

    private fun submitPayment() {
        binding.apply {
            if(inputCardNumber.text.isEmpty()) {
                inputCardNumber.error = "Enter your card number"
                inputCardNumber.requestFocus()
            } else if (inputNameOnCard.text.isEmpty()) {
                inputNameOnCard.error = "Enter the name on your card"
                inputNameOnCard.requestFocus()
            } else if (inputCvv.text.isEmpty()) {
                inputCvv.error = "Enter your card dvv"
                inputCvv.requestFocus()
            } else if (inputExpiresOn.text.isEmpty()) {
                inputExpiresOn.error = "Enter your card expired date"
                inputExpiresOn.requestFocus()
            } else {
                paymentDao.addPayment(Payment(
                    id = 0,
                    cardNumber = inputCardNumber.text.toString(),
                    nameOnCard = inputNameOnCard.text.toString(),
                    cvv = inputCvv.text.toString().toInt(),
                    expDate = inputExpiresOn.text.toString()
                ))
                activity?.supportFragmentManager?.let {
                    it.beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                        .replace(R.id.content_frame, ConfirmFragment())
                        .commit()
                }
            }
        }

    }

    private fun getSavedPayment(): Payment {
        val payments = paymentDao.getAllPayments()
        if (payments.isNotEmpty()) {
            return payments.last()
        } else {
            return Payment(
                id = 0,
                nameOnCard = "",
                cardNumber = "",
                cvv = 0,
                expDate = ""
            )
        }
    }

    private fun initDBConnection() {
        appDB = AppDatabase.getInstance(requireContext())!!
        paymentDao = appDB.getPaymentDao()
    }

}