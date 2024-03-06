package com.cinar.catchtheben10

import android.os.Bundle

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cinar.catchtheben10.databinding.ActivityMainBinding
import java.util.Random


class MainActivity : AppCompatActivity() {

    var score = 0
    var imageArray = ArrayList<ImageView>()
    var handler = Handler(Looper.getMainLooper())
    var runnable = Runnable {  }
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //ImageArray

        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)




        hideImages()

        //CountDown Timer sayaç

        object : CountDownTimer(15500,1000){
            override fun onFinish() {

                //İşlem bitince ne olsun

                binding.timeText.text = "Time: 0"

                handler.removeCallbacks(runnable)

                for (image in imageArray) {
                    //View.Gone komple gider layouttan , View.Invısıble görünmez olur ama yer kaplar
                    //View.Visible görünür
                    image.visibility = View.INVISIBLE
                }



                //Alert dioalog uyarı mesajı
                val alert = AlertDialog.Builder(this@MainActivity)

                alert.setTitle("Game Over")
                alert.setMessage("Restart The Game?")
                alert.setPositiveButton("Yes") {dialog, which ->
                    //Restart oyunu yeniden başlatma
                    val intent = intent
                    finish()
                    startActivity(intent)


                }

                alert.setNegativeButton("No") {dialog, which ->
                    Toast.makeText(this@MainActivity,"Game Over",Toast.LENGTH_LONG).show()
                }

                alert.show()


            }

            override fun onTick(millisUntilFinished: Long) {
                // her bir saniyede ne olsun
                binding.timeText.text = "Time: " + millisUntilFinished/1000
            }

        }.start()

    }


    fun hideImages() {

        runnable = object : Runnable {
            //Sürekli işlem yapma
            override fun run() {
                //Resimleri gizleme
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }

                //Random ile resimleri gösterme
                val random = Random()
                val randomIndex = random.nextInt(6)
                imageArray[randomIndex].visibility = View.VISIBLE

                // 500 saniyede bir random resim değişikliği yap
                handler.postDelayed(runnable,500)
            }

        }

        handler.post(runnable)

    }


    fun imageClick(view: View){
        score = score + 1
        binding.scoreText.text = "Score: $score"

    }
}
