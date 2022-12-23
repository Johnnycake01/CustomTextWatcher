package com.coroutine.cardexpiredateinput

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.coroutine.cardexpiredateinput.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private var shouldAddSlash = true
    private var expiredateWithoutSlash:String = ""
    private var customTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            shouldAddSlash = !s.toString().contains('/')
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val word = s.toString()
            var wordToShow = ""
            for(i in word.indices){
                if (i == 1 && shouldAddSlash){
                    wordToShow += "${word[i]}/";
                }else{
                    wordToShow += word[i]
                }
            }
            binding.expireDate.removeTextChangedListener(this)
            binding.expireDate.setText(wordToShow)
            binding.expireDate.setSelection(wordToShow.length)
            expiredateWithoutSlash = binding.expireDate.text.toString().replace("/","")
            binding.expireDate.addTextChangedListener(this)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.expireDate.addTextChangedListener(customTextWatcher)
        binding.confirmButton.setOnClickListener {
            Toast.makeText(this, "sending $expiredateWithoutSlash", Toast.LENGTH_SHORT).show()
        }

    }
}