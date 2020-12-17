akka-http-demo
-----

## Description

This project uses Akka HTTP with Spray client and Wiremock for stubbing an external service

## API

Fetch data:
```
curl --location --request GET 'http://localhost:8484/heroes' --header 'Content-Type: application/json'
```

Save item:
```
curl --location --request POST 'http://localhost:8484/heroes' \
--header 'Content-Type: application/json' \
--data-raw '{"name":"HeroName", "attack":1, "defense":1, "spellPower":3, "mana":3}'
```