package com.bandtec.finfamily.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bandtec.finfamily.R
import com.bandtec.finfamily.popups.PopAlterEntry
import com.bandtec.finfamily.popups.PopAlterExpense
import kotlinx.android.synthetic.main.activity_panel.*
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

        val entriesTypes = resources.getStringArray(R.array.entry)


        when (idCategory) {
            0 -> tvCategory.text = ""
            1 -> tvCategory.text = entriesTypes[0]
            2 -> tvCategory.text = entriesTypes[1]
            3 -> tvCategory.text = entriesTypes[2]
            4 -> tvCategory.text = entriesTypes[3]
            5 -> tvCategory.text = entriesTypes[4]
        }

        tvName.text = name
        tvValue.text = value

        ivEdit.setOnClickListener {
            val alter = Intent(requireActivity(), PopAlterEntry::class.java)
            alter.putExtra("id", id)
            alter.putExtra("name", name)
            alter.putExtra("category", idCategory)
            alter.putExtra("value", value)
            alter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(alter)
            activity?.finish()
        }
    }

}
