import java.net.ServerSocket
import java.net.Socket
import java.io.PrintWriter
import java.io.File

fun main(args: Array<String>) {
    val port: Int = args[0].toInt()
    val dir: String = args[1]
    mainLoop(port, dir)
}

fun mainLoop(port: Int, dir: String) {
    val listener: ServerSocket = ServerSocket(port)

    try {
        while (true) {
            val socket: Socket = listener.accept()

            try {
                val pw: PrintWriter = PrintWriter(socket.getOutputStream())
                val resp: String = response(dir)

                pw.print("HTTP/1.1 200 OK\r\n")
                pw.print("Content-Type: text/html\r\n")
                pw.print("Content-Length: " + resp.length + "\r\n\r\n")
                pw.flush()
                pw.println(resp)
                pw.flush()
            } finally {
                socket.close();
            }
        }
    } finally {
        listener.close();
    } 
}

fun response(dir: String): String {
    val sb = StringBuilder()

    sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">\n")
    sb.append("<html>\n")
    sb.append("<title>Directory Listing</title>\n")
    sb.append("<body>\n")

    sb.append("<h1>Directory Listing for $dir</h1>\n")
    sb.append("<br />\n")

    sb.append("<u1>\n")
    
    File(dir).list().forEach {
        sb.append("<li> " + it + "\n")
    }

    sb.append("</body>\n")
    sb.append("</html>\n")

    return sb.toString()

}

