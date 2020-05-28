package com.sancell.xingqiu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.LayoutInflaterFactory
import com.sancell.xingqiu.view.login.activity.CodeLoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val mactivie = this
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(mactivie),
            object : LayoutInflater.Factory2 {
                override fun onCreateView(
                    parent: View?,
                    name: String,
                    context: Context,
                    attrs: AttributeSet
                ): View? {
                    Log.i("keey", "onCreateView1")
                    if (name.equals("TextView")) {
                        val buuton = Button(context, attrs)
                        buuton.setOnClickListener{
                            startToLogin()
                        }
                        buuton.setText("我是替换了的")
                        return buuton

                    }
                    return null
                }

                override fun onCreateView(
                    name: String,
                    context: Context,
                    attrs: AttributeSet
                ): View? {
                    Log.i("keey", "onCreateView2")

                    return null
                }
            })
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val classN = Class.forName("com.sancell.xingqiu.test.Test")
        val mehtod = classN.getMethod("logTest")
        mehtod.invoke(classN.newInstance())

        val testBi = classN.newInstance()
        val filed = classN.getDeclaredField("testName")
        filed.setAccessible(true)
        filed.set(testBi, "我是那么")

        val mehtoddd = classN.getMethod("logTest")
        mehtoddd.invoke(testBi)

        val list = listOf(1, 2, 3, 4)

    }
    fun startToLogin(){
        startActivity(Intent(this@MainActivity, CodeLoginActivity::class.java))

    }
}
