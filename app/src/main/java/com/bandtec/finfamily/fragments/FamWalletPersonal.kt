package com.bandtec.finfamily.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bandtec.finfamily.ModalEntry
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.fragment_fam_wallet_personal.*

/**
 * A simple [Fragment] subclass.
 */
class FamWalletPersonal : Fragment() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        more.setOnClickListener {
            val tela1 = Intent(requireActivity(), ModalEntry::class.java)
            startActivity(tela1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fam_wallet_personal, container, false)
    }

}