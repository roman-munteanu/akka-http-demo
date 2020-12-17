package com.munteanu.service

import akka.actor.ActorSystem
import com.munteanu.domain.DomainJsonProtocol
import spray.client.pipelining._
import spray.http._
import spray.httpx.SprayJsonSupport

import scala.concurrent.{ExecutionContext, Future}

class RestService(implicit system: ActorSystem, ec: ExecutionContext) extends DomainJsonProtocol with SprayJsonSupport {

  import com.munteanu.domain.Model._

  val host = "http://localhost:8089"

  def getCall(path: String): Future[List[Hero]] = {
    val pipeline: HttpRequest => Future[List[Hero]] = sendReceive  ~> unmarshal[List[Hero]]
    pipeline(Get(host + path))
  }

  def postCall(path: String, hero: Hero): Future[HeroUpdated] = {
    val pipeline: HttpRequest => Future[HeroUpdated] = sendReceive ~> unmarshal[HeroUpdated]
    pipeline(Post(host + path, hero))
  }

}
