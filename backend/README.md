# Gourmet

Back-end de um aplicativo que adivinha a comida automaticamente. A documentação foi feita em português para facilitar a leitura, entretanto, todo o resto foi feito em inglês.

## 🤔 Qual é o objetivo?

O gourmet tem a função de ser rápido, escalável e prático. Porém, vale dizer que existe algumas coisas a serem ressaltadas ao decorrer dessa documentação.

## ⚙ Ferramentas Utilizadas

#### 🟠 Kotlin
Optei utilizar o Kotlin ao invés do Java pela praticidade que ele contém. Entretanto, utilizei bibliotecas Java como o Spring e o Sonic. O código também poderia ser escrito completamente em Java, tendo em vista que ambos rodam na JVM e o código final de Kotlin é escrito em Java. Utilizei a linguagem por mera sofisticação e elegância.

#### 🍃 Spring - Reactive Stack
A escolha da stack reativa foi simples. Um servidor TomCat não possuí a mesma escalibilidade do que o servidor Netty, que trata requisições de forma assíncrona e não-bloqueante. Portanto, caso o **Gourmet** precise escalar, o Netty fará isso com maestria.

#### 🐘 Postgres
Quando eu comecei a fazer o projeto, eu pensei em utilizar um banco não-relacional, pois assim, o ORM junto com o JPA estaria disponível. Porém, aproveitei a oportunidade e fiz tudo na mão, foi bem simples.

#### 🦔 Sonic
O [Sonic](https://github.com/valeriansaliou/sonic) é um buscador como o Algolia e o ElasticSearch. Fiquei em dúvida desses três mas no final acabei me decidindo pelo Sonic pelo baixo consumo de memória e pela eficiência na sugestão de resultados. Um possível problema, e que poderia ser resolvido no futuro é que o wrapper Java mais avaliado pelo Sonic não possuí a opção de tradução para português, então fiquei limitado a pesquisa e sugestões em inglês, o que é uma pena.

#### 🐳 Docker
Para não só englobar isso tudo, mas como facilitar a instalação do projeto, eu utilizei o Docker. Os comandos de como subir o container estarão logo abaixo.


## 💲 Overall do Projeto
Nesse projeto, como se pode observar, eu fiz uso do Docker. Entretanto, há de se considerar de que um projeto simples como esse, não haveria a necessidade de tal. Porque  afinal de contas, há custos para se manter na cloud. Esse é uma base de um projeto e portanto pelo momento que ele se encontra, uma solução tão sofisticada como Docker não seria necesssária. A containerização foi utilizada apenas para fins educativos e para a fácil instalação.
