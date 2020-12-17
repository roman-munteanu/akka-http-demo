package com.munteanu.service

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration._
import com.munteanu.domain.DomainJsonProtocol

class ExternalService extends DomainJsonProtocol {

  import com.munteanu.domain.Model._
  import spray.json._

  private val hostPath = "/api/v1/heroes"
  private val wireMockServer: WireMockServer = new WireMockServer(wireMockConfig().port(8089));

  val data = List(
    Hero(Some(1), "Gelu", 5, 3, 1, 1),
    Hero(Some(2), "Solmyr", 2, 0, 5, 4)
  )

  wireMockServer.stubFor(
    get(urlPathEqualTo(hostPath))
      .willReturn(aResponse()
        .withHeader("Content-Type", "application/json")
        .withBody(data.toJson.prettyPrint)
        .withStatus(200))
  )

  wireMockServer.stubFor(
    post(urlPathEqualTo(hostPath))
      .withRequestBody(matchingJsonPath("$.name"))
      .willReturn(aResponse()
        .withHeader("Content-Type", "application/json")
        .withBody(HeroUpdated(data.size + 1).toJson.prettyPrint)
        .withStatus(200))
  )

  def startServer() =
    wireMockServer.start();

  def stopServer() =
    wireMockServer.stop()
}
