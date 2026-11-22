# HASH-RPEC

Trabalho prático de Tabelas Hash, da disciplina de Resolução de Problemas Estruturados em Computação.

Feito por Rhyan Rocha e Augusto Neon.

- Repositório: https://github.com/Rhuuuyyyy/HASH-RPEC
- Vídeo: 

## O que o programa faz

Lê o arquivo `Books_rating.csv` (avaliações de livros da Amazon, cerca de 3 milhões de linhas) e, para cada usuário,
descobre qual dos livros que ele avaliou tem a maior média. A média de um livro é a soma de todas as notas que ele
recebeu dividida pelo número de avaliações, de todos os usuários.

Usamos uma tabela hash feita por nós, com encadeamento separado e 1.000.003 posições, e comparamos três funções hash:
FNV1a, polinomial de base 31 e djb2. Não usamos nenhuma classe pronta de coleções do Java, só vetores e as nossas próprias classes.

## Como compilar

Na pasta do projeto:

```
javac -encoding UTF-8 -d out scr/*.java
```

## Como executar

```
java -Xmx1g -cp out Principal <Books_rating.csv> <pasta_de_saida> [user_id.txt]
```

- `<Books_rating.csv>` é o caminho do arquivo com os dados.
- `<pasta_de_saida>` é uma pasta que já precisa existir.
- `user_id.txt` é opcional. Tem um `User_id` por linha, e o programa mostra o nome e o melhor livro de cada um. O arquivo
  deste repositório é um exemplo.

Exemplo no Windows:

```
mkdir saida
java -Xmx1g -cp out Principal "C:\dados\Books_rating.csv" saida user_id.txt
```

Com a base completa leva uns 20 segundos.

## O que é gerado

Na pasta de saída:

- `usuario_melhor_livro.csv`: o melhor livro de cada usuário.
- `usuario_melhor_livro_ordenado.csv`: o mesmo arquivo, ordenado da maior para a menor média.
- `top10_livros.csv`: os 10 livros com maior média entre os que têm pelo menos 100 avaliações.

Na tela o programa também mostra quantas linhas foram descartadas e por quê, o resultado das consultas, as tabelas do
experimento com as três funções hash e o tempo de cada etapa.

## Organização do código

Todo o código está na pasta `scr/`.

- `FuncoesHash`, `NoHash`, `TabelaHash`: as funções hash e a tabela.
- `LeitorCsv`, `CampoTexto`, `Texto`: leitura do arquivo e manipulação de texto.
- `RegistroLivro`, `RegistroUsuario`, `Avaliacoes`, `BaseDeDados`: guardam os dados lidos.
- `MelhorLivro`, `RankingLivros`, `Ordenacao`: escolhem o melhor livro, montam o top 10 e ordenam (merge sort).
- `Formato`, `Saida`, `GravadorTexto`, `GravadorResultados`, `Consultas`: escrita na tela e nos arquivos, e consulta por usuário.
- `Cronometro`, `MedidaHash`, `Experimento`: medição de tempo e experimento.
- `Principal`: junta tudo.

## Algumas decisões

- A média de um livro só fica definitiva depois de ler o arquivo inteiro. Por isso o arquivo é lido uma vez, as
  avaliações válidas ficam guardadas em memória e o melhor livro de cada usuário é escolhido depois.
- As notas são guardadas como inteiros (`4.0` vira `400`) para as contas ficarem exatas, e as médias são comparadas sem
  divisão: `somaA * nB > somaB * nA`.
- Se dois livros têm a mesma média, ganha o que tem mais avaliações; se ainda empatar, o título em ordem alfabética.
- Linhas sem `User_id`, sem `Title` ou com nota inválida são descartadas e contadas. Nome de perfil vazio não descarta a linha.
