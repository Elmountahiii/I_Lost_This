package com.redgunner.ilostthis.view.fragment.auth


import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.adapter.RegistrationStateAdapter
import com.redgunner.ilostthis.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        setUpTapLayout()
    }


    override fun onResume() {
        super.onResume()

        viewModel.authSgInSuccessful.observe(viewLifecycleOwner,{ Successful ->

           if (Successful){
               findNavController().navigate(R.id.action_registrationFragment_to_addInformationFragment)
           }
        })

        viewModel.authLogInSuccessful.observe(viewLifecycleOwner,{Successful ->

            if (Successful){
                findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
            }

        })
    }

    private fun setUpTapLayout() {
        myViewpager.adapter = RegistrationStateAdapter(activity)
        TabLayoutMediator(TabLayout, myViewpager) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = "SignIn"
                }
                1 -> {
                    tab.text = "LogIn"
                }
            }

        }.attach()
    }

}