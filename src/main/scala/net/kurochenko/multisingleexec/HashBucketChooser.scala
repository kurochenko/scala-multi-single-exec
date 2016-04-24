package net.kurochenko.multisingleexec

import scala.util.hashing.MurmurHash3


trait HashBucketChooser {
  def getBucketForHash(hash: String, totalBucketCount: Int): Bucket
}


class HashBucketChooserImpl extends HashBucketChooser {

  override def getBucketForHash(hash: String, totalBucketCount: Int): Bucket = {
    val hashAsInt = Math.abs(MurmurHash3.stringHash(hash))

    Bucket(hashAsInt % totalBucketCount)
  }

}

