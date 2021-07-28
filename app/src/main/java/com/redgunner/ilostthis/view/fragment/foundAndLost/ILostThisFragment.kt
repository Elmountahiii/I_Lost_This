package com.redgunner.ilostthis.view.fragment.foundAndLost

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.adapter.ILostThisAdapter
import com.redgunner.ilostthis.viewModel.AuthViewModel
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_i_lost_this.*


class ILostThisFragment : Fragment(R.layout.fragment_i_lost_this) {


    private val viewModel: MainViewModel by activityViewModels()

    private  val iLostThisAdapter=ILostThisAdapter {

    }


    override fun onStart() {
        super.onStart()
                   setUpRecyclerView()
        viewModel.getLostItems()


    }

    override fun onResume() {
        super.onResume()
        viewModel.lostItems.observe(viewLifecycleOwner,{lostItems ->

            Log.d("zbi",lostItems[0].name)

            iLostThisAdapter.submitList(lostItems)

        })
    }

    private fun setUpRecyclerView() {
        ILostList.apply {
            this.adapter=iLostThisAdapter
            this.layoutManager = LinearLayoutManager(
                    this@ILostThisFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
            )
            this.hasFixedSize()
        }
    }


}