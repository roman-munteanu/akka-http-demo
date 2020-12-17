package com.munteanu.service

import com.munteanu.domain.DomainJsonProtocol

import scala.concurrent.{ExecutionContext, Future}

class BusinessService(val restService: RestService)(implicit ec: ExecutionContext) extends DomainJsonProtocol {

  import com.munteanu.domain.Model._

  private val hostPath = "/api/v1/heroes"

  def fetchData(): Future[List[Hero]] = {
    val responseF = restService.getCall(hostPath)
    responseF
  }

  def save(hero: Hero): Future[HeroUpdated] =
    restService.postCall(hostPath, hero)
}
