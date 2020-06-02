package com.bandtec.finfamily.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bandtec.finfamily.R
import com.bandtec.finfamily.popups.PopAlterPut
import com.bandtec.finfamily.popups.PopListContributors
import kotlinx.android.synthetic.main.fragment_account_items.*

/**
 * A simple [Fragment] subclass.
 */
class AccountItems : Fragment() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        edit.setOnClickListener {
            val tela1 = Intent(requireActivity(), PopAlterPut::class.java)
            startActivity(tela1)
        }

        contributors.setOnClickListener {
            val tela1 = Intent(requireActivity(), PopListContributors::class.java)
            startActivity(tela1)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_items, container, false)
    }

}