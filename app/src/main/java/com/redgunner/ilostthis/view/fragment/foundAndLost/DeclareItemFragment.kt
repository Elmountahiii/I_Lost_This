package com.redgunner.ilostthis.view.fragment.foundAndLost

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.utils.FoundItem
import com.redgunner.ilostthis.utils.LostItem
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_declare_item.*


class DeclareItemFragment : Fragment(R.layout.fragment_declare_item) {

    private val viewModel: MainViewModel by activityViewModels()
    private val itemsLostIt = listOf("I Lost it", "I Found it ")
    private val categoryItems = listOf("Phones", "Clothes", "Accessories", "Money")


    override fun onStart() {
        super.onStart()

        setUpDropDownMenu()
    }

    override fun onResume() {
        super.onResume()


        viewModel.addItemSuccessful.observe(viewLifecycleOwner, { Successful ->

            if (Successful) {
                Toast.makeText(this.context, "Adding Item Successfully", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()

            } else {
                Toast.makeText(this.context, "Error", Toast.LENGTH_LONG).show()
            }

        })



        DeclareButton.setOnClickListener {


            if (DeclareTitle.text.toString().isNotEmpty() &&
                    DeclareDescription.text.toString().isNotEmpty() &&
                    DeclareItemName.text.toString().isNotEmpty() &&
                    DeclarePlace.text.toString().isNotEmpty() &&
                    DeclareCategory1.editText?.text?.isNotEmpty() == true &&
                    Declarefoundorlost1.editText?.text?.isNotEmpty() == true
            ) {

                if (Declarefoundorlost1.editText!!.text.toString() == itemsLostIt[0]) {
                    viewModel.addLostItem(
                            LostItem(title = DeclareTitle.text.toString(),
                                    description = DeclareDescription.text.toString(),
                                    name = DeclareItemName.text.toString(),
                                    place = DeclarePlace.text.toString(),
                                    category =  DeclareCategory1.editText!!.text.toString()
                            )


                    )

                } else {
                    viewModel.addFoundItem(
                            FoundItem(title = DeclareTitle.text.toString(),
                                    description = DeclareDescription.text.toString(),
                                    name = DeclareItemName.text.toString(),
                                    place = DeclarePlace.text.toString(),
                                    category =  DeclareCategory1.editText!!.text.toString())
                    )
                }


            } else {
                Toast.makeText(this.context, "Please fill in the information below", Toast.LENGTH_LONG).show()

            }


        }


    }

    private fun setUpDropDownMenu() {

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, R.id.item, itemsLostIt)
        val categoryAdapter = ArrayAdapter(requireContext(), R.layout.list_item, R.id.item, categoryItems)

        (Declarefoundorlost1.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (DeclareCategory1.editText as? AutoCompleteTextView)?.setAdapter(categoryAdapter)
    }

}