package net.kurochenko.multisingleexec

import scala.concurrent.ExecutionContext

trait ExecutionContextHolder {

  def size: Int
  def getExecutionContextForBucket(bucket: Bucket): ExecutionContext

}
