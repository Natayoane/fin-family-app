package com.bandtec.finfamily.popups

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.AvatarGroups
import com.bandtec.finfamily.Group
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.CreateGroupModel
import com.bandtec.finfamily.model.GroupResponse
import kotlinx.android.synthetic.main.pop_activity_new_group.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopNewGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_activity_new_group);
        val groups = Intent(this, Group::class.java)

        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        avatar.setOnClickListener {
            val avatar = Intent(this, AvatarGroups::class.java)
            startActivity(avatar)
            finish()
        }

        btnClose.setOnClickListener {
            val chooseAction = Intent(this, PopChooseGroupAction::class.java)

            startActivity(chooseAction)
            finish()
        }

        ivFinish.setOnClickListener {
            val groupName = inputNameGroup.text.toString()

            if (groupName.isEmpty()) {
                inputNameGroup.error = getString(R.string.group_name_input)
                inputNameGroup.requestFocus()
                return@setOnClickListener
            } else if (groupName.length < 4 || groupName.length > 45) {
                inputNameGroup.error = getString(R.string.invalid_group_name)
                inputNameGroup.requestFocus()
                return@setOnClickListener
            }

            val group = CreateGroupModel(groupName, 2, sp.getInt("userId", 0))

            RetrofitClient.instance.createGroup(group)
                .enqueue(object : Callback<GroupResponse> {
                    override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.default_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<GroupResponse>,
                        response: Response<GroupResponse>
                    ) {
                        if (response.code().toString() == "201") {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.group_created),
                                Toast.LENGTH_LONG
                            ).show()
                            startActivity(groups)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.default_error),
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
                })
        }
    }

}
