package com.example.sampleproject_mob2041.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.activity.LoginActivity
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.database.LibrarianDB
import com.example.sampleproject_mob2041.databinding.FragmentEditInformationBinding
import com.example.sampleproject_mob2041.model.Librarian


class EditInformationFragment : Fragment() {
    private lateinit var librarianDB: LibrarianDB
    private lateinit var binding: FragmentEditInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditInformationBinding.inflate(layoutInflater, container, false)
        librarianDB = LibrarianDB(Database(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCurrentInformation()

        binding.btnEditInformationCommit.setOnClickListener {
            if (validate()) {
                if (!isUserExist()) {
                    binding.edtLayoutEditInformationCurrentPassword.error =
                        requireContext().getString(R.string.wrong_password)
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.edtLayoutEditInformationCurrentPassword.error = null
                    }, 2000)
                    return@setOnClickListener
                } else {
                    librarianDB.editLibrarian(
                        binding.edtEditInformationLoginName.text.toString(),
                        Librarian(
                            loginName = binding.edtEditInformationLoginName.text.toString(),
                            displayName = binding.edtEditInformationDisplayName.text.toString(),
                            password = binding.edtEditInformationReNewPassword.text.toString()
                        )
                    )
                    Toast.makeText(
                        requireContext(),
                        requireContext().getText(R.string.change_information_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
                        requireActivity().finish()
                    }, 2000)
                }
            }
        }
    }

    private fun initCurrentInformation() {
        val currentInformation = requireActivity().intent
        binding.edtEditInformationDisplayName.setText(currentInformation.getStringExtra("display_name"))
        binding.edtEditInformationLoginName.setText(currentInformation.getStringExtra("username"))
    }

    private fun validate(): Boolean {
        if (binding.edtEditInformationDisplayName.text.isNullOrBlank()) {
            binding.edtLayoutEditInformationDisplayName.error =
                requireContext().getText(R.string.you_must_enter_all_information)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.edtLayoutEditInformationDisplayName.error = null
            }, 2000)
            return false
        }

        if (binding.edtEditInformationCurrentPassword.text.isNullOrBlank()) {
            binding.edtLayoutEditInformationCurrentPassword.error =
                requireContext().getText(R.string.you_must_enter_all_information)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.edtLayoutEditInformationCurrentPassword.error = null
            }, 2000)
            return false
        }

        if (binding.edtEditInformationNewPassword.text.isNullOrBlank()) {
            binding.edtLayoutEditInformationNewPassword.error =
                requireContext().getText(R.string.you_must_enter_all_information)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.edtLayoutEditInformationNewPassword.error = null
            }, 2000)
            return false
        }

        if (binding.edtEditInformationReNewPassword.text.isNullOrBlank()) {
            binding.edtLayoutEditInformationReNewPassword.error =
                requireContext().getText(R.string.you_must_enter_all_information)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.edtLayoutEditInformationReNewPassword.error = null
            }, 2000)
            return false
        }

        if (binding.edtEditInformationNewPassword.text.toString() != binding.edtEditInformationReNewPassword.text.toString()) {
            binding.edtLayoutEditInformationReNewPassword.error =
                requireContext().getString(R.string.password_must_be_the_same)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.edtLayoutEditInformationReNewPassword.error = null
            }, 2000)
            return false
        }

        return true
    }

    private fun isUserExist(): Boolean {
        return librarianDB.isLibrarianExist(
            binding.edtEditInformationLoginName.text.toString(),
            binding.edtEditInformationCurrentPassword.text.toString()
        )
    }


}