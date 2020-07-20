package com.example.myapplication.util

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

fun bitMapToBase64(bitmap: Bitmap):String{
    //criar um fluxo de saida
    val byteArrayOutputStream = ByteArrayOutputStream()


    //comprimindo o bit map no fluxo de saida
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream )
    val imagemArray = byteArrayOutputStream.toByteArray();
    //converter imagem em base 64
    val imagemBase64 = Base64.encodeToString(imagemArray, Base64.NO_WRAP);
    return  imagemBase64
}