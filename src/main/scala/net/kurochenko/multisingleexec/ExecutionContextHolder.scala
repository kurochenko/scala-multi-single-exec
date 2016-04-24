package net.kurochenko.multisingleexec

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext


trait ExecutionContextHolder {

  def size: Int
  def getExecutionContextForBucket(bucket: Bucket): ExecutionContext
  
}


class ExecutionContextHolderImpl extends ExecutionContextHolder {

  val ContextPoolSize: Int = 10

  lazy val contexts = (0 to ContextPoolSize)
    .toList
    .map { i =>
      Bucket(i) -> createExecutionContext
    }.toMap


  override val size: Int = ContextPoolSize


  override def getExecutionContextForBucket(bucket: Bucket): ExecutionContext = {
    if (bucket.number >= ContextPoolSize) {
      throw new IllegalStateException(s"Bucket number ${bucket.number} exceeds allocated buckets count $ContextPoolSize")
    }

    contexts.get(bucket).get
  }


  private def createExecutionContext: ExecutionContext = {
    ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
  }
}
