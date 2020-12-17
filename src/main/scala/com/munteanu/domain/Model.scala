package com.munteanu.domain

object Model {

  case class Hero(id: Option[Int] = None, name: String, attack: Int = 0, defense: Int = 0, spellPower: Int = 0, mana: Int = 0)

  case class HeroUpdated(id: Int)

}
