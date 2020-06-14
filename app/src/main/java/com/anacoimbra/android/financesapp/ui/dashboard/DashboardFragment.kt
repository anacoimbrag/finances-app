package com.anacoimbra.android.financesapp.ui.dashboard

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.anacoimbra.android.financesapp.R
import com.anacoimbra.android.financesapp.network.Resource
import com.anacoimbra.android.financesapp.ui.dashboard.adapter.DashboardAdapter
import kotlinx.android.synthetic.main.dashboard_content.*
import kotlinx.android.synthetic.main.dashboard_loading.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (loadingText.compoundDrawablesRelative[1] as? AnimationDrawable)?.start()

        btnAddTransaction.setOnClickListener {
            findNavController().navigate(R.id.registerTransaction)
        }

        dashboardViewModel.transactions.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    content.showLoading()
                }
                is Resource.Success -> {
                    (loadingText.compoundDrawablesRelative[1] as? AnimationDrawable)?.stop()
                    content.showContent()
                    transactions.adapter =
                        DashboardAdapter().apply {
                            transactions = it.data.orEmpty().plus(it.data.orEmpty())
                        }
                    transactions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            setElevation(recyclerView)
                            translateParallax(recyclerView.computeVerticalScrollOffset().toFloat())
                        }
                    })
                }
                is Resource.Error -> content.showError()
            }
        })
    }

    private fun setElevation(recyclerView: RecyclerView) {
        for (i in 1 until (recyclerView.adapter?.itemCount ?: 0))
            recyclerView.findViewHolderForAdapterPosition(i)?.itemView?.isSelected =
                recyclerView.canScrollVertically(-1)
    }

    private fun translateParallax(of: Float) {
        val header = transactions.findViewHolderForAdapterPosition(0)?.itemView ?: return
        val ofCalculated = of * 0.5f
        if (of < header.height) {
            val anim = TranslateAnimation(0f, 0f, ofCalculated, ofCalculated)
            anim.fillAfter = true
            anim.duration = 100
            header.startAnimation(anim)
        }
    }
}