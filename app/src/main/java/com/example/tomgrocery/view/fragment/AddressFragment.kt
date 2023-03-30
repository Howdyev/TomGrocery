package com.example.tomgrocery.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.FragmentAddressBinding
import com.example.tomgrocery.model.jsons.JsonState
import com.example.tomgrocery.model.local.AppDatabase
import com.example.tomgrocery.model.local.dao.AddressDao
import com.example.tomgrocery.model.remote.dto.Address
import com.example.tomgrocery.model.remote.dto.UpdateAddressRequestData
import com.example.tomgrocery.util.Utils
import com.example.tomgrocery.util.localstorage.LocalStorage
import com.example.tomgrocery.viewmodel.CheckoutViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private lateinit var binding: FragmentAddressBinding
    @Inject lateinit var localStorage: LocalStorage
    private val viewModel: CheckoutViewModel by viewModels()
    private lateinit var appDB: AppDatabase
    private lateinit var addressDao: AddressDao

    private var allStateList = listOf<JsonState>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDBConnection()
        setupObserver()
        activity?.title = "Address"

        initViews()
    }

    private fun initState( stateName: String) {
        val gson = Gson()
        val jsonStateFileString = Utils.getJsonDataFromAsset(requireActivity().applicationContext, "US_state.json")
        val listStateType = object : TypeToken<List<JsonState>>() {}.type
        allStateList = gson.fromJson(jsonStateFileString, listStateType)
        val stateList = allStateList.map { it.State }

        //set state adapter
        val adapterSate: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.spinnertextview, stateList)
        adapterSate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.stateSpinner.adapter = adapterSate
        if (stateName.isNotEmpty()) {
            val selectionPosition = adapterSate.getPosition(stateName)
            binding.stateSpinner.setSelection(selectionPosition)
        }
    }

    private fun initViews() {
        val address = getSavedAddress()
        binding.saStreet.setText(address.streetName)
        binding.saHouse.setText(address.houseNo)
        binding.saType.setText(address.type)
        binding.saCity.setText(address.city)
        val stateName = address.state ?: ""
        initState(stateName)
        if(address.pincode > 0) {
            binding.saZip.setText( address.pincode.toString() )
        }
        binding.txtPyment.setOnClickListener {
            submitAddress()
        }
    }

    private fun submitAddress() {
        binding.apply {
            if (saCity.text.isEmpty()) {
                saCity.error = "Enter your city"
                saCity.requestFocus()
            } else if (saZip.text.isEmpty()) {
                saZip.error = "Enter your Zip Code"
                saZip.requestFocus()
            } else {
                val updateAddressRequestData = UpdateAddressRequestData(
                    city = saCity.text.toString(),
                    houseNo = saHouse.text.toString(),
                    pincode = saZip.text.toString().toInt(),
                    streetName = saStreet.text.toString(),
                    type = saType.text.toString(),
                    userId = localStorage.getUserId()
                )
                viewModel.updateUserAddress(updateAddressRequestData)
            }

        }
    }

    fun loadJSONFromAssetState(): String? {
        var json: String? = null
        json = try {
            val inputStrem = requireContext().assets.open("state.joson")
            val size = inputStrem.available()
            val buffer = ByteArray(size)
            inputStrem.read(buffer)
            inputStrem.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun loadJSONFromAssetCity(): String? {
        var json: String? = null
        json = try {
            val inputStrem = requireContext().assets.open("US_city_state.json")
            val size = inputStrem.available()
            val buffer = ByteArray(size)
            inputStrem.read(buffer)
            inputStrem.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
    private fun getSavedAddress(): Address {
        val addressList = addressDao.getAllAddresses()
        if(addressList.isNotEmpty()) {
            return addressList.last()
        } else {
            return Address(
                index = 0,
                __v = 0,
                _id = "",
                state = "",
                city = "",
                houseNo = "",
                pincode = 0,
                streetName = "",
                type = "",
                userId = localStorage.getUserId()
            )
        }
    }

    private fun initDBConnection() {
        appDB = AppDatabase.getInstance(requireContext())!!
        addressDao = appDB.getAdressDao()
    }

    private fun setupObserver() {
        viewModel.isProcessing.observe(viewLifecycleOwner) {
            if(it) {
                binding.progressBar.root.visibility = View.VISIBLE
            } else {
                binding.progressBar.root.visibility = View.GONE
            }
        }
        viewModel.addressInfo.observe(viewLifecycleOwner) {
            if(it.data != null) {
                addressDao.addAddress(it.data)
                activity?.supportFragmentManager?.let {
                    it.beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                        .replace(R.id.content_frame, PaymentFragment())
                        .commit()
                }
            }
        }
    }

}