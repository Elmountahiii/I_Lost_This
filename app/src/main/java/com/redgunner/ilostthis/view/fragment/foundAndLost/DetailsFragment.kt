package com.redgunner.ilostthis.view.fragment.foundAndLost

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel: MainViewModel by activityViewModels()
    private val navArgs: DetailsFragmentArgs by navArgs()


    override fun onStart() {
        super.onStart()
        if (navArgs.lostOrFound) {
            viewModel.getLostPost(navArgs.postId)
        } else {
            viewModel.getFoundPost(navArgs.postId)

        }

    }

    override fun onResume() {
        super.onResume()

        viewModel.lostItem.observe(viewLifecycleOwner, { Item ->

            MainDetaliItem.text = " ${Item.name} lost in ${Item.place} "
            Glide.with(this).load(Item.imageUrl).into(detilsImage)
            ItemTitle.text = Item.title
            itemDescription.text = Item.description
            ItemTime.text = Item.date


        })

        viewModel.foundItem.observe(viewLifecycleOwner, { Item ->

            MainDetaliItem.text = " ${Item.name} found in ${Item.place}"
            Glide.with(this).load(Item.imageUrl).into(detilsImage)
            ItemTitle.text = Item.title
            itemDescription.text = Item.description
            ItemTime.text = Item.date

        })

        viewModel.userInformation.observe(viewLifecycleOwner, { userInformation ->

            UserFullName.text = "FullName : ${userInformation.FullName}"
            UserAddress.text = "${userInformation.City}, ${userInformation.AddressOne}"
            UserPhoneNumbber.text = "${userInformation.Email} \n ${userInformation.PhoneNumber}"
        })

        topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


    }


}