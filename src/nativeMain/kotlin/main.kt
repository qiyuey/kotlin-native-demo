import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen

fun readLines(path: String): List<String> {
    val file = fopen(path, "r")
    val lines = mutableListOf<String>()

    try {
        memScoped {
            val bufferLength = 64 * 1024
            val buffer = allocArray<ByteVar>(bufferLength)
            while (true) {
                val nextLine = fgets(buffer, bufferLength, file)?.toKString()
                if (nextLine.isNullOrEmpty()) {
                    break
                }
                lines.add(nextLine)
            }
        }
    } finally {
        fclose(file)
    }
    return lines;
}

fun main() {
    println("RUN ON:${Platform.osFamily} ${Platform.cpuArchitecture}")
    println()
    readLines("settings.gradle.kts").forEach {
        print(it)
    }
}