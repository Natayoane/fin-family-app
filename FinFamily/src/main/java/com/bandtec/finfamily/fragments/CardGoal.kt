package com.bandtec.finfamily.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bandtec.finfamily.ModalEntry
import com.bandtec.finfamily.R
import com.bandtec.finfamily.popups.PopEntryOutputGoal
import com.bandtec.finfamily.popups.PopNewEntry
import kotlinx.android.synthetic.main.fragment_card_goal.*
import kotlinx.android.synthetic.main.fragment_fam_wallet.*

class CardGoal : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnNewEntry.setOnClickListener {
            val intent = Intent(requireActivity(), PopEntryOutputGoal::class.java)
            startActivity(intent)
        }
    }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_card_goal, container, false)
        }
    }
