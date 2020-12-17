package com.munteanu

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.munteanu.domain.DomainJsonProtocol
import com.munteanu.service.{BusinessService, ExternalService, RestService}
import spray.json._

object AkkaHttpDemoApp extends App with DomainJsonProtocol with SprayJsonSupport {

  implicit val system = ActorSystem("AkkaHttpDemoApp")
  implicit val materializer = ActorMaterializer()
  import system.dispatcher
  import com.munteanu.domain.Model._

  // context
  lazy val restService = new RestService()
  lazy val businessService = new BusinessService(restService)

  val mainRoute: Route =
    (path("heroes") & extractLog) { log =>
      get {
        log.info("Fetching heroes...")
        complete(
          businessService.fetchData()
            .map(_.toJson.prettyPrint)
            .map(toHttpEntity)
        )
      } ~
      post {
        entity(as[Hero]) { hero =>
          log.info(s"Saving hero: $hero")
          complete(
            StatusCodes.Created,
            businessService.save(hero)
              .map(_.toJson.prettyPrint)
              .map(toHttpEntity)
          )
        }
      }
    }

  def toHttpEntity(payload: String) =
    HttpEntity(
      ContentTypes.`application/json`,
      payload
    )

  Http().bindAndHandle(mainRoute, "localhost", 8484)

  val externalService = new ExternalService()
  externalService.startServer()
}
