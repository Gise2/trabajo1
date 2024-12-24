package com.example.restaurantapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var etDish1Qty: EditText
    private lateinit var etDish2Qty: EditText
    private lateinit var swTip: Switch
    private lateinit var tvTotal: TextView

    private val dish1Price = 36000 // Precio del Pastel de Choclo
    private val dish2Price = 10000 // Precio de la Cazuela
    private val tipPercentage = 0.1 // 10% de propina

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vinculación con las vistas
        etDish1Qty = findViewById(R.id.etDish1Qty)
        etDish2Qty = findViewById(R.id.etDish2Qty)
        swTip = findViewById(R.id.swTip)
        tvTotal = findViewById(R.id.tvTotal)

        // Listener para actualizar el total
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateTotal()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        etDish1Qty.addTextChangedListener(textWatcher)
        etDish2Qty.addTextChangedListener(textWatcher)
        swTip.setOnCheckedChangeListener { _, _ -> calculateTotal() }
    }

    private fun calculateTotal() {
        val qty1 = etDish1Qty.text.toString().toIntOrNull() ?: 0
        val qty2 = etDish2Qty.text.toString().toIntOrNull() ?: 0

        val subtotal = (qty1 * dish1Price) + (qty2 * dish2Price)
        val tip = if (swTip.isChecked) subtotal * tipPercentage else 0.0
        val total = subtotal + tip

        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        tvTotal.text = "Total: ${format.format(total)}"
    }
}
