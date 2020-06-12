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
            1 -> tvCategory.text = "Moradia"
            2 -> tvCategory.text = "Contas"
            3 -> tvCategory.text = "Saúde"
            4 -> tvCategory.text = "Educação"
            5 -> tvCategory.text = "Transporte"
            6 -> tvCategory.text = "Mercado"
            7 -> tvCategory.text = "Empregados"
            8 -> tvCategory.text = "TV"
            9 -> tvCategory.text = "Taxas"
            10 -> tvCategory.text = "Saques"
            11 -> tvCategory.text = "Bares"
            12 -> tvCategory.text = "Lazer"
            13 -> tvCategory.text = "Compras"
            14 -> tvCategory.text = "Cuidados"
            15 -> tvCategory.text = "Serviços"
            16 -> tvCategory.text = "Viagem"
            17 -> tvCategory.text = "Presentes"
            18 -> tvCategory.text = "Família"
            19 -> tvCategory.text = "Despesas"
            20 -> tvCategory.text = "Outros"
            21 -> tvCategory.text = "Impostos"
            22 -> tvCategory.text = "Juros"
            23 -> tvCategory.text = "Crediário"
            24 -> tvCategory.text = "Cheque"
            25 -> tvCategory.text = "Crédito"
            26 -> tvCategory.text = "Carnê"
            27 -> tvCategory.text = "Outros"
            28 -> tvCategory.text = "Juros"
            29 -> tvCategory.text = "Pagamento"
            30 -> tvCategory.text = "Resgate"
            31 -> tvCategory.text = "Aplicação"
            32 -> tvCategory.text = "Transferência"
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
