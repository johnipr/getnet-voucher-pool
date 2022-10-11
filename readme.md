# Getnet Voucher Pool

Este projeto faz parte de um processo seletivo onde foi solicitado o desenvolvimento de um microsserviço com o seguinte objetivo:
**O objetivo é criar um micro serviço de pool de vouchers baseado em micro serviços.**


## Instalação com docker

```sh
cd getnet-voucher-pool
mvn package -Dmaven.test.skip 
docker-compose up     
```
## Endpoints publicos

Cadastrar Cliente
**POST /api/signup**
```sh
{
	"name" : "Teste User",
	"email" : "teste@gmail.com",
	"password" : "123456"
}
```

Logar para receber bearer token para as demais requisições
**POST /api/signup**
```sh
{
	"email" : "teste@gmail.com",
	"password" : "123456"
}
```
## Endpoints privados
Todos os endpoints privados precisam do Header Authorization nesse formato : 
[ **Bearer eyJhbGciOiJIUzUxMiJ9.eyJ...g0fQ.mg_emI...IAENA** ]

Logar para receber bearer token para as demais requisições
**POST api/offers**
```sh
{
    "name": "Oferta 01",
    "percentDiscount": 13.5,
    "expirationDate": "12/12/2022"
}
```
# Getnet Voucher Pool

Este projeto faz parte de um processo seletivo onde foi solicitado o desenvolvimento de um microsserviço com o seguinte objetivo:
**O objetivo é criar um micro serviço de pool de vouchers baseado em micro serviços.**


## Instalação com docker

```sh
cd getnet-voucher-pool
mvn package -Dmaven.test.skip 
docker-compose up     
```


## Endpoints publicos

Interface do Swagger
**GET /api/swagger-ui/index.html**

```sh

```
**POST /api/signup**
```sh
{
	"name" : "Teste User",
	"email" : "teste@gmail.com",
	"password" : "123456"
}
```

Logar para receber bearer token para as demais requisições
**POST /api/signup**
```sh
{
	"email" : "teste@gmail.com",
	"password" : "123456"
}
```
## Endpoints privados
Todos os endpoints privados precisam do Header Authorization nesse formato : 
[ **Bearer eyJhbGciOiJIUzUxMiJ9.eyJ...g0fQ.mg_emI...IAENA** ]

Cria uma nova oferta e gera um voucher para cada cliente
**POST /api/offers**
```sh
{
    "name": "Oferta 01",
    "percentDiscount": 13.5,
    "expirationDate": "12/12/2022"
}
```
Resgata um voucher se ele for válido
**POST /api/vouchers/redeem**
```sh
{
	"email" : "johni03@gmail.com",
	"code" : "6f918d1a-2b72-49ad-bab9-7373790c859c"
}
```

Busca todos os vouchers válidos de um determinado email
**GET /api/vouchers/email/{email}**
```sh

```
Busca um voucher pelo seu código unico 
**GET /api/vouchers/{code}**
```sh

```

Busca todos os vouchers 
**GET /api/vouchers**
```sh

```