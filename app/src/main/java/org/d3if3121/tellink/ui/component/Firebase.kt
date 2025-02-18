package org.d3if3121.tellink.ui.component

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

// Di file utilitas, misalnya FirebaseUtils.kt

fun uploadImageToFirebase(
    imageUri: Uri,
    onSuccess: (String) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val storageRef: StorageReference = FirebaseStorage.getInstance().reference
    val fileRef = storageRef.child("uploads/${System.currentTimeMillis()}.jpg")

    fileRef.putFile(imageUri)
        .addOnSuccessListener { taskSnapshot ->
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                onSuccess(downloadUrl)
            }
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}
