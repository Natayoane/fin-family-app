package com.bandtec.finfamily.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GoalsTransResponse
import com.bandtec.finfamily.popups.PopDeleteGoal
import com.bandtec.finfamily.popups.PopEntryOutputGoal
import kotlinx.android.synthetic.main.fragment_card_goal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardGoal : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_goal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = arguments?.getInt("id", 0)
        val name = arguments?.getString("name", "")
        val description = arguments?.getString("description", "")
        val value = arguments?.getFloat("value", 0f)
        val deadline = arguments?.getString("deadline", "")
        val userId = arguments?.getInt("userId", 0)
        val groupId = arguments?.getInt("groupId", 0)

        tvGoalName.text = name
        tvDescription.text = description
        goalValue.text = getString(R.string.cifrao, String.format("%.2f", value))
        goalDeadline.text = deadline
        goalProgress.max = value?.toInt()!!

        getTotal(id!!)

        btnNewEntry.setOnClickListener {
            val newInvoice = Intent(requireActivity(), PopEntryOutputGoal::class.java)
            newInvoice.putExtra("groupId", groupId)
            newInvoice.putExtra("userId", userId)
            newInvoice.putExtra("goalId", id)
            startActivity(newInvoice)
        }

        deleteGoal.setOnClickListener {
            val deleteGoal = Intent(requireActivity(), PopDeleteGoal::class.java)
            deleteGoal.putExtra("goalId", id)
            startActivity(deleteGoal)
        }
    }

    fun getTotal(goalId: Int) {
        RetrofitClient.instance.getGoalsTrans(goalId)
            .enqueue(object : Callback<List<GoalsTransResponse>> {
                override fun onFailure(call: Call<List<GoalsTransResponse>>, t: Throwable) {
                    Toast.makeText(activity, getString(R.string.default_error), Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(
                    call: Call<List<GoalsTransResponse>>,
                    response: Response<List<GoalsTransResponse>>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            setProgress(response.body()!!)
                        }
                        response.code().toString() == "204" -> {
                            goalProgress.progress = 0
                            tvAvaibleNow.text = getString(R.string.cifrao, "0.00")
                            tvPercentage.text = getString(R.string.percentage, "0%")
                        }
                        else -> {
                            goalProgress.progress = 0
                            tvAvaibleNow.text = getString(R.string.cifrao, "0.00")
                            tvPercentage.text = getString(R.string.percentage, "0")
                            Toast.makeText(
                                activity,
                                getString(R.string.default_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            })
    }

    fun setProgress(transactions: List<GoalsTransResponse>) {
        var totalEntry = 0f
        var totalExpense = 0f
        val max = goalProgress.max
        transactions.forEach {
            if (it.transactionTypeId == 1) {
                totalEntry += it.value!!
            } else if (it.transactionTypeId == 2) {
                totalExpense += it.value!!
            }
        }
        val total = totalEntry - totalExpense
        val percentage = (total * 100) / max
        goalProgress.progress = total.toInt()
        tvAvaibleNow.text = getString(R.string.cifrao, String.format("%.2f", total))
        tvPercentage.text = getString(R.string.percentage, String.format("%.2f", percentage) + "%")

    }
}
