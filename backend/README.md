# Gourmet

Back-end de um aplicativo que adivinha a comida automaticamente. A documentação foi feita em português para facilitar a leitura, entretanto, todo o resto foi feito em inglês.

**Sumário**

1. [Objetivos](#objectives)
2. [Escolhas e Ferramentas Utilizadas](#choices-tools)
3. [Overall e Considerações](#overall)
4. [Executando a Aplicação](#running)
5. [Documentação](#docs)

## 🤔 Qual é o objetivo? <a name="objectives"></a>

O gourmet tem a função de ser rápido, escalável e prático. Porém, vale dizer que existe algumas coisas a serem ressaltadas ao decorrer dessa documentação.

## ⚙ Ferramentas Utilizadas <a name="choices-tools"></a>

#### 🟠 Kotlin
Optei utilizar o Kotlin ao invés do Java pela praticidade que ele contém. Entretanto, utilizei bibliotecas Java como o Spring e o Sonic. O código também poderia ser escrito completamente em Java, tendo em vista que ambos rodam na JVM e o código final de Kotlin é escrito em Java. Utilizei a linguagem por mera sofisticação e elegância.

#### 🍃 Spring - Reactive Stack
A escolha da stack reativa foi simples. Um servidor TomCat não possuí a mesma escalibilidade do que o servidor Netty, que trata requisições de forma assíncrona e não-bloqueante. Portanto, caso o **Gourmet** precise escalar, o Netty fará isso com maestria.

#### 🐘 Postgres
Quando eu comecei a fazer o projeto, eu pensei em utilizar um banco não-relacional, pois assim, o ORM junto com o JPA estaria disponível. Porém, aproveitei a oportunidade e fiz tudo na mão, foi bem simples.

#### 🦔 Sonic
O [Sonic](https://github.com/valeriansaliou/sonic) é um buscador como o Algolia e o ElasticSearch. Fiquei em dúvida desses três mas no final acabei me decidindo pelo Sonic pelo baixo consumo de memória e pela eficiência na sugestão de resultados. Um possível problema, e que poderia ser resolvido no futuro é que o wrapper Java mais avaliado pelo Sonic não possuí a opção de tradução para português, então fiquei limitado a pesquisa e sugestões em inglês, o que é uma pena.

#### 🐳 Docker
Para não só englobar isso tudo, mas facilitar a instalação do projeto, eu utilizei o Docker. Os comandos de como subir o container estarão logo abaixo.

#### 📗 OpenAPI
Resolvi utilizar uma opção prática e direta para a documentação da API. O OpenAPI. Eu converto a API em JSON utilizando o Swagger e com o OpenAPI eu faço a criação da interface para a consulta.


## 💲 Overall do Projeto <a name="overall"></a>
Nesse projeto, como se pode observar, eu fiz uso do Docker. Entretanto, há de se considerar de que um projeto simples como esse, não haveria a necessidade de tal. Porque  afinal de contas, há custos para se manter na cloud. Esse é uma base de um projeto e portanto pelo momento que ele se encontra, uma solução tão sofisticada como Docker não seria necesssária. A containerização foi utilizada apenas para fins educativos e para a fácil instalação.

## ⚒ Executando a Aplicação <a name="running"></a>

Executar a aplicação se torna muito simples graças ao Docker. Em apenas dois passos a aplicação estará pronta para uso.

### 1 - Clone o repositório na sua máquina

```git
git clone https://github.com/ianguuima/gourmet.git
or
git clone https://github.com/ianguuima/gourmet.git && cd backend
```

### 2 - Docker
Para essa parte, é necessário que você possua o docker instalado em sua maquina. Caso não tenha, você pode instala-lo através:

**Windows**

Clicando [aqui](https://hub.docker.com/) para baixar o Docker Hub e executa-lo na sua máquina.

**Linux**

```bash
sudo apt install docker.io && sudo systemctl start docker
```

Após de terminar de instalar o docker, utilize esse comando para subir o container:

```docker
docker-compose up --build
```

O container será buildado e a aplicação estará disponível em alguns instantes :).
Caso você queira destruir o container, utilize:

```docker
docker-compose down
```

## 📜 Documentação <a name="docs"></a>

As imagens abaixo representam a documentação da api. Se desejado uma representação e visualização mais completa, você pode acessar `HOST:8080/index.html`.

