package com.elaj.patient.Utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object NumberHandler {
    fun arabicToDecimal(number: String): String {
        val chars = CharArray(number.length)
        for (i in 0 until number.length) {
            var ch = number[i]
            if (ch.toInt() >= 0x0660 && ch.toInt() <= 0x0669)
                ch -= 0x0660.minus('0'.toInt())
            else if (ch.toInt() >= 0x06f0 && ch.toInt() <= 0x06F9)
                ch -= 0x06f0.minus('0'.toInt())
            chars[i] = ch
        }
        return String(chars)
    }

    fun roundDouble(value: Any?): String { //        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
//        DecimalFormat df = (DecimalFormat)nf;
        val currentLocale = Locale.GERMAN
        val otherSymbols =
            DecimalFormatSymbols(currentLocale)
        otherSymbols.decimalSeparator = '.'
        otherSymbols.groupingSeparator = ','
        val df = DecimalFormat("####0.00", otherSymbols)
        //        DecimalFormat df = new DecimalFormat("####0.00");
//        System.out.println("Value: " + df.format(value));
        return df.format(value)
    }

    fun roundDouble2(value: Any): String {
        var res = value.toString()
        try {
            res = res.substring(0, res.indexOf(".") + 2)
        } catch (e: Exception) {
        }
        return res
    }

    fun getFloat(value: String): Float {
        return try {
            val f = value.toFloat()
            if (java.lang.Float.isNaN(f)) 0f else f
        } catch (e: Exception) {
            0f
        }
    }

    fun getDouble(value: String): Double {
        return try {
            val f = value.toDouble()
            if (java.lang.Double.isNaN(f)) 0.0 else f
        } catch (e: Exception) {
            0.0
        }
    }

    fun getInt(value: String): Int {
        return try {
            value.toInt()
        } catch (e: Exception) {
            0
        }
    }

    fun formatFloat(d: Double): String {
        var d = d
        d = round(d, 4)
        return ClearFloat(String.format(Locale.US, "%.3f", d))
    }

    private fun ClearFloat(Val: String): String {
        return Val.replace("\\.0*$".toRegex(), "")
    }

    fun formatFloat(d: Double, r: Int): String {
        var d = d
        d = round(d, r)
        return String.format(Locale.US, "%." + (r - 1) + "f", d)
    }

    fun formatFloat_Q(d: Double): String {
        var d = d
        d = round(d, 4)
        return String.format(Locale.US, "%.2f", d)
    }

    fun round(value: Double, places: Int): Double {
        var value = value
        require(places >= 0)
        val factor = Math.pow(10.0, places.toDouble()).toLong()
        value = value * factor
        val tmp = Math.round(value)
        return tmp.toDouble() / factor
    }

    fun formatDouble(value: Double): String {
        val df = DecimalFormat("####0.00")
        return df.format(value)
        //        System.out.println("Value: " + df.format(value));
    }
}