package com.souzaemerson.imcapplication.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.souzaemerson.imcapplication.core.Status
import com.souzaemerson.imcapplication.data.CalculateIMC
import com.souzaemerson.imcapplication.databinding.FragmentDetailsBinding
import com.souzaemerson.imcapplication.repository.ImcRepository
import com.souzaemerson.imcapplication.repository.ImcRepositoryImpl
import com.souzaemerson.imcapplication.ui.home.viewmodel.HomeViewModel
import com.souzaemerson.imcapplication.util.setError

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: ImcRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = ImcRepositoryImpl(CalculateIMC())
        viewModel = HomeViewModel(repository)

        calculateImc()
        observeVMEvents()
    }

    private fun calculateImc() {
        binding.buttonCalculate.setOnClickListener {
            val userHeight = binding.txtHeightEdit.text.toString()
            val userWeight = binding.txtWeightEdit.text.toString()

            viewModel.calculateImcBodyType(height = userHeight, weight = userWeight)
        }
    }

    private fun observeVMEvents() {
        viewModel.heightFieldErrorResId.observe(viewLifecycleOwner) {
            binding.txtHeightLayout.setError(requireContext(), it)
        }
        viewModel.weightFieldErrorResId.observe(viewLifecycleOwner) {
            binding.txtWeightLayout.setError(requireContext(), it)
        }
        viewModel.imcBodyTypeResult.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { result ->
                        binding.run {
                            setVisibility()
                            setImageByBodyType(result)
                        }
                    }
                }
                Status.ERROR -> {}
                Status.LOADING -> {}
            }
        }
    }

    private fun FragmentDetailsBinding.setImageByBodyType(result: String) {
        viewModel.setImageBodyType(result, txtImcResult, imgBodyResult)
    }

    private fun FragmentDetailsBinding.setVisibility() {
        txtTitleImc.visibility = View.GONE
        txtDescriptionImc.visibility = View.GONE
        txtImcResult.visibility = View.VISIBLE
        imgBodyResult.visibility = View.VISIBLE
        txtTitleResult.visibility = View.VISIBLE
    }

}