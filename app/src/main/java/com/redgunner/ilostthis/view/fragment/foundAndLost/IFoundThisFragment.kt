package com.redgunner.ilostthis.view.fragment.foundAndLost

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.adapter.IFoundThisAdapter
import com.redgunner.ilostthis.state.PostClick
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_i_found_this.*


class IFoundThisFragment : Fragment(R.layout.fragment_i_found_this) {

    private val viewModel: MainViewModel by activityViewModels()
    private val iFoundThisAdapter=IFoundThisAdapter{postClick ->

    when(postClick){
        is PostClick.ItemFound ->{
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(postClick.postId,postClick.isLost))
        }
        else -> {

        }
    }


    }


    override fun onStart() {
        super.onStart()

        setUpRecyclerView()
        viewModel.getFoundItem()

    }

    override fun onResume() {
        super.onResume()
        viewModel.foundItems.observe(viewLifecycleOwner,{foundItems ->
            iFoundThisAdapter.submitList(foundItems)

        })
    }

    private fun setUpRecyclerView() {
        IFoundList.apply {
            this.adapter=iFoundThisAdapter
        }
    }


}