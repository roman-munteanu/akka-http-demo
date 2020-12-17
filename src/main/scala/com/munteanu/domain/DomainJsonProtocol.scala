package com.munteanu.domain

import spray.json.DefaultJsonProtocol

trait DomainJsonProtocol extends DefaultJsonProtocol {

  import com.munteanu.domain.Model._

  implicit val heroFormat = jsonFormat6(Hero)
  implicit val heroUpdatedFormat = jsonFormat1(HeroUpdated)
}
