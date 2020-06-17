package com.bandtec.finfamily.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.fragment_members_fam_contribution.*

/**
 * A simple [Fragment] subclass.
 */
class MembersFamContribution : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_members_fam_contribution, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println(arguments?.getFloat("groupTotal")).toString()
//        tvFamName.text = arguments?.getString("groupName", "")
        tvFamValue.text = arguments?.getFloat("groupTotal", 0f).toString()
    }

}
