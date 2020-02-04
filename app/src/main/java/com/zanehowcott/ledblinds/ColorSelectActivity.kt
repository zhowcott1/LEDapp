package com.zanehowcott.ledblinds

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.color_select.*
import java.io.Serializable

class ColorSelectActivity() : AppCompatActivity(), Parcelable {

    lateinit var Rslider : SeekBar
    lateinit var Rvalue : TextView
    private var seekbarStatusView: TextView? = null


    lateinit var Gslider : SeekBar
    lateinit var Gvalue : TextView

    lateinit var Bslider : SeekBar
    lateinit var Bvalue : TextView

    class LED : Serializable {
        var red: Int = 0
        var green: Int = 0
        var blue: Int = 0

        fun insertValues(r: Int, g: Int, b: Int){
            red = r
            green = g
            blue = b
        }


    }
    var LEDColor = LED()

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.color_select)

        Rslider = findViewById(R.id.seekBarR) as SeekBar
        Rvalue = findViewById(R.id.redProg) as TextView
        seekbarStatusView = this.seekbarStatus

        Rslider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Rvalue.text = getString(R.string.selected, progress)
                seekbarStatusView!!.text = "Tracking Touch"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekbarStatusView!!.text = "Stopped Tracking Touch"

                val Rnum = Rslider.progress
                val Gnum = Gslider.progress
                val Bnum = Bslider.progress
                set_color.setBackgroundColor(Color.argb(255, Rnum, Gnum, Bnum))
                LEDColor.insertValues(Rnum, Gnum, Bnum)

            }
        })
        Gslider = findViewById(R.id.seekBarG) as SeekBar
        Gvalue = findViewById(R.id.greenProg) as TextView


        Gslider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Gvalue.text = getString(R.string.selected, progress)
                seekbarStatusView!!.text = "Tracking Touch"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekbarStatusView!!.text = "Stopped Tracking Touch"

                val Rnum = Rslider.progress
                val Gnum = Gslider.progress
                val Bnum = Bslider.progress
                set_color.setBackgroundColor(Color.argb(255, Rnum, Gnum, Bnum))
                LEDColor.insertValues(Rnum, Gnum, Bnum)


            }
        })
        Bslider = findViewById(R.id.seekBarB) as SeekBar
        Bvalue = findViewById(R.id.blueProg) as TextView


        Bslider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Bvalue.text =  getString(R.string.selected, progress)
                seekbarStatusView!!.text = "Tracking Touch"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekbarStatusView!!.text = "Stopped Tracking Touch"

                val Rnum = Rslider.progress
                val Gnum = Gslider.progress
                val Bnum = Bslider.progress
                set_color.setBackgroundColor(Color.argb(255, Rnum, Gnum, Bnum))
                LEDColor.insertValues(Rnum, Gnum, Bnum)


            }
        })

        set_color.setOnClickListener {
            intent = Intent(this, ControlActivity::class.java )
            intent.putExtra("rLED", LEDColor.red)
            intent.putExtra("gLED", LEDColor.green)
            intent.putExtra("bLED", LEDColor.blue)

            startActivity(intent)
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ColorSelectActivity> {
        override fun createFromParcel(parcel: Parcel): ColorSelectActivity {
            return ColorSelectActivity(parcel)
        }

        override fun newArray(size: Int): Array<ColorSelectActivity?> {
            return arrayOfNulls(size)
        }
    }

}
