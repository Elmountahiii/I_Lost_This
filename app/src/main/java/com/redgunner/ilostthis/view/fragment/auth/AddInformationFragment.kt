package com.redgunner.ilostthis.view.fragment.auth


import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.utils.User
import com.redgunner.ilostthis.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_add_information.*
import kotlinx.coroutines.delay

class AddInformationFragment : Fragment(R.layout.fragment_add_information) {


    private val viewModel: AuthViewModel by activityViewModels()


    override fun onStart() {
        super.onStart()

        viewModel.saveInformationSuccessful.observe(viewLifecycleOwner, { Successful ->

            if (Successful) {

                lifecycleScope.launchWhenStarted {
                    delay(500)
                    progressBar2.isVisible=false
                    findNavController().navigate(R.id.action_addInformationFragment_to_mainFragment)

                }
            }else{
                progressBar2.isVisible=false

            }
        })
    }

    override fun onResume() {
        super.onResume()


        SaveButton.setOnClickListener {


            if (FullName.text.toString().isNotEmpty()
                    && Email.text.toString().isNotEmpty()
                    && addressOne.text.toString().isNotEmpty()
                    && country.text.toString().isNotEmpty()
                    && city.text.toString().isNotEmpty()
                    && phoneNumber.text.toString().isNotEmpty()
            ) {

                progressBar2.isVisible=true


                viewModel.saveUserInformation(


                        User(FullName = FullName.text.toString(),
                                Email = Email.text.toString(),
                                AddressOne = addressOne.text.toString(),
                                Country = country.text.toString(),
                                City = city.text.toString(),
                                PhoneNumber = phoneNumber.text.toString()

                        )
                )


            } else {
                Toast.makeText(this.context, "Please fill in the information below", Toast.LENGTH_LONG).show()

            }

        }


    }

}