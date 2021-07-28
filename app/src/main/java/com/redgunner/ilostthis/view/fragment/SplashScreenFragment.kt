package com.redgunner.ilostthis.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.viewModel.AuthViewModel
import kotlinx.coroutines.delay

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {


    private val viewModel: AuthViewModel by activityViewModels()


    override fun onStart() {
        super.onStart()

        lifecycleScope.launchWhenStarted {
            delay(3000)

            if (viewModel.userAuth.value==true){
                findNavController().navigate(R.id.action_splashScrrenFragment_to_mainFragment)

            }else{
                findNavController().navigate(R.id.action_splashScrrenFragment_to_registrationFragment)

            }
        }
    }
}