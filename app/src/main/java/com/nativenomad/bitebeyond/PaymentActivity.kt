package com.nativenomad.bitebeyond

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Razorpay checkout
        val finalAmount = intent.getIntExtra("amount", 0)
        if (finalAmount <= 0) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_AU6bdtIL2fRHtR")
        checkout.setImage(R.drawable.ic_logo) // your app logo// Replace with your test/live key
        checkout.setFullScreenDisable(true)
        try {
            val options = JSONObject().apply {
                put("name", "BiteBeyond")
                put("description", "Order Payment")
                put("currency", "INR")
                put("amount", finalAmount * 100) // Razorpay takes amount in paise
                put("theme.color", "#FF9800")
                put("prefill.contact", "9999999999")
                put("prefill.email", "test@example.com")
            }

            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            finish()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String?) {
        val intent = Intent().apply {
            putExtra("paymentId", razorpayPaymentID)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onPaymentError(code: Int, description: String?) {
        val intent = Intent().apply {
            putExtra("error", description ?: "Payment failed")
        }
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }
}