package com.employees.file

import android.content.Context
import android.net.Uri
import java.io.*
import java.nio.charset.Charset
import java.util.*

enum class FDMode(val value: String) {
    Read("r"),
    Write("w")
}

fun readFile(
    context: Context,
    uri: Uri,
    charset: Charset = Charset.defaultCharset()
): String? {
    return try {
        val contentResolver = context.contentResolver

        var fileContent: String? = null

        contentResolver.openFileDescriptor(uri, FDMode.Read.value)?.use {
            FileInputStream(it.fileDescriptor).use { fileInputStream ->
                fileContent = readFileContent(
                    fileInputStream = fileInputStream,
                    charset = charset
                )
            }
        }

        fileContent
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

@Throws(IOException::class)
private fun readFileContent(
    fileInputStream: FileInputStream,
    charset: Charset
): String {
    BufferedReader(InputStreamReader(fileInputStream, charset)).use { br ->
        val sb = StringBuilder()
        var line: String?
        while (br.readLine().also { line = it } != null) {
            sb.append(line)
            sb.append('\n')
        }
        return sb.toString()
    }
}