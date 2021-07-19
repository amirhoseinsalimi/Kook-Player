package com.example.cassette.utlis

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

object FileUtils {

    enum class FILE_TYPES {
        MUSIC {
            val path = FilePathUtlis.getMusicsUri()
        },
        PLAYLIST
    }

    fun fileType(file: File): String {
        when {
            file.isDirectory -> return "${file.name} + is directory"
            file.isFile -> return "${file.name} + is file"
            else -> return "${file.name} not file, not dir"
        }
    }

    fun shareFile(context: Context, fileUri: Uri)
    {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "audio/*"
        context.startActivity(Intent.createChooser(shareIntent, "share audio"))
    }


    fun convertSongToFile(fileUri: String): File {
        return File(fileUri)
    }

    fun getUriForFile(context: Context, authority: String, fileToBeShared: File): Uri?
    {
        try {
             val uri =  FileProvider.getUriForFile(
                context,
                authority,
                fileToBeShared
            )
            return uri
        } catch (e: IllegalArgumentException) {
            Log.e(
                "File Selector",
                "The selected file can't be shared: $fileToBeShared"
            )
            return null
        }
    }

    fun checkFileExistence(file: File): Boolean {
        return file.exists()
    }

    fun fetchFiles(
        fileType: FILE_TYPES,
        context: Context,
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sortOrder: String? = null
    ): Cursor? {

        val path: Uri

        when (fileType) {
            FILE_TYPES.MUSIC -> path = FilePathUtlis.getMusicsUri()
            FILE_TYPES.PLAYLIST -> path = FilePathUtlis.getPlayListsUri()
        }

        try {
            val cursor: Cursor? = context?.contentResolver?.query(
                path,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )
            return cursor
        } catch (exception: java.lang.Exception) {
//          TODO(handle the exception)
        }
        return null
    }



}