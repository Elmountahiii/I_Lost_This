package com.redgunner.ilostthis.view.fragment.foundAndLost

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import java.net.URLEncoder


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel: MainViewModel by activityViewModels()
    private val navArgs: DetailsFragmentArgs by navArgs()

    lateinit var subject: String
    lateinit var email: String
    lateinit var phoneNumber:String


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
            subject = " ${Item.name} lost in ${Item.place} "
            Glide.with(this).load(Item.imageUrl).into(detilsImage)
            ItemTitle.text = Item.title
            itemDescription.text = Item.description
            ItemTime.text = Item.date


        })

        viewModel.foundItem.observe(viewLifecycleOwner, { Item ->

            MainDetaliItem.text = " ${Item.name} found in ${Item.place}"
            subject = " ${Item.name} found in ${Item.place} "
            Glide.with(this).load(Item.imageUrl).into(detilsImage)
            ItemTitle.text = Item.title
            itemDescription.text = Item.description
            ItemTime.text = Item.date

        })

        viewModel.userInformation.observe(viewLifecycleOwner, { userInformation ->

            UserFullName.text = "FullName : ${userInformation.FullName}"
            UserAddress.text = "${userInformation.City}, ${userInformation.AddressOne}"
            UserPhoneNumbber.text = userInformation.PhoneNumber
            phoneNumber = userInformation.PhoneNumber
            UserEmail.text = userInformation.Email
            email = userInformation.Email
        })

        topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        UserPhoneNumbber.setOnClickListener {


            sendToWhatsApp(phoneNumber)
        }
        UserEmail.setOnClickListener {
            shareOnEmail(subject, email)

        }


    }

    private fun shareOnEmail(subject: String, email: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)

        emailIntent.type = "message/rfc822"
        emailIntent.`package` = "com.google.android.gm"

        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        try {
            requireContext().startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Email not installed", Toast.LENGTH_LONG).show()

        }

    }

    private  fun sendToWhatsApp(phoneNumber:String){
        val packageManager = context?.packageManager
        val i = Intent(Intent.ACTION_VIEW)
        try {
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (packageManager?.let { i.resolveActivity(it) } != null) {
                requireContext().startActivity(i)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}