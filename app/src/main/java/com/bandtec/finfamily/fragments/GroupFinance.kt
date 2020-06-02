package com.bandtec.finfamily.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bandtec.finfamily.Panel
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.fragment_group_finance.*

/**
 * A simple [Fragment] subclass.
 */
class GroupFinance : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_finance, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val groupName = arguments?.get("groupName").toString()
        tvGroups.text = groupName
        println(arguments?.get("groupName").toString())
        tvGroups.setOnClickListener {
            val panel = Intent(requireActivity(), Panel::class.java)
            startActivity(panel)
        }

    }
}