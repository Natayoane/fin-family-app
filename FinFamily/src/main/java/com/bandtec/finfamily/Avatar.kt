package com.bandtec.finfamily
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Avatar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar)
    }

    fun chooseAvatar(image: View) {
        val profileEdit = Intent(this, ProfileEdit::class.java)
        val profile = Intent(this, Profile::class.java)
        profileEdit.putExtra("avatar", ((image as ImageView).drawable as BitmapDrawable).bitmap)
        profile.putExtra("avatar", (image.drawable as BitmapDrawable).bitmap)

        startActivity(profileEdit)
        finish()
    }
}

