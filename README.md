# Gourmet

Front-end da aplicação. Não adianta ter uma API bacana se não tem um front bonitinho pra consumir, não é mesmo? 

## ⚙ Ferramentas Utilizadas

#### 🌀 React
A escolha foi feita baseada em minha experiência pessoal. Lido melhor com o React e estou familiarizado com o framework. Entretanto a aplicação poderia ser consumida
pelo Vue ou Angular que também ficaria legal.

#### ⚡ Typescript
Linguagens fracamente tipadas correm o risco de atribuir um valor errado e também fica muito: "Aceito qualquer coisa". Gosto de estar no controle do meu código e ter
certeza que ele só vai receber o que eu defini que ele vai receber. E o Typescript, como uma linguagem bem tipada, encaixou bem nesse projeto.

#### 🅰 Axios
Utilizei o Axios para consumir o end-point da minha API. Ele foi simples de usar e de fácil acesso.

## 🤔 Overall do Projeto e Considerações <a name="overall"></a>
A stack React + Typescript + Axios encaixa muito bem para aplicação simples. A partir do momento em que a aplicação começa a crescer, a utilização de um bom cacheamento
local e um bom gerenciador de estado como o [Redux](https://redux.js.org/) se faz necessária.

## ⚒ Executando a Aplicação <a name="running"></a>

Executar uma aplicação React é muito simples. O tutorial conta com dois passos que te auxiliarão a compilar o projeto.

### 1 - Clone o repositório na sua máquina

```git
git clone https://github.com/ianguuima/gourmet.git
or
git clone https://github.com/ianguuima/gourmet.git && cd front-end
```

### 2 - Execute o comando para executar o projeto em localhost

```git
yarn start
or
npm start
```
