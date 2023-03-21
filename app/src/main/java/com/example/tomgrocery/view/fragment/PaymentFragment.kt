package com.example.tomgrocery.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding

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

        binding.paymentGroup.setOnClickListener {
            activity?.supportFragmentManager?.let {
                it.beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.content_frame, ConfirmFragment())
                    .commit()
            }
        }
    }

}