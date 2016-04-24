package net.kurochenko.multisingleexec

import java.util.UUID


object App {

  def main(args: Array[String]) {
    val hashChooser: HashBucketChooser = new HashBucketChooserImpl()
    val executionContextHolder: ExecutionContextHolder = new ExecutionContextHolderImpl()

    val bucketCount = executionContextHolder.size

    val results = new java.util.HashMap()

    val hash = "some hash"

    (0 to 100000)
      .toList
      .map(_ => UUID.randomUUID().toString())
      .map(uuid => hashChooser.getBucketForHash(uuid, bucketCount))
      .groupBy(identity)
      .mapValues(_.size)
      .map { case (k,v) => (k,v)}
      .toList
      .sortBy{ case (k,v) => k.number}
      .foreach { case (bucket, count) =>

        println(s"${bucket.number}:\t$count")

    }

  }

}
