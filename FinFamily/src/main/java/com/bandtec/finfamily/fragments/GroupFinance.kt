package com.bandtec.finfamily.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bandtec.finfamily.Panel
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.fragment_group_finance.*

class GroupFinance : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_finance, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val groupId = arguments?.get("groupId").toString().toInt()
        val groupName = arguments?.get("groupName").toString()
        val groupType = arguments?.get("groupType").toString().toInt()
        val groupOwner = arguments?.get("groupOwner").toString().toInt()
        val externalGroupId = arguments?.get("groupExternalId").toString()

        tvGroups.text = groupName

        tvGroups.setOnClickListener {
            val panel = Intent(requireActivity(), Panel::class.java)

            panel.putExtra("groupId", groupId)
            panel.putExtra("groupName", groupName)
            panel.putExtra("groupType", groupType)
            panel.putExtra("groupOwner", groupOwner)
            panel.putExtra("groupExternalId", externalGroupId)

            startActivity(panel)
        }
    }
}