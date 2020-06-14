package com.bandtec.finfamily.fragments
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bandtec.finfamily.R
import com.bandtec.finfamily.popups.PopAlterExpense
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
        val userId = arguments?.getInt("userId", 0)

        tvName.text = name
        val expensesTypes = resources.getStringArray(R.array.expenses)

        when (idCategory) {
            0 -> tvCategory.text = ""
            1 -> tvCategory.text = expensesTypes[0]
            2 -> tvCategory.text = expensesTypes[1]
            3 -> tvCategory.text = expensesTypes[2]
            4 -> tvCategory.text = expensesTypes[3]
            5 -> tvCategory.text = expensesTypes[4]
            6 -> tvCategory.text = expensesTypes[5]
            7 -> tvCategory.text = expensesTypes[6]
            8 -> tvCategory.text = expensesTypes[7]
            9 -> tvCategory.text = expensesTypes[8]
            10 -> tvCategory.text = expensesTypes[9]
            11 -> tvCategory.text = expensesTypes[10]
            12 -> tvCategory.text = expensesTypes[11]
            13 -> tvCategory.text = expensesTypes[12]
            14 -> tvCategory.text = expensesTypes[13]
            15 -> tvCategory.text = expensesTypes[14]
            16 -> tvCategory.text = expensesTypes[15]
            17 -> tvCategory.text = expensesTypes[16]
            18 -> tvCategory.text = expensesTypes[17]
            19 -> tvCategory.text = expensesTypes[18]
            20 -> tvCategory.text = expensesTypes[19]
            21 -> tvCategory.text = expensesTypes[20]
            22 -> tvCategory.text = expensesTypes[21]
            23 -> tvCategory.text = expensesTypes[22]
            24 -> tvCategory.text = expensesTypes[23]
            25 -> tvCategory.text = expensesTypes[24]
            26 -> tvCategory.text = expensesTypes[25]
            27 -> tvCategory.text = expensesTypes[26]
            28 -> tvCategory.text = expensesTypes[27]
            29 -> tvCategory.text = expensesTypes[28]
            30 -> tvCategory.text = expensesTypes[29]
            31 -> tvCategory.text = expensesTypes[30]
            32 -> tvCategory.text = expensesTypes[31]
        }
        tvValue.text = value

        edit.setOnClickListener {
            val alter = Intent(requireActivity(), PopAlterExpense::class.java)
            alter.putExtra("id", id)
            alter.putExtra("name", name)
            alter.putExtra("category", idCategory)
            alter.putExtra("value", value)
            startActivity(alter)
        }
        contributors.setOnClickListener {
            val list = Intent(requireActivity(), PopListContributors::class.java)
            list.putExtra("value", value)
            list.putExtra("userId", userId)
            startActivity(list)
        }
    }
}
