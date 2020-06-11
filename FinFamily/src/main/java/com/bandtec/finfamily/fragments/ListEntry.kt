package com.bandtec.finfamily.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.fragment_list_entry.*

/**
 * A simple [Fragment] subclass.
 */
class ListEntry : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_entry, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = arguments?.getInt("id", 0)
        val name = arguments?.getString("name", "")
        val idCategory = arguments?.getInt("category", 0)
        val value = arguments?.getFloat("value", 0f).toString()


        when (idCategory) {
            0 -> tvCategory.text = ""
            1 -> tvCategory.text = "Remuneração"
            2 -> tvCategory.text = "Bônus"
            3 -> tvCategory.text = "Rendimento"
            4 -> tvCategory.text = "Outras rendas"
            5 -> tvCategory.text = "Empréstimos"
        }

        Toast.makeText(activity, "2 : $value", Toast.LENGTH_SHORT).show()

        tvName.text = name
        tvValue.text = value
    }

}
