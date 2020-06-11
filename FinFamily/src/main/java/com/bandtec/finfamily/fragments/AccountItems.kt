package com.bandtec.finfamily.fragments
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bandtec.finfamily.R
import com.bandtec.finfamily.popups.PopAlterPut
import com.bandtec.finfamily.popups.PopListContributors
import kotlinx.android.synthetic.main.fragment_account_items.*
/**
 * A simple [Fragment] subclass.
 */
class AccountItems : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_items, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = arguments?.getInt("id", 0)
        val name = arguments?.getString("name", "")
        val idCategory = arguments?.getInt("category", 0)
        val value = arguments?.getFloat("value", 0f).toString()

        tvName.text = name
        when (idCategory) {
            0 -> tvCategory.text = ""
            1 -> tvCategory.text = "Remuneração"
            2 -> tvCategory.text = "Bônus"
            3 -> tvCategory.text = "Rendimento"
            4 -> tvCategory.text = "Outras rendas"
            5 -> tvCategory.text = "Empréstimos"
        }
        tvValue.text = value

        edit.setOnClickListener {
            val tela1 = Intent(requireActivity(), PopAlterPut::class.java)
            startActivity(tela1)
        }
        contributors.setOnClickListener {
            val tela1 = Intent(requireActivity(), PopListContributors::class.java)
            startActivity(tela1)
        }
    }
}
