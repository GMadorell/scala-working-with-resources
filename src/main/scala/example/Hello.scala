package example

import java.nio.file.FileSystemNotFoundException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import scala.collection.mutable
import scala.io.Source
import scala.jdk.CollectionConverters._

object Hello extends App {

//  print(Source.fromResource("rootfile.json").toList)

  private val path = "/subdir/subsubdir"
  val resourceFile = this.getClass.getResource(path)
  println(resourceFile)
  val uri = resourceFile.toURI()
  val dirPath: Path =
    try {
      Paths.get(uri)
    } catch {
      case (e: FileSystemNotFoundException) =>
        // If this is thrown, then it means that we are running the JAR directly (example: not from an IDE)
        val env = mutable.Map[String, String]().asJava
        FileSystems.newFileSystem(uri, env).getPath(path)
    }

  Files.list(dirPath).forEach { it =>
    println(s"ToString: ${it.toString}")
    println(s"Filename: ${it.getFileName.toString}")
    println(s"AbsolutePath: ${it.toAbsolutePath.toString}")
    if (it.getFileName.toString.endsWith("json")) {
      println("Result:")
      println(Files.readString(it))
    }
  }
}
